package com.navinfo.opentsp.dongfeng.report.service.online;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.dataaccess.terminal.LCTerminalRecieveDateSave;
import com.lc.core.protocol.webservice.newstatisticsdata.LCGetTerminalFirstRecieveDateRes;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ObjUtils;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.online.QueryOnlineCommand;
import com.navinfo.opentsp.dongfeng.report.converter.online.OnlineConverter;
import com.navinfo.opentsp.dongfeng.report.dto.online.OnlineDto;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarInfoPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.car.CarQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant.SYSTEM_ADMIN;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class OnlineService extends BaseService implements IOnlineService {

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Autowired
    private GpsCache gpsCache;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public PagingInfo queryOnline(QueryOnlineCommand command, boolean isPaging) {
        if (StringUtil.isEmpty(command.getLocationStatus()) || command.getLocationStatus() == Constants.LocationStatusEnum.ALL.getValue()) {
            return queryOnlineNormal(command, isPaging);
        } else {
            return queryOnlineByLocationStatus(command, isPaging);
        }
    }

    /**
     * @param command
     * @param isPaging 是否分页
     * @return
     */
    private PagingInfo queryOnlineNormal(QueryOnlineCommand command, Boolean isPaging) {
        CarQueryParam param = OnlineConverter.onlineParamToCarQueryParam(command);
        //获取绑定终端的车辆
        PagingInfo<CarInfoPojo> pojoPagingInfo = queryOnlineVehicleOfBindTerminal(param, isPaging);
        List<CarInfoPojo> carInfoPojoList = pojoPagingInfo.getList();
        List<OnlineDto> onlineDtos = toOnlineDtoList(carInfoPojoList, command.getLocationStatus());
        List<Long> communicationIds = new ArrayList<>(onlineDtos.size());
        for (OnlineDto onlineDto : onlineDtos) {
            communicationIds.add(Long.valueOf(onlineDto.getCommunicationId()));
        }
        //从位置云获取首次连线时间
        List<OnlineDto> list = getFirstReceiveDate(onlineDtos, communicationIds);
        //转换为输出
        PagingInfo page = new PagingInfo();
        page.setList(list);
        page.setPage_total(pojoPagingInfo.getPage_total());
        page.setTotal(pojoPagingInfo.getTotal());
        return page;
    }

    /**
     * 分页查询同步上线车辆同步定位方式
     *
     * @param command
     * @return
     */
    private PagingInfo queryOnlineByLocationStatus(QueryOnlineCommand command, boolean isPaging) {
        PagingInfo page = new PagingInfo();
        List<OnlineDto> datas = new ArrayList<>();
        CarQueryParam param = OnlineConverter.onlineParamToCarQueryParam(command);
        //获取所以的绑定终端的车辆
        PagingInfo<CarInfoPojo> pojoPagingInfo = queryOnlineVehicleOfBindTerminal(param, false);
        List<CarInfoPojo> carInfoPojoList = pojoPagingInfo.getList();
        List<OnlineDto> onlineDtos = toOnlineDtoList(carInfoPojoList, command.getLocationStatus());
        //总数
        Integer total = onlineDtos.size();
        List<Long> communicationIds = new ArrayList<>(onlineDtos.size());
        if (isPaging) {
            //组装通讯号
            int beginIndex = (StringUtil.toInteger(command.getPage_number())) * StringUtil.toInteger(command.getPage_size());
            int endIndex = (StringUtil.toInteger(command.getPage_number()) + 1) * StringUtil.toInteger(command.getPage_size());
            if (beginIndex < onlineDtos.size()) {
                endIndex = endIndex > onlineDtos.size() ? onlineDtos.size() : endIndex;
                for (int i = beginIndex; i < endIndex; i++) {
                    communicationIds.add(Long.valueOf(onlineDtos.get(i).getCommunicationId()));
                    datas.add(onlineDtos.get(i));
                }
            }
        } else {//导出
            for (OnlineDto onlineDto : onlineDtos) {
                communicationIds.add(Long.valueOf(onlineDto.getCommunicationId()));
                datas.add(onlineDto);
            }
        }
        //从位置云获取首次连线时间
        List<OnlineDto> list = getFirstReceiveDate(datas, communicationIds);
        Integer pageSize = StringUtil.toInteger(command.getPage_size());
        // 总页数
        Integer pageTotal = (total + pageSize - 1) / pageSize;
        page.setList(list);
        page.setPage_total(pageTotal);
        page.setTotal(total);
        return page;
    }

    /**
     * 查询绑定终端的同步上线车辆
     *
     * @param queryParam
     * @param isPaging
     * @return
     */
    private PagingInfo<CarInfoPojo> queryOnlineVehicleOfBindTerminal(CarQueryParam queryParam, boolean isPaging) {
        PagingInfo<CarInfoPojo> pageList = new PagingInfo<CarInfoPojo>();
        try {
            UserTypeConstant userTypeConstant = UserTypeConstant.valueOf(queryParam.getUserType());
            if (userTypeConstant == SYSTEM_ADMIN) {
                queryParam.setAccountId(null);
            }
            Map<String, Object> paramMap = ObjUtils.toPropertyMap(queryParam);
            if (isPaging) {//分页查询
                pageList = dao.sqlPagelFind("queryOnlineVehicleOfBindTerminal", paramMap, Integer.valueOf(queryParam.getPageNum()), Integer.valueOf(queryParam.getPageSize()), CarInfoPojo.class);
            } else {//查询全部
                List<CarInfoPojo> list = dao.sqlFind("queryOnlineVehicleOfBindTerminal", paramMap, CarInfoPojo.class);
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
     * 获取首次连线时间
     *
     * @param datas            数据
     * @param communicationIds 通讯号
     * @return
     */
    private List<OnlineDto> getFirstReceiveDate(List<OnlineDto> datas, List<Long> communicationIds) {
        //从位置云获取首次连线时间
        Map<Long, Long> terminalFirstReceiveDate = getTerminalFirstReceiveDate(communicationIds);
        if (!StringUtil.isEmpty(terminalFirstReceiveDate)) {
            for (OnlineDto data : datas) {
                Long firstReceiveDate = terminalFirstReceiveDate.get(Long.valueOf(data.getCommunicationId()));
                if (!StringUtil.isEmpty(firstReceiveDate)) {
                    data.setFirstLinkTimeStr(sdf.format(firstReceiveDate * 1000));
                }
            }
        }
        return datas;
    }

    /**
     * @param carInfoPojoList 车辆信息
     * @param locationStatus  定位状态  1-同步未定位  0-同步定位
     * @return
     */
    private List<OnlineDto> toOnlineDtoList(List<CarInfoPojo> carInfoPojoList, Integer locationStatus) {
        List<OnlineDto> list = new ArrayList<>(carInfoPojoList.size());
        List<OnlineDto> haveLocation = new ArrayList<>(carInfoPojoList.size());
        List<OnlineDto> haveNotLocation = new ArrayList<>(carInfoPojoList.size());
        for (CarInfoPojo carInfoPojo : carInfoPojoList) {
            OnlineDto onlineDto = toOnlineDto(carInfoPojo);
            if (onlineDto.isLocationStatus()) {
                haveLocation.add(onlineDto);
            } else {
                haveNotLocation.add(onlineDto);
            }
            list.add(onlineDto);
        }

        if (locationStatus == Constants.LocationStatusEnum.HAVE_LOCATION.getValue()) {
            return haveLocation;
        } else if (locationStatus == Constants.LocationStatusEnum.HAVE_NOT_LOCATION.getValue()) {
            return haveNotLocation;
        } else {
            return list;
        }
    }

    /**
     * 转换数据
     *
     * @param carInfoPojo
     * @return
     */
    private OnlineDto toOnlineDto(CarInfoPojo carInfoPojo) {
        OnlineDto onlineDto = new OnlineDto();
        onlineDto.setCarId(carInfoPojo.getCarId());
        onlineDto.setChassisNum(carInfoPojo.getChassisNum());
        onlineDto.setCarCph(carInfoPojo.getCarNo());
        onlineDto.setTerId(carInfoPojo.getTerminalCode());
        onlineDto.setFkId(carInfoPojo.getFkCode());
        onlineDto.setCompanyName(carInfoPojo.getCompanyName());
        onlineDto.setDealerCode(carInfoPojo.getDealerCode());
        onlineDto.setCarOwner(carInfoPojo.getBusinessName());
        onlineDto.setCarType(carInfoPojo.getCarTypeName());
        onlineDto.setEngineType(carInfoPojo.getEngineTypeName());
        onlineDto.setEngineNumber(carInfoPojo.getEngineNumber());
        onlineDto.setCommunicationId(carInfoPojo.getCommunicationId().toString());
        onlineDto.setSaleStatus(carInfoPojo.getSalesStatusValue());
        onlineDto.setSaleDateStr(carInfoPojo.getSalesDateStr());
        onlineDto.setAutoFlag(carInfoPojo.getAutoFlagStr());
        onlineDto.setAakSaleDateStr(carInfoPojo.getAakSalesDateStr());
        // aak销售状态
        if (!StringUtil.isEmpty(carInfoPojo.getAakSalesStatus()) && carInfoPojo.getAakSalesStatus() == 0) {
            onlineDto.setAakSaleStatusStr("已售");
        } else {
            onlineDto.setAakSaleStatusStr("未售");
        }
        if (carInfoPojo.getSyncTime() != null) {
            onlineDto.setSyncDateStr(carInfoPojo.getSyncTime());
        }
        if (!StringUtil.isEmpty(carInfoPojo.getFirstGpsLng()) &&
                !StringUtil.isEmpty(carInfoPojo.getFirstGpsLat()) &&
                !StringUtil.isEmpty(carInfoPojo.getFirstGpsTimeStr())) {
            onlineDto.setFirstLat(Double.valueOf(carInfoPojo.getFirstGpsLat()));
            onlineDto.setFirstLog(Double.valueOf(carInfoPojo.getFirstGpsLng()));
            onlineDto.setFirstLocationDateStr(carInfoPojo.getFirstGpsTimeStr());
            onlineDto.setFirstLocation(carInfoPojo.getFirstGpsLng() + ";" + carInfoPojo.getFirstGpsLat());
        }
        LCLocationData.LocationData lastGps = gpsCache.getLastGps(carInfoPojo.getCommunicationId().toString());
        // 车辆是否定位判断逻辑：先判断是否有末次位置，如果没有末次位置再判断首次上线位置和时间，
        // 如果末次位置和首次上线位置，时间都为空，则为未定位
        if (!StringUtil.isEmpty(lastGps)) {
            if (lastGps.getLatitude() != 0 && lastGps.getLongitude() != 0) {
                onlineDto.setLastLat(new Long(lastGps.getLatitude()) * 0.000001);
                onlineDto.setLastLon(new Long(lastGps.getLongitude()) * 0.000001);
                onlineDto.setLastLocation(onlineDto.getLastLon() + ";" + onlineDto.getLastLat());//导出使用
                onlineDto.setLocationStatus(true);
                onlineDto.setLastLocationDateStr(DateUtils.formatDate(lastGps.getGpsDate() * 1000));
            }
        } else {
            if (!StringUtil.isEmpty(carInfoPojo.getFirstGpsLng()) &&
                    !StringUtil.isEmpty(carInfoPojo.getFirstGpsLat()) &&
                    !StringUtil.isEmpty(carInfoPojo.getFirstGpsTimeStr())) {
                //用首次位置判断其是否是定位数据
                onlineDto.setLocationStatus(true);//未定位
                onlineDto.setLastLon(Double.valueOf(carInfoPojo.getFirstGpsLng()));
                onlineDto.setLastLat(Double.valueOf(carInfoPojo.getFirstGpsLat()));
                onlineDto.setLastLocation(onlineDto.getLastLon() + ";" + onlineDto.getLastLat());//导出使用
                onlineDto.setLastLocationDateStr(carInfoPojo.getFirstGpsTimeStr());//末次位置时间
            } else {
                onlineDto.setLocationStatus(false);
            }
        }
        return onlineDto;

    }

    /**
     * 从位置云获取首次连线时间
     *
     * @param communicationId
     * @return
     */
    private Map<Long, Long> getTerminalFirstReceiveDate(List<Long> communicationId) {
        try {
            BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration.getBasicDataQueryWebService();
            byte[] response = basicDataQueryWebService.getTerminalFirstReceiveDate(communicationId);
            if (!StringUtil.isEmpty(response)) {
                LCGetTerminalFirstRecieveDateRes.GetTerminalFirstRecieveDateRes builder = LCGetTerminalFirstRecieveDateRes.GetTerminalFirstRecieveDateRes.parseFrom(response);
                int statusCode = builder.getStatusCode();
                if (statusCode == 1) {//成功
                    List<LCTerminalRecieveDateSave.TerminalRecieveDateSave> recordsList = builder.getRecordsList();
                    if (!StringUtil.isEmpty(recordsList)) {
                        Map<Long, Long> map = new HashMap<>(recordsList.size());
                        for (LCTerminalRecieveDateSave.TerminalRecieveDateSave recieveDate : recordsList) {
                            map.put(recieveDate.getTerminalId(), recieveDate.getRecieveDate());
                        }
                        return map;
                    } else {
                        logger.warn("位置云返回数据为空");
                    }
                } else {
                    logger.warn("getTerminalFirstReceiveDate 返回异常状态码");
                }
            } else {
                logger.warn(communicationId + "——getTerminalFirstReceiveDate 接口返回数据为空");
            }
        } catch (Exception e) {
            logger.error(communicationId + "获取首次连接时间异常", e);
        }
        return null;
    }
}
