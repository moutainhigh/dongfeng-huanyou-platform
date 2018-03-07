package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalTrackRes;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.dao.impl.RedisDao;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.general.OilingStealOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryStealOilCommand;
import com.navinfo.opentsp.dongfeng.report.dto.general.GeographicalDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.StealOilDataDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.StealOilDto;
import com.navinfo.opentsp.dongfeng.report.entity.OilingStealOilDataBean;
import com.navinfo.opentsp.dongfeng.report.entity.VehicleOilChangeEntity;
import com.navinfo.opentsp.dongfeng.report.pojo.general.StealOilPojo;
import com.navinfo.opentsp.dongfeng.report.service.general.IOilingStealOilService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangtiantong on 2018/02/28
 */
@Service
public class OilingStealOilServiceImpl implements IOilingStealOilService {

    private Logger logger = LoggerFactory.getLogger(OilingStealOilServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisDao redisTemplate;

    @Autowired
    private QueryGeographicalService queryGeographicalService;

    @Autowired
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    private static final double LimitSpeed = 200;

    public static final String VEHICLE_CHANGE_OIL = "vehicle_change_oil_";

    private final static String redis_vehicle_oil_change = "vehicle_oil_change";

    /**
     * 获取异常加油减油情况
     *
     * @param command
     * @return
     */
    @Override
    public StealOilDataDto getStealOilData(QueryStealOilCommand command) {

        StealOilDataDto stealOilDataDto = new StealOilDataDto();

        OilingStealOilCommand oilingStealOilCommand = new OilingStealOilCommand();
        oilingStealOilCommand.setVidList(Arrays.asList(command.getCommunicationId()));
        Long[] times = constructSearchParam(command.getDateStr());
        oilingStealOilCommand.setStartDate(times[0]);
        oilingStealOilCommand.setEndDate(times[1]);
        oilingStealOilCommand.setOilType(command.getType());

        String json = this.getOilingStealOil(oilingStealOilCommand.getVidList(), oilingStealOilCommand.getStartDate(), oilingStealOilCommand.getEndDate(),
                oilingStealOilCommand.getOilType());

        if (StringUtil.isEmpty(json)) {
            return stealOilDataDto;
        }
        Map<String, Object> stealOilPojosMap = null;
        try {
            stealOilPojosMap = JsonUtil.toMap(json);
            if (MapUtils.isEmpty(stealOilPojosMap) || stealOilPojosMap.get(command.getCommunicationId().toString()) == null || ListUtil.isEmpty((List<StealOilPojo>) stealOilPojosMap.get(command.getCommunicationId().toString()))) {
                return stealOilDataDto;
            }
            List<StealOilPojo> stealOilPojoList = JsonUtil.toList(JsonUtil.toJson(stealOilPojosMap.get(command.getCommunicationId().toString())), StealOilPojo.class);
            List<StealOilDto> stealOilDtoList = stealOilPojoToStealOilDto(stealOilPojoList);
            Map<Long, StealOilDto> tracks = getTerminalTrackAndTransformIntoStealOilDto(command.getCommunicationId(), times[0], times[1]);

            logger.info("abnormal tracks is {}", JsonUtil.toJson(stealOilDtoList));
            logger.info("normal tracks is {}", JsonUtil.toJson(tracks));

            // 异常点和正常轨迹合并
            List<StealOilDto> oilTracks = merge(stealOilDtoList, tracks);

            if (CollectionUtils.isEmpty(oilTracks)) {
                return stealOilDataDto;
            }
            stealOilDataDto.setList(oilTracks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stealOilDataDto;
    }

    /**
     * 加油减油
     *
     * @param lists
     * @param startDate
     * @param endDate
     * @param oilType
     * @return
     */
    @Override
    public String getOilingStealOil(List<Long> lists, long startDate, long endDate, long oilType) {

        long tempTime = System.currentTimeMillis();
        logger.info("OilingStealOilServiceImpl start" + tempTime);
        try {
            if (lists.size() > 0) {
                Map<Long, VehicleOilChangeEntity> pipeline = pipeline(redis_vehicle_oil_change, lists);//把redis里的数据取出来
                Map<Long, List<OilingStealOilDataBean>> mapOil = new HashMap<Long, List<OilingStealOilDataBean>>();//结果map
                //获取要查询哪个月表
                List list = new ArrayList<>();//添加查询表名
                String month = getMonth(startDate);
                if (month == null || "".equals(month)) {
                    return "";
                }
                list.add(VEHICLE_CHANGE_OIL + month); // 添加本选时间月表
                if (getDay(startDate) == 1) { //如果是每月的一号
                    String upperMonth = upperYearMonth(startDate);
                    if (upperMonth == null || "".equals(upperMonth)) {
                        return "";
                    }
                    list.add(VEHICLE_CHANGE_OIL + upperMonth); // 添加本选时间月表
                }

                for (int i = 0; i < lists.size(); i++) {//按vid取
                    List<OilingStealOilDataBean> oilRes = new ArrayList<>();
                    if (pipeline != null) {
                        VehicleOilChangeEntity oilBeanRedis = pipeline.get(lists.get(i));
                        if (oilBeanRedis != null) {
                            OilingStealOilDataBean oilBeanTemp = new OilingStealOilDataBean();
                            oilBeanTemp = getBean(oilBeanRedis);
                            oilRes.add(oilBeanTemp);//如果redis里有数据添加到vid的结果列表里
                        }
                    }

                    Criteria criteriaStart = new Criteria();

                    if (oilType == 3) {//查询全部
                        criteriaStart.andOperator(
                                Criteria.where("vid").is(lists.get(i)),
                                Criteria.where("endDate").gte(startDate),// 大于等于
                                Criteria.where("beginDate").lte(endDate));// 小于等于
                    } else if (oilType == 1 || oilType == 2) {
                        criteriaStart.andOperator(
                                Criteria.where("vid").is(lists.get(i)),
                                Criteria.where("endDate").gte(startDate),// 大于等于
                                Criteria.where("beginDate").lte(endDate),// 小于等于
                                Criteria.where("oilType").is(oilType));
                    } else {
                        return null;
                    }

                    Query query = new Query(criteriaStart);

                    List<VehicleOilChangeEntity> oilList = new ArrayList<>();//存放mongodb查出结果列表
                    if (list != null && list.size() > 0) {
                        for (int i1 = 0; i1 < list.size(); i1++) {//按月表查询
                            List<VehicleOilChangeEntity> oilListTemp = mongoTemplate.find(query, VehicleOilChangeEntity.class, list.get(i1).toString());
                            if (oilListTemp.size() > 0) {
                                oilList.addAll(oilListTemp);
                            }
                        }
                    }
                    if (oilList != null && oilList.size() > 0) {

                        for (int j = 0; j < oilList.size(); j++) {//一个车几条记录
                            OilingStealOilDataBean oilBeanTemp = new OilingStealOilDataBean();
                            VehicleOilChangeEntity oilBean = (VehicleOilChangeEntity) oilList.get(j);//每条个
                            //根据经纬度获得城市
                            long geoTime = System.currentTimeMillis();
                            logger.info("OilingStealOilServiceImpl queryGeographical start" + geoTime);
                            oilBeanTemp = getBean(oilBean);
                            logger.info("OilingStealOilServiceImpl queryGeographical end" + (System.currentTimeMillis() - geoTime));
                            oilRes.add(oilBeanTemp);//添加一个vid的一组数据
                        }
                    }
                    if (oilRes.size() > 0) {
                        mapOil.put(lists.get(i), oilRes);//添加一个以vid为key,list为结果的一条记录
                    }
                }
                tempTime = System.currentTimeMillis() - tempTime;
                logger.info("OilingStealOilServiceImpl end" + tempTime);
                if (mapOil.size() > 0) {
                    return JsonUtil.toJson(mapOil);
                }

            }
        } catch (Exception e) {
            logger.error("OilingStealOilServiceImpl getOilingStealOil" + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * 获取redis里的统计结果
     */
    public Map<Long, VehicleOilChangeEntity> pipeline(String mapName, List<Long> vids) {
        Map<Long, VehicleOilChangeEntity> map = new HashMap<Long, VehicleOilChangeEntity>();
        try {
            String[] hashkeys = new String[vids.size()];
            int index = 0;
            for (Long vid : vids) {
                hashkeys[index] = "" + vid;
                index++;
            }
            List<byte[]> all = redisTemplate.hMGetForBytes(mapName, hashkeys);
            for (int i = 0, size = all.size(); i < size; i++) {
                byte[] o = all.get(i);
                try {
                    if (o != null) {
                        VehicleOilChangeEntity online = JsonUtil.fromJson(getString(o), VehicleOilChangeEntity.class);
                        map.put(vids.get(i), online);
                    }
                } catch (Exception e) {
                    logger.error("OilingStealOilServiceImpl pipeline" + e.getMessage());
                }

            }
        } catch (JedisException e) {
            logger.error("OilingStealOilServiceImpl JedisException" + e.getMessage());
            return null;
        }
        return map;
    }

    public OilingStealOilDataBean getBean(VehicleOilChangeEntity oilBean) {
        OilingStealOilDataBean oilBeanTemp = new OilingStealOilDataBean();
        oilBeanTemp.setVid(oilBean.getVid());
        oilBeanTemp.setBeginDate(oilBean.getBeginDate());
        oilBeanTemp.setBeginLatitude(oilBean.getBeginLatitude());
        oilBeanTemp.setBeginLongitude(oilBean.getBeginLongitude());
        oilBeanTemp.setBeginGpsSpeed(oilBean.getBeginGpsSpeed());
        oilBeanTemp.setBeginStealOil(((float) oilBean.getBeginStealOil() / 100));
        oilBeanTemp.setBeginMileage(oilBean.getBeginMileage());

        oilBeanTemp.setEndDate(oilBean.getEndDate());
        oilBeanTemp.setEndLongitude(oilBean.getEndLongitude());
        oilBeanTemp.setEndLatitude(oilBean.getEndLatitude());
        oilBeanTemp.setEndGpsSpeed(oilBean.getEndGpsSpeed());
        oilBeanTemp.setEndStealOil(((float) oilBean.getEndStealOil() / 100));
        oilBeanTemp.setEndMileage(oilBean.getEndMileage());

        oilBeanTemp.setChangeTime(oilBean.getChangeTime());
        oilBeanTemp.setOilingDvalue(((float) oilBean.getOilingDvalue() / 100));
        oilBeanTemp.setOilType(oilBean.getOilType());

        String slon = String.valueOf((double) oilBean.getBeginLongitude() / 1000000);
        String slat = String.valueOf((double) oilBean.getBeginLatitude() / 1000000);
        GeographicalDto sGeographicalDto = queryGeographicalService.queryGeographical(slat, slon);

        if (sGeographicalDto != null) {
            String detailName = "";
            // 所在城市
            if (sGeographicalDto.getCity() != null && !"".equals(sGeographicalDto.getCity())) {
                detailName += sGeographicalDto.getCity();
            }
            // 所在区县
            if (sGeographicalDto.getDist() != null && !"".equals(sGeographicalDto.getDist())) {
                detailName += sGeographicalDto.getDist();
            }
            // 所在区域
            if (sGeographicalDto.getArea() != null && !"".equals(sGeographicalDto.getArea())) {
                detailName += sGeographicalDto.getArea();
            }
            // 所在乡镇
            if (sGeographicalDto.getTown() != null && !"".equals(sGeographicalDto.getTown())) {
                detailName += sGeographicalDto.getTown();
            }
            // 所在村
            if (sGeographicalDto.getVillage() != null && !"".equals(sGeographicalDto.getVillage())) {
                detailName += sGeographicalDto.getVillage();
            }
            oilBeanTemp.setDetailPosition(detailName);
        }
        return oilBeanTemp;
    }

    public Long byte2Long(byte[] bytes) {
        try {
            return Long.parseLong(new String(bytes, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getString(byte[] data) {
        if (data == null) {
            return null;
        }
        String ret = null;
        try {
            ret = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取当前天
     */
    public int getDay(long time) {
        try {
            time = time * 1000;
            //先解析给定的时间
            Date da = new Date(time);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            return c.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            logger.error(e.getMessage() + "OilingStealOilServiceImpl getDay");
        }
        return -1;
    }

    public String getMonth(long time) {
        try {
            time = time * 1000;
            //先解析给定的时间
            Date da = new Date(time);
            Calendar c = Calendar.getInstance();
            c.setTime(da);
            int re = c.get(Calendar.MONTH) + 1;
            String s = "";
            if (re < 10) {
                s = "0" + re;
            } else {
                s = "" + re;
            }

            return (c.get(Calendar.YEAR)) + s;
        } catch (Exception e) {
            logger.error(e.getMessage() + "OilingStealOilServiceImpl upperMonth");
            return "";
        }
    }

    public String upperYearMonth(long time) {
        try {
            time = time * 1000;
            //先解析给定的时间
            Date da = new Date(time);
            Calendar c = Calendar.getInstance();
            c.setTime(da);
            c.add(Calendar.MONTH, -1);
            int re = c.get(Calendar.MONTH) + 1;
            String s = "";
            if (re < 10) {
                s = "0" + re;
            } else {
                s = "" + re;
            }
            return c.get(Calendar.YEAR) + s;
        } catch (Exception e) {
            logger.error(e.getMessage() + "OilingStealOilServiceImpl upperMonth");
            return "";
        }
    }

    /**
     * 构建查询时间参数
     *
     * @param dateStr 日期字符串
     * @return 开始时间，结束时间
     */
    private Long[] constructSearchParam(String dateStr) {
        String beginTimeStr = "";
        String endTimeStr = "";
        if (!StringUtil.isEmpty(dateStr)) {
            beginTimeStr = dateStr + " 00:00:00";
            endTimeStr = dateStr + " 23:59:59";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String currentDate = sdf.format(c.getTime());
            beginTimeStr = currentDate + " 00:00:00";
            endTimeStr = currentDate + " 23:59:59";
        }
        long beginTime = DateUtil.parseTimeStrToSecond(beginTimeStr);
        long endTime = DateUtil.parseTimeStrToSecond(endTimeStr);
        Long[] result = new Long[2];
        result[0] = beginTime;
        result[1] = endTime;
        return result;
    }

    private List<StealOilDto> stealOilPojoToStealOilDto(List<StealOilPojo> stealOilPojos) {
        if (ListUtil.isEmpty(stealOilPojos)) {
            return null;
        }
        List<StealOilDto> stealOilDtoList = new ArrayList<>(stealOilPojos.size() * 2);
        for (StealOilPojo stealOilPojo : stealOilPojos) {
            StealOilDto stealOilDto = new StealOilDto();
            stealOilDto.setCommId(stealOilPojo.getVid());
            stealOilDto.setBeginDate(stealOilPojo.getBeginDate());
            stealOilDto.setBeginLatitude(stealOilPojo.getBeginLatitude());
            stealOilDto.setBeginLongitude(stealOilPojo.getBeginLongitude());
            stealOilDto.setDetailPosition(stealOilPojo.getDetailPosition());
            stealOilDto.setBeginOil(stealOilPojo.getBeginStealOil());
            stealOilDto.setBeginGpsSpeed(stealOilPojo.getBeginGpsSpeed());
            stealOilDto.setOilType(stealOilPojo.getOilType());
            stealOilDto.setEndDate(stealOilPojo.getEndDate());
            stealOilDto.setEndLatitude(stealOilPojo.getEndLatitude());
            stealOilDto.setEndLongitude(stealOilPojo.getEndLongitude());
            stealOilDto.setEndOil(stealOilPojo.getEndStealOil());
            stealOilDto.setOilingDvalue(stealOilPojo.getOilingDvalue());
            stealOilDto.setEndGpsSpeed(stealOilPojo.getEndGpsSpeed());
            stealOilDto.setOilType(stealOilPojo.getOilType());
            stealOilDto.setBeginMileage(stealOilPojo.getBeginMileage());
            stealOilDto.setEndMileage(stealOilPojo.getEndMileage());
            stealOilDto.setChangeTime(stealOilPojo.getChangeTime());
            stealOilDtoList.add(stealOilDto);
        }
        return stealOilDtoList;
    }

    /**
     * 获取车辆轨迹并转化为异常油量对象
     *
     * @param communicationId
     * @param startDate
     * @param endDate
     * @return
     */
    private Map<Long, StealOilDto> getTerminalTrackAndTransformIntoStealOilDto(Long communicationId, Long startDate, Long endDate) throws Exception {
        Map<Long, StealOilDto> resultMap = new HashMap<>();
        List<LCTerminalLocationData.TerminalLocationData> terminalTrack = getTerminalTrack(communicationId, startDate, endDate);
        for (LCTerminalLocationData.TerminalLocationData terminalLocationData : terminalTrack) {
            LCLocationData.LocationData loc = terminalLocationData.getLocationData();
            if (!StringUtil.isEmpty(loc)) {
                long statusLists = loc.getStatus();
                boolean isAccOn = (statusLists & 0x000001) == 0 ? false : true;// 点火状态:0：ACC
                if (isAccOn) {// acc状态为on
                    if (loc.getGpsDate() > 0L) {
                        StealOilDto stealOilDto = new StealOilDto();
                        Double tempOilCapacities = 0d;
                        if (loc.getStatusAddition() != null && loc.getStatusAddition().getStatusList().size() > 0) {
                            LCLocationData.VehicleStatusAddition vstatus = loc.getStatusAddition();
                            List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
                            for (LCVehicleStatusData.VehicleStatusData status : vlists) {
                                int t = status.getTypes().getNumber();
                                switch (t) {
                                    case LCStatusType.StatusType.oilValue_VALUE:
                                        double oilValue = status.getStatusValue() * 0.01 * 0.01;
                                        BigDecimal bd = new BigDecimal(oilValue);
                                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                        tempOilCapacities = new Double(bd.doubleValue());// 油量（百分比）
                                        break;
                                }
                            }
                        }
                        stealOilDto.setCommId(communicationId);
                        stealOilDto.setBeginDate(loc.getGpsDate());
                        if (!StringUtil.isEmpty(loc.getLatitude())) {
                            stealOilDto.setBeginLatitude((long) loc.getLatitude());
                        }
                        if (!StringUtil.isEmpty(loc.getLongitude())) {
                            stealOilDto.setBeginLongitude((long) loc.getLongitude());
                        }
                        stealOilDto.setBeginOil(tempOilCapacities.floatValue());
                        if (!StringUtil.isEmpty(loc.getSpeed())) {
                            stealOilDto.setBeginGpsSpeed((long) loc.getSpeed());
                        }
                        stealOilDto.setOilType(0);//填充为0
                        stealOilDto.setBeginMileage(Float.valueOf(loc.getStandardMileage()).longValue());
                        resultMap.put(loc.getGpsDate(), stealOilDto);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 轨迹合并
     *
     * @param abnormalTracks 异常轨迹点
     * @param tracks         全部轨迹点
     * @return
     */
    private List<StealOilDto> merge(final List<StealOilDto> abnormalTracks, final Map<Long, StealOilDto> tracks) {
        List<StealOilDto> oilTracks = new ArrayList<>();
        for (Map.Entry<Long, StealOilDto> track : tracks.entrySet()) {
            final long beginDate = track.getKey();
            boolean isAbnormal = false;
            for (StealOilDto oilDto : abnormalTracks) {
                if (beginDate > oilDto.getBeginDate() && beginDate <= oilDto.getEndDate()) {
                    isAbnormal = true;
                    break;
                }
            }
            if (!isAbnormal) {
                oilTracks.add(track.getValue());
            }
        }
        oilTracks.addAll(tracks.values());
        Collections.sort(oilTracks);
        return oilTracks;
    }

    /**
     * 获取终端轨迹数据
     *
     * @param vid
     * @param startDate
     * @param endDate
     */
    public List<LCTerminalLocationData.TerminalLocationData> getTerminalTrack(long vid, long startDate,
                                                                              long endDate) throws Exception {
        List<LCTerminalLocationData.TerminalLocationData> list = new ArrayList<>();
        try {
            // 公共参数
            CommonParameterSerializer commonParameter = new CommonParameterSerializer();
            commonParameter.setAccessTocken(0);
            commonParameter.setMultipage(false);

            BasicDataQueryWebService basicDataQueryWebService =
                    cloudDataRmiClientConfiguration.getBasicDataQueryWebService();

            byte[] data =
                    basicDataQueryWebService.getTerminalTrack(vid,
                            startDate,
                            endDate,
                            false,
                            0L,
                            commonParameter,
                            -1);
            if (!StringUtil.isEmpty(data)) {
                LCTerminalTrackRes.TerminalTrackRes builder = LCTerminalTrackRes.TerminalTrackRes.parseFrom(data);
                list = builder.getDatasList();
            }
            logger.warn("----位置云getTerminalTrack，返回总记录数：" + list.size());
        } catch (Exception e) {
            logger.error("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + vid + ")", e);
            throw e;
        }
        return list;
    }
}
