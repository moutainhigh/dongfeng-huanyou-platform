package com.navinfo.opentsp.dongfeng.report.service.oil;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.webservice.newstatisticsdata.LCMileageConsumptionRecords;
import com.lc.core.protocol.webservice.newstatisticsdata.entity.LCMileageConsumptionRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalTrackRes;
import com.mongodb.BasicDBObject;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.impl.MongoDBService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.common.util.tcp.DoubleUtils;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilChartCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryTireCommand;
import com.navinfo.opentsp.dongfeng.report.constant.LCMongo;
import com.navinfo.opentsp.dongfeng.report.converter.oil.OilConverter;
import com.navinfo.opentsp.dongfeng.report.dto.oil.OilDto;
import com.navinfo.opentsp.dongfeng.report.dto.oil.TireDto;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarInfoPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarQueryParam;
import com.navinfo.opentsp.dongfeng.report.pojo.oil.OilChartPojo;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant.SYSTEM_ADMIN;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-30
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class OilService extends BaseService implements IOilService {
    private static Double lastAverageOil = 0.0;
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
    @Autowired
    private GpsInfoCache gpsInfoCache;
    @Autowired
    private MongoDBService mongoDBService;

    private static FastDateFormat fdf = FastDateFormat.getInstance("yyyyMM");

    // 第一轴左轮
    @Value("${tire.location.first.axis.left.wheel}")
    private String firstAxisLeftWheel;
    // 第一轴右轮
    @Value("${tire.location.first.axis.right.wheel}")
    private String firstAxisRightWheel;
    // 第二轴左外轮
    @Value("${tire.location.second.axis.left.outer.wheel}")
    private String secondAxisLeftOuterWheel;
    // 第二轴左内轮
    @Value("${tire.location.second.axis.left.inner.wheel}")
    private String secondAxisLeftInnerWheel;
    // 第二轴右内轮
    @Value("${tire.location.second.axis.right.inner.wheel}")
    private String secondAxisRightInnerWheel;
    // 第二轴右外轮
    @Value("${tire.location.second.axis.right.outer.wheel}")
    private String secondAxisRightOuterWheel;
    // 第三轴左外轮
    @Value("${tire.location.third.axis.left.outer.wheel}")
    private String thirdAxisLeftOuterWheel;
    // 第三轴左内轮
    @Value("${tire.location.third.axis.left.inner.wheel}")
    private String thirdAxisLeftInnerWheel;
    // 第三轴右内轮
    @Value("${tire.location.third.axis.right.inner.wheel}")
    private String thirdAxisRightInnerWheel;
    // 第三轴右外轮
    @Value("${tire.location.third.axis.right.outer.wheel}")
    private String thirdAxisRightOuterWheel;
    // 备胎
    @Value("${tire.location.spare.tire}")
    private String spareTire;


    @Override
    public PagingInfo queryOil(QueryOilCommand command, boolean isPaging) {
        //转换为查询参数
        CarQueryParam param = OilConverter.oilParamToCarQueryParam(command);
        //获取绑定终端的车辆
        PagingInfo<CarInfoPojo> pojoPagingInfo = queryOilCarOfBindTerminal(param, isPaging);
        List<CarInfoPojo> carInfoPojoList = pojoPagingInfo.getList();
        List<Long> communicationIds = new ArrayList<>(carInfoPojoList.size());
        //组装通讯号
        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            communicationIds.add(carInfoPojo.getCommunicationId().longValue());
        }
        //从位置云获取油耗信息
        Long[] times = constructSearchParam(command.getDateStr());
        List<LCMileageConsumptionRes.MileageConsumptionRes> mileageConsumptionRecords = getMileageConsumptionRecords(communicationIds, times[0], times[1]);
        //拼装返回值
//        List<OilDto> oilDtos = toOilDtoList(carInfoPojoList, mileageConsumptionRecords);
        // 获取胎压,胎温信息
        List<BasicDBObject> tireInfoList = getTireData(communicationIds, command.getDateStr());
        // 拼装返回值
        List<OilDto> oilDtos = toOilDtoList(carInfoPojoList, mileageConsumptionRecords, tireInfoList);
        PagingInfo page = new PagingInfo();
        page.setList(oilDtos);
        page.setPage_total(pojoPagingInfo.getPage_total());
        page.setTotal(pojoPagingInfo.getTotal());
        return page;
    }

    @Override
    public HttpCommandResultWithData oilChart(QueryOilChartCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        OilChartPojo oilChart = new OilChartPojo();
        oilChart.setMaxOilCapacity(command.getOilCapacity());
        Long[] times = constructSearchParam(command.getDateStr());
        List<LCTerminalLocationData.TerminalLocationData> terminalTrack = getTerminalTrack(command.getCommunicationId(), times[0], times[1]);
        for (LCTerminalLocationData.TerminalLocationData terminalLocationData : terminalTrack) {
            LCLocationData.LocationData loc = terminalLocationData.getLocationData();
            if (!StringUtil.isEmpty(loc)) {
                long statusLists = loc.getStatus();
                boolean isAccOn = (statusLists & 0x000001) == 0 ? false : true;//点火状态:0：ACC 关；1： ACC 开
                if (isAccOn) {//acc状态为on
                    //时间，速度km/h
                    //整车里程km，油量oilValue 0x0E 车辆当前油量（百分比）
                    if (loc.getGpsDate() > 0L) {
                        Double tempOilCapacities = 0d;
                        if (loc.getStatusAddition() != null
                                && loc.getStatusAddition().getStatusList().size() > 0) {
                            LCLocationData.VehicleStatusAddition vstatus = loc.getStatusAddition();
                            List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
                            for (LCVehicleStatusData.VehicleStatusData status : vlists) {
                                int t = status.getTypes().getNumber();
                                switch (t) {
//                                    case LCStatusType.StatusType.mileage_VALUE:
//                                        tempMileages = new Double(status.getStatusValue() * 0.01);//整车里程（Km）
//                                        break;
                                    case LCStatusType.StatusType.oilValue_VALUE:
                                        double oilValue = status.getStatusValue() * 0.01 * 0.01;
                                        BigDecimal bd = new BigDecimal(oilValue);
                                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                        tempOilCapacities = new Double(bd.doubleValue());//油量（百分比）
                                        break;
//                                    case LCStatusType.StatusType.totalFuelConsumption_VALUE:
//                                        tempOilCons = status.getStatusValue() * 0.01;
//                                        break;
                                }
                            }
                        }
                        double totalMile = loc.getStandardMileage();
                        double totalOil = loc.getStandardFuelCon();
                        oilChart.addOneTime(new Long(loc.getGpsDate() * 1000));
                        oilChart.addOneSpeeds(new Double(loc.getSpeed()));
                        oilChart.addOneMileages(totalMile);//整车里程（Km）);
                        oilChart.addOneOilCapacities(tempOilCapacities);
                        oilChart.addOneOilCons(totalOil);
                        if (totalMile != 0) {
                            Double averageOil = totalOil / totalMile * 100;
                            if (averageOil > 0) {
                                lastAverageOil = averageOil;
                            }
                            oilChart.addOneAverageOil(lastAverageOil);
                        } else {
                            oilChart.addOneAverageOil(0.0);
                        }

                    }
                } else {
                    logger.info("油量异常chart：accStatus为off");
                }
            }
        }
        oilChart.fullFillList();
        result.setData(oilChart);
        return result;
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

    /**
     * @param carInfoPojoList
     * @param mileageConsumptionRecords
     * @return
     */
    private List<OilDto> toOilDtoList
    (List<CarInfoPojo> carInfoPojoList, List<LCMileageConsumptionRes.MileageConsumptionRes> mileageConsumptionRecords) {
        List<OilDto> result = new ArrayList<>();
        Map<Long, LCMileageConsumptionRes.MileageConsumptionRes> oilMap = new HashMap<Long, LCMileageConsumptionRes.MileageConsumptionRes>();
        for (LCMileageConsumptionRes.MileageConsumptionRes mileageConsumptionRecord : mileageConsumptionRecords) {
            oilMap.put(mileageConsumptionRecord.getTerminalID(), mileageConsumptionRecord);
        }
        Map<String, GpsInfoData> allGpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            GpsInfoData gpsInfo = allGpsInfoMap.get(carInfoPojo.getCommunicationId().toString());
            result.add(toOilDto(carInfoPojo, oilMap.get(carInfoPojo.getCommunicationId().longValue()), gpsInfo));
        }
        return result;
    }

    /**
     * 转换为数据对象
     *
     * @param carInfoPojo        车辆基本信息
     * @param mileageConsumption 油耗信息
     * @return
     */

    private OilDto toOilDto(CarInfoPojo carInfoPojo, LCMileageConsumptionRes.MileageConsumptionRes mileageConsumption, GpsInfoData gpsInfo) {
        OilDto oilDto = new OilDto();
        //填充车辆基本信息
        oilDto.setCarId(carInfoPojo.getCarId());
        oilDto.setChassisNum(carInfoPojo.getChassisNum());
        oilDto.setCarCph(carInfoPojo.getCarNo());
        oilDto.setTerId(carInfoPojo.getTerminalCode());
        oilDto.setFkId(carInfoPojo.getFkCode());
        oilDto.setCompanyName(carInfoPojo.getCompanyName());
        oilDto.setCarOwner(carInfoPojo.getBusinessName());
        oilDto.setCarType(carInfoPojo.getCarTypeName());
        oilDto.setEngineType(carInfoPojo.getEngineTypeName());
        oilDto.setEngineNumber(carInfoPojo.getEngineNumber());
        oilDto.setCommunicationId(carInfoPojo.getCommunicationId().toString());

        //填充油耗信息
        if (mileageConsumption != null) {
            if (mileageConsumption.getStartDate() != 0) {
                oilDto.setBeginDate(mileageConsumption.getStartDate());
                oilDto.setBeginDateStr(DateUtils.formatDate(mileageConsumption.getStartDate() * 1000));
            }
            if (mileageConsumption.getEndDate() != 0) {
                oilDto.setEndDate(mileageConsumption.getEndDate());
                oilDto.setEndDateStr(DateUtils.formatDate(mileageConsumption.getEndDate() * 1000));
            }
            oilDto.setbLog(mileageConsumption.getBeginLng() * 0.000001);
            oilDto.setbLat(mileageConsumption.getBeginLat() * 0.000001);
            oilDto.setBeginLocation(oilDto.getbLog() + ";" + oilDto.getbLat());
            oilDto.seteLog(mileageConsumption.getEndLng() * 0.000001);
            oilDto.seteLat(mileageConsumption.getEndLat() * 0.000001);
            oilDto.setEndLocation(oilDto.geteLog() + ";" + oilDto.geteLat());
            if (mileageConsumption.getMileage() >= 0) {
                oilDto.setMileage(DoubleUtils.getDoubleData(mileageConsumption.getMileage() / 1000.0) + "");
            } else {
                oilDto.setMileage("0.0");
            }
            if (mileageConsumption.getOilConsumption() >= 0) {
                oilDto.setFuelCspDaily(mileageConsumption.getOilConsumption() + "");
            } else {
                oilDto.setFuelCspDaily("0.0");
            }

            if (mileageConsumption.getMileage() > 0 && mileageConsumption.getOilConsumption() >= 0) {
                oilDto.setFuelCspRate(DoubleUtils.getDoubleData(100.0 * mileageConsumption.getOilConsumption() / mileageConsumption.getMileage() * 1000) + "");
            } else {
                oilDto.setFuelCspRate("0.0");
            }
            //从缓存获取总油耗和总里程
            if (gpsInfo != null && !StringUtil.isEmpty(gpsInfo.getTempMileage()) && !StringUtil.isEmpty(gpsInfo.getTempOilCons())) {
                if (gpsInfo.getTempMileage() != 0) {
                    Double averageOil = gpsInfo.getTempOilCons() / gpsInfo.getTempMileage() * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    oilDto.setFuelCspTotalRate(df.format(averageOil));///平均百公里能耗
                } else {
                    oilDto.setFuelCspTotalRate("0.0");///平均百公里能耗
                }
            } else {
                oilDto.setFuelCspTotalRate("0.0");///平均百公里能耗
            }
        }
        return oilDto;
    }


    /**
     * 转换为数据对象
     *
     * @param carInfoPojo 车辆基本信息
     * @return
     */

    private TireDto toTireDto(CarInfoPojo carInfoPojo, LCTireTemperatureAddition.TireTemperatureAddition addition) {
        TireDto tireDto = new TireDto();
        //填充车辆基本信息
        tireDto.setCarId(carInfoPojo.getCarId());
        tireDto.setChassisNum(carInfoPojo.getChassisNum());
        tireDto.setCarCph(carInfoPojo.getCarNo());
        tireDto.setTerId(carInfoPojo.getTerminalCode());
        tireDto.setFkId(carInfoPojo.getFkCode());
        tireDto.setCompanyName(carInfoPojo.getCompanyName());
        tireDto.setCarOwner(carInfoPojo.getBusinessName());
        tireDto.setCarType(carInfoPojo.getCarTypeName());
        tireDto.setEngineType(carInfoPojo.getEngineTypeName());
        tireDto.setEngineNumber(carInfoPojo.getEngineNumber());
        tireDto.setCommunicationId(carInfoPojo.getCommunicationId().toString());


        StringBuffer tirePressure = new StringBuffer();
        StringBuffer tireTemperature = new StringBuffer();
        for (LCTireTemperatureAddition.TireConditionItem item : addition.getTireConditionItemList()) {
            if (Integer.parseInt(firstAxisLeftWheel) == item.getTireLocation()) {
                tirePressure.append("第一轴左轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第一轴左轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(firstAxisRightWheel) == item.getTireLocation()) {
                tirePressure.append("第一轴右轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第一轴右轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(secondAxisLeftInnerWheel) == item.getTireLocation()) {
                tirePressure.append("第二轴左内轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第二轴左内轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(secondAxisLeftOuterWheel) == item.getTireLocation()) {
                tirePressure.append("第二轴左外轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第二轴左外轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(secondAxisRightOuterWheel) == item.getTireLocation()) {
                tirePressure.append("第二轴右外轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第二轴右外轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(secondAxisRightInnerWheel) == item.getTireLocation()) {
                tirePressure.append("第二轴右内轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第二轴右内轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(thirdAxisRightInnerWheel) == item.getTireLocation()) {
                tirePressure.append("第三轴右内轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第三轴右内轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(thirdAxisRightOuterWheel) == item.getTireLocation()) {
                tirePressure.append("第三轴右外轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第三轴右外轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(thirdAxisLeftOuterWheel) == item.getTireLocation()) {
                tirePressure.append("第三轴左外轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第三轴左外轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(thirdAxisLeftInnerWheel) == item.getTireLocation()) {
                tirePressure.append("第三轴左内轮: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("第三轴左内轮: " + item.getTireTemperature() + "℃,");
            } else if (Integer.parseInt(spareTire) == item.getTireLocation()) {
                tirePressure.append("备胎: " + item.getTirePressure() + "kpa,");
                tireTemperature.append("备胎: " + item.getTireTemperature() + "℃,");
            }
        }
        if (StringUtil.isNotEmpty(new String(tirePressure)) && tirePressure.substring(tirePressure.length() - 1).equals(",")) {
            tirePressure = new StringBuffer(tirePressure.substring(0, tirePressure.length() - 1));
        }
        if (StringUtil.isNotEmpty(new String(tireTemperature)) && tireTemperature.substring(tireTemperature.length() - 1).equals(",")) {
            tireTemperature = new StringBuffer(tireTemperature.substring(0, tireTemperature.length() - 1));
        }
        // 设置胎温
        tireDto.setTirePressurePercent(tirePressure.toString());
        // 设置胎压
        tireDto.setTireTempreturePercent(tireTemperature.toString());
        // 设置GPS时间
        tireDto.setTimeStr(DateUtil.time2Str(addition.getGpsTime()));

        return tireDto;
    }


    /**
     * 获取里程油耗信息
     *
     * @param communicationIds 通讯号
     * @param startDate        起始时间
     * @param endDate          结束时间
     * @return
     */
    private List<LCMileageConsumptionRes.MileageConsumptionRes> getMileageConsumptionRecords(List<Long> communicationIds, long startDate, long endDate) {
        List<LCMileageConsumptionRes.MileageConsumptionRes> list = new ArrayList<>();
        try {
            CommonParameterSerializer commonParameterSerializer = new CommonParameterSerializer();
            commonParameterSerializer.setMultipage(false);
            commonParameterSerializer.setCode(0);
            commonParameterSerializer.setSort(true);
            commonParameterSerializer.setSortTerminal(true);
            commonParameterSerializer.setQueryKey("");
            DataAnalysisWebService dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
            byte[] response = dataAnalysisWebService.getMileageConsumptionRecords(communicationIds, startDate, endDate, commonParameterSerializer);
            if (!StringUtil.isEmpty(response)) {
                LCMileageConsumptionRecords.MileageConsumptionRecords mileageConsumptionRecords = LCMileageConsumptionRecords.MileageConsumptionRecords.parseFrom(response);
                list = mileageConsumptionRecords.getDataListList();
                logger.warn("----位置云getMileageConsumptionRecords，返回总记录数" + list.size());
            } else {
                logger.warn("----位置云getMileageConsumptionRecords，返回 null");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取终端轨迹数据
     *
     * @param communicationId
     * @param startDate
     * @param endDate
     */
    public List<LCTerminalLocationData.TerminalLocationData> getTerminalTrack(long communicationId, long startDate, long endDate) {
        List<LCTerminalLocationData.TerminalLocationData> list = new ArrayList<>();
        try {
            CommonParameterSerializer commonParameterSerializer = new CommonParameterSerializer();
            commonParameterSerializer.setMultipage(false);
            BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration.getBasicDataQueryWebService();
            byte[] response = basicDataQueryWebService.getTerminalTrack(communicationId, startDate, endDate, false, 0l, commonParameterSerializer, 0);
            if (response.length != 0 || null != response) {
                LCTerminalTrackRes.TerminalTrackRes builder = LCTerminalTrackRes.TerminalTrackRes.parseFrom(response);
                if (null != builder) {
                    list = builder.getDatasList();
                    logger.warn("----位置云getTerminalTrack，返回总记录数：" + list.size());
                }
            }
        } catch (Exception e) {
            logger.error("调用位置云获取轨迹数据出错，调用参数：terminalIds(" + communicationId + ")", e);
        }
        return list;
    }

    /**
     * 时间段内总油耗
     */
    public long getTToil(LCLocationData.LocationData gpslist) {
        //实际返回油耗
        long lastOil = 0;
        //ECU油耗
        long ecuoil = 0;
        //积分油耗
        long jfoil = 0;
        //平台计算油耗
        long ptjsoil = 0;
        LCLocationData.VehicleStatusAddition vstatus = gpslist.getStatusAddition();
        List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
        for (LCVehicleStatusData.VehicleStatusData status : vlists) {
            int t = status.getTypes().getNumber();
            if (t == LCStatusType.StatusType.totalFuelConsumption_VALUE) {
                ecuoil = status.getStatusValue();
                continue;
            }

            if (t == LCStatusType.StatusType.integralFuelConsumption_VALUE) {
                jfoil = status.getStatusValue();
                continue;
            }
            if (t == LCStatusType.StatusType.oilValue_VALUE) {
                ptjsoil = status.getStatusValue();
                continue;
            }
        }
        if (ecuoil != 0l) {
            lastOil = ecuoil;
        }
        if (ptjsoil != 0l) {
            lastOil = ptjsoil;
        }
        if (jfoil != 0l) {
            lastOil = jfoil;
        }
        return lastOil;
    }

    /*
   * 总里程
    */
    public double getTTMile(LCLocationData.LocationData gpslist) {
        //仪表行驶
        long ybmile = 0;
        //ECU里程
        long ecumile = 0;
        //GPS里程
        long gpsmile = gpslist.getMileage();
        LCLocationData.VehicleStatusAddition vstatus = gpslist.getStatusAddition();
        List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
        for (LCVehicleStatusData.VehicleStatusData status : vlists) {
            int t = status.getTypes().getNumber();
            if (t == LCStatusType.StatusType.mileage_VALUE) {
                ecumile = status.getStatusValue();
                continue;
            }

            if (t == LCStatusType.StatusType.mileageDD_VALUE) {
                ybmile = status.getStatusValue();
                continue;
            }
        }
        //实际返回里程
        double lastMile = 0;
        if (ybmile != 0l) {
            lastMile = NumberFormatUtil.getDoubleValueData((double) ybmile / 100, 2);
            return lastMile;
        }
        if (ecumile != 0l) {
            lastMile = NumberFormatUtil.getDoubleValueData((double) ecumile / 100, 2);
            return lastMile;
        }
        if (gpsmile != 0l) {
            lastMile = NumberFormatUtil.getDoubleValueData((double) gpsmile / 1000, 2);
            return lastMile;
        }
        return lastMile;
    }

    public PagingInfo<CarInfoPojo> queryOilCarOfBindTerminal(CarQueryParam queryParam, boolean isPaging) {
        PagingInfo<CarInfoPojo> pageList = new PagingInfo<CarInfoPojo>();
        try {
            UserTypeConstant userTypeConstant = UserTypeConstant.valueOf(queryParam.getUserType());
            if (userTypeConstant == SYSTEM_ADMIN) {
                queryParam.setAccountId(null);
            }
            Map<String, Object> paramMap = ObjUtils.toPropertyMap(queryParam);
            if (isPaging) {//分页查询
                pageList = dao.sqlPagelFind("queryOilVehicleOfBindTerminal", paramMap, Integer.valueOf(queryParam.getPageNum()), Integer.valueOf(queryParam.getPageSize()), CarInfoPojo.class);
            } else {//查询全部
                List<CarInfoPojo> list = dao.sqlFind("queryOilVehicleOfBindTerminal", paramMap, CarInfoPojo.class);
                pageList.setList(list);
                // 总条数
                pageList.setTotal(list.size());
                // 总页数
                pageList.setPage_total(0);
            }
            return pageList;
        } catch (Exception e) {
            logger.error("query car list of bind terminal failed,", e);
        }
        return null;
    }

    /**
     * 查询胎压胎温信息
     *
     * @param communicationIds 通讯号集合
     * @param dateStr          截止时间
     * @return
     */
    private List<BasicDBObject> getTireData(List<Long> communicationIds, String dateStr) {
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }
        // 从mongo获取最新胎压,胎温信息
        String endDateStr = fdf.format(DateUtil.parseDate(dateStr + " 00:00:00"));
        Query query = new Query(Criteria.where("terminalId").in(communicationIds)).with(new Sort(Sort.Direction.DESC, "gpsTime")).limit(1);
        List<BasicDBObject> basicDBObjects = mongoDBService.find(query, BasicDBObject.class, LCMongo.Collection.LC_TIRE_TEMPERATURE_DATA + endDateStr);
        return basicDBObjects;
    }

    /**
     * 查询轮胎详细信息
     *
     * @param communicationIds 通讯号集合
     * @param dateStr          截止时间
     * @param isPage           是否分页
     * @param pageSize         每页数量
     * @param pageNum          当前页
     * @return
     */
    private Map<String, Object> getTireDetailData(List<Long> communicationIds, String dateStr, boolean isPage, int pageSize, int pageNum) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }
        // 从mongo获取最新轮胎详细信息
        String endDateStr = fdf.format(DateUtil.parseDate(dateStr + " 00:00:00"));
        Query query;
        if (isPage) { // 分页
            query = new Query(Criteria.where("terminalId").in(communicationIds)).skip(pageNum * pageSize).with(new Sort(Sort.Direction.DESC, "gpsTime")).limit(pageSize);
        } else {
            query = new Query(Criteria.where("terminalId").in(communicationIds)).with(new Sort(Sort.Direction.DESC, "gpsTime"));
        }
        // 设置总数
        Long total = mongoDBService.count(new Query(Criteria.where("terminalId").in(communicationIds)), LCMongo.Collection.LC_TIRE_TEMPERATURE_DATA + endDateStr);
        resultMap.put("total", total);
        List<BasicDBObject> basicDBObjects = mongoDBService.find(query, BasicDBObject.class, LCMongo.Collection.LC_TIRE_TEMPERATURE_DATA + endDateStr);
        resultMap.put("data", basicDBObjects);
        return resultMap;
    }


    /**
     * 组装数据
     *
     * @return
     */
    private List<OilDto> toOilDtoList(List<CarInfoPojo> carInfoPojoList, List<LCMileageConsumptionRes.MileageConsumptionRes> mileageConsumptionRecords, List<BasicDBObject> basicDBObjects) {
        List<OilDto> result = new ArrayList<>();
        Map<Long, LCMileageConsumptionRes.MileageConsumptionRes> oilMap = new HashMap<Long, LCMileageConsumptionRes.MileageConsumptionRes>();
        for (LCMileageConsumptionRes.MileageConsumptionRes mileageConsumptionRecord : mileageConsumptionRecords) {
            oilMap.put(mileageConsumptionRecord.getTerminalID(), mileageConsumptionRecord);
        }
        Map<String, GpsInfoData> allGpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            GpsInfoData gpsInfo = allGpsInfoMap.get(carInfoPojo.getCommunicationId().toString());
            result.add(toOilDto(carInfoPojo, oilMap.get(carInfoPojo.getCommunicationId().longValue()), gpsInfo));
        }
        for (BasicDBObject basicDBObject : basicDBObjects) {
            for (OilDto oilDto : result) {
                if (oilDto.getCommunicationId().equals(basicDBObject.get("terminalId").toString())) {// 通过terminalId匹配
                    try {
                        LCTireTemperatureAddition.TireTemperatureAddition addition = LCTireTemperatureAddition.TireTemperatureAddition.parseFrom((byte[]) basicDBObject.get("data"));
                        StringBuffer tirePressure = new StringBuffer();
                        StringBuffer tireTemperature = new StringBuffer();
                        for (LCTireTemperatureAddition.TireConditionItem item : addition.getTireConditionItemList()) {
                            if (Integer.parseInt(firstAxisLeftWheel) == item.getTireLocation()) {
                                tirePressure.append("第一轴左轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第一轴左轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(firstAxisRightWheel) == item.getTireLocation()) {
                                tirePressure.append("第一轴右轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第一轴右轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(secondAxisLeftInnerWheel) == item.getTireLocation()) {
                                tirePressure.append("第二轴左内轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第二轴左内轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(secondAxisLeftOuterWheel) == item.getTireLocation()) {
                                tirePressure.append("第二轴左外轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第二轴左外轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(secondAxisRightOuterWheel) == item.getTireLocation()) {
                                tirePressure.append("第二轴右外轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第二轴右外轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(secondAxisRightInnerWheel) == item.getTireLocation()) {
                                tirePressure.append("第二轴右内轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第二轴右内轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(thirdAxisRightInnerWheel) == item.getTireLocation()) {
                                tirePressure.append("第三轴右内轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第三轴右内轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(thirdAxisRightOuterWheel) == item.getTireLocation()) {
                                tirePressure.append("第三轴右外轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第三轴右外轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(thirdAxisLeftOuterWheel) == item.getTireLocation()) {
                                tirePressure.append("第三轴左外轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第三轴左外轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(thirdAxisLeftInnerWheel) == item.getTireLocation()) {
                                tirePressure.append("第三轴左内轮: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("第三轴左内轮: " + item.getTireTemperature() + "℃,");
                            } else if (Integer.parseInt(spareTire) == item.getTireLocation()) {
                                tirePressure.append("备胎: " + item.getTirePressure() + "kpa,");
                                tireTemperature.append("备胎: " + item.getTireTemperature() + "℃,");
                            }
                        }
                        if (StringUtil.isNotEmpty(tirePressure) && tirePressure.substring(tirePressure.length() - 1).equals(",")) {
                            tirePressure = new StringBuffer(tirePressure.substring(0, tirePressure.length() - 1));
                        }
                        if (StringUtil.isNotEmpty(tireTemperature) && tireTemperature.substring(tireTemperature.length() - 1).equals(",")) {
                            tireTemperature = new StringBuffer(tireTemperature.substring(0, tireTemperature.length() - 1));
                        }
                        // 设置胎温
                        oilDto.setLastTireTempreture(tireTemperature.toString());
                        // 设置胎压
                        oilDto.setLastTirePressure(tirePressure.toString());
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }


    /**
     * 组装数据
     *
     * @return
     */
    private List<TireDto> toTireDtoList(List<CarInfoPojo> carInfoPojoList, List<BasicDBObject> basicDBObjects) {
        List<TireDto> result = new ArrayList<>();

        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            for (BasicDBObject basicDBObject : basicDBObjects) {
                if (carInfoPojo.getCommunicationId().equals(BigInteger.valueOf(basicDBObject.getLong("terminalId")))) {
                    try {
                        LCTireTemperatureAddition.TireTemperatureAddition addition = LCTireTemperatureAddition.TireTemperatureAddition.parseFrom((byte[]) basicDBObject.get("data"));
                        if (addition != null) {
                            result.add(toTireDto(carInfoPojo, addition));
                        }
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }


    /**
     * 查询轮胎信息
     *
     * @param command
     * @param isPaging 是否分页  true 分页  false 不分页查询全部
     * @return
     */
    @Override
    public PagingInfo queryTireInfo(QueryTireCommand command, boolean isPaging) {
        //转换为查询参数
        CarQueryParam param = OilConverter.oilParamToCarTireQueryParam(command);
        //获取绑定终端的车辆
        PagingInfo<CarInfoPojo> pojoPagingInfo = queryOilCarOfBindTerminal(param, false);
        List<CarInfoPojo> carInfoPojoList = pojoPagingInfo.getList();
        List<Long> communicationIds = new ArrayList<>(carInfoPojoList.size());
        //组装通讯号
        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            communicationIds.add(carInfoPojo.getCommunicationId().longValue());
        }
        // 获取胎压,胎温信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap = getTireDetailData(communicationIds, command.getDateStr(), isPaging, Integer.parseInt(command.getPage_size()), Integer.parseInt(command.getPage_number()));
        List<BasicDBObject> tireInfoList = (List<BasicDBObject>) resultMap.get("data");
        // 拼装返回值
        List<TireDto> tireDtos = toTireDtoList(carInfoPojoList, tireInfoList);
        PagingInfo page = new PagingInfo();
        page.setList(tireDtos);
//        page.setPage_total((Long) resultMap.get("total"));
        page.setTotal((Long) resultMap.get("total"));
        return page;
    }

}
