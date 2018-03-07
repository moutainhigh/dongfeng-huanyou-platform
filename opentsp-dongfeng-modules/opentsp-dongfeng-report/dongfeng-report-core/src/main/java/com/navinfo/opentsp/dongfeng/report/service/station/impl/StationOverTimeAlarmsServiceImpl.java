package com.navinfo.opentsp.dongfeng.report.service.station.impl;

import com.lc.core.protocol.webservice.originaldata.LCDelOvertimeParkResult;
import com.lc.core.protocol.webservice.statisticsdata.LCOvertimeParkRecoreds;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCOvertimePark;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ListPageUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.report.commands.station.IgnoreStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.converter.station.StationOverTimeAlarmsToDto;
import com.navinfo.opentsp.dongfeng.report.pojo.station.StationOverTimeAlarmsPojo;
import com.navinfo.opentsp.dongfeng.report.pojo.station.StationPojo;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationOverTimeAlarmsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
public class StationOverTimeAlarmsServiceImpl extends BaseService implements IStationOverTimeAlarmsService {
    private static final Logger logger = LoggerFactory.getLogger(StationOverTimeAlarmsServiceImpl.class);
    private static final long CLOUD_ACCESSTOCKEN = 0;
    private static final long DEFAULT_INVALID_COMMID = 0;
    private static final String QUERY_VEHICLE_SQL_ID = "queryOverTimeAlarmVehicles";
    private static final String QUERY_STATION_SQL_ID = "queryServerStations";

    @Resource
    private CloudDataRmiClientConfiguration cloudRmiClient;

    @Override
    public HttpCommandResultWithData ignoreStationOverTimeAlarms(final IgnoreStationOverTimeAlarmsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        final long inBoundTime = DateUtil.strTimeChangeLong(command.getInboundTime());
        final int resultCode = delOvertimeParkRecords(CLOUD_ACCESSTOCKEN, command.getParkId(), inBoundTime);
        if (resultCode == -1) {
            result.fillResult(ReturnCode.IGNORE_STATION_OVERTIME_WARN_ERROR);
            result.setMessage(ReturnCode.IGNORE_STATION_OVERTIME_WARN_ERROR.message());
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryStationOverTimeAlarms(final QueryStationOverTimeAlarmsCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 查询报警车辆信息
        List<StationOverTimeAlarmsPojo> alarmVehicles = queryOverTimeAlarms(command);
        Map<Long, StationOverTimeAlarmsPojo> alarmVehicleMaps = new HashMap<>();
        for (final StationOverTimeAlarmsPojo alarmVehicle : alarmVehicles) {
            alarmVehicleMaps.put(getTerminalCommID(alarmVehicle), alarmVehicle);
        }

        // 查询服务站信息
        List<StationPojo> stations = queryServerStations(command);
        Map<Long, StationPojo> stationMaps = new HashMap<>();
        for (final StationPojo station : stations) {
            stationMaps.put(station.getStationId().longValue(), station);
        }

        // 查询位置云
        LCOvertimeParkRecoreds.OvertimeParkRecoreds parkRecoreds = getOvertimeParkRecordsInArea(new ArrayList<>(alarmVehicleMaps.keySet()), new ArrayList<>(stationMaps.keySet()), command);
        List<StationOverTimeAlarmsPojo> results = configStationAndAlarmInfos(alarmVehicleMaps, stationMaps, parkRecoreds.getDataListList());
        PagingInfo page = new PagingInfo();
        if (isQueryAll) {
            page.setList(StationOverTimeAlarmsToDto.convert(results));
        } else {
            page = ListPageUtil.getPagingInfo(command, StationOverTimeAlarmsToDto.convert(results));
        }
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        return result;
    }

    // 获取终端通讯号: F9优先
    private long getTerminalCommID(final StationOverTimeAlarmsPojo alarmVehicle) {
        if (isValidCommID(alarmVehicle.getFkCommunicationId())) {
            return alarmVehicle.getFkCommunicationId().longValue();
        } else {
            return isValidCommID(alarmVehicle.getBeidouCommunicationId()) ? alarmVehicle.getBeidouCommunicationId().longValue() : DEFAULT_INVALID_COMMID;
        }
    }

    private boolean isValidCommID(final BigInteger communicationId) {
        return communicationId != null && communicationId.longValue() != DEFAULT_INVALID_COMMID;
    }

    private List<StationOverTimeAlarmsPojo> configStationAndAlarmInfos(final Map<Long, StationOverTimeAlarmsPojo> alarmVehicleMaps,
                                                                       final Map<Long, StationPojo> stationMaps, final List<LCOvertimePark.OvertimePark> parks) {
        logger.info("configStationAndAlarmInfos parks size: " + parks.size());
        List<StationOverTimeAlarmsPojo> results = new ArrayList<>();
        for (final LCOvertimePark.OvertimePark park : parks) {
            final long parkingTimes = park.getContinuousTime() + park.getLimitParking();
            final long parkThreashold = park.getLimitParking();
            if (parkingTimes <= parkThreashold) {
                continue;
            }

            StationOverTimeAlarmsPojo alarm = alarmVehicleMaps.get(park.getTerminalId());
            StationPojo station = stationMaps.get(park.getAreaId());
            alarm.setStationId(station.getStationId());
            alarm.setStationName(station.getName());
            alarm.setStationAddress(station.getAddress());
            alarm.setInboundTime((park.getBeginDate() - parkThreashold) * 1000);
            alarm.setLastAlarmTime(park.getEndDate() * 1000);
            alarm.setParkingTimes(DateUtils.convert1(parkingTimes));
            alarm.setParkingThreshold(DateUtils.convert1(parkThreashold));
            alarm.setIsOvertime(Constant.OverTimeEnum.YES.getIndex());
            alarm.setParkId(park.getId());
            results.add(alarm);
        }
        return results;
    }

    private List<StationOverTimeAlarmsPojo> queryOverTimeAlarms(final QueryStationOverTimeAlarmsCommand command) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("chassisNum", command.getChassisNum());
        parameters.put("carModel", command.getModels());
        parameters.put("client", command.getClient());
        parameters.put("plateNumber", command.getPlateNumber());
        parameters.put("fkControllerID", command.getFkControllerID());
        parameters.put("dealer", command.getDealer());
        parameters.put("engineType", command.getEngineType());
        parameters.put("beidouMachineID", command.getBeidouMachineID());

        try {
            return dao.sqlFind(QUERY_VEHICLE_SQL_ID, parameters, StationOverTimeAlarmsPojo.class);
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#queryOverTimeAlarms is error ", e);
            return Collections.emptyList();
        }
    }

    private List<StationPojo> queryServerStations(final QueryStationOverTimeAlarmsCommand command) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("stationName", command.getStationName());
        if (NumberUtils.isNumber(String.valueOf(command.getAddressCode()))) {
            final int city = command.getAddressCode();
            final int province = city - city % 10000;
            if (city % 10000 > 0) {
                parameters.put("province", province);
                parameters.put("city", city);
            } else {
                parameters.put("province", province);
            }
        }
        try {
            return dao.sqlFind(QUERY_STATION_SQL_ID, parameters, StationPojo.class);
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#getServerStations is error ", e);
            return Collections.emptyList();
        }
    }

    private long[] getTimesRange(final QueryStationOverTimeAlarmsCommand command) {
        long[] times = new long[2];
        try {
            times[0] = DateUtil.strTimeChangeLong(command.getAlarmTime().substring(0, 10) + DateUtil.start);
            times[1] = DateUtil.strTimeChangeLong(command.getAlarmTime().substring(13, 23) + DateUtil.end);
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#getTimesRange is error ", e);
            return new long[2];
        }
        return times;
    }

    private LCOvertimeParkRecoreds.OvertimeParkRecoreds getOvertimeParkRecordsInArea(final List<Long> terminalCommIds, final List<Long> districtCodes, final QueryStationOverTimeAlarmsCommand command) {
        if (ListUtil.isEmpty(terminalCommIds) || ListUtil.isEmpty(districtCodes)) {
            logger.warn("getOvertimeParkRecordsInArea parameters terminalCommIds {} or districtCodes {} is empty ", CollectionUtils.size(terminalCommIds), CollectionUtils.size(districtCodes));
            return LCOvertimeParkRecoreds.OvertimeParkRecoreds.getDefaultInstance();
        }

        try {
            CommonParameterSerializer commonParameter = new CommonParameterSerializer();
            commonParameter.setMultipage(true);
            commonParameter.setCode(0);
            commonParameter.setSort(true);
            commonParameter.setSortTerminal(true);
            commonParameter.setQueryKey("");
            commonParameter.setPageIndex(Integer.parseInt(command.getPage_number()) - 1);
            commonParameter.setPageSize(Integer.parseInt(command.getPage_size()));
            final byte[] bytes = getOvertimeParkRecords(terminalCommIds, districtCodes, commonParameter, getTimesRange(command));
            if (ArrayUtils.isEmpty(bytes)) {
                return LCOvertimeParkRecoreds.OvertimeParkRecoreds.getDefaultInstance();
            }
            return LCOvertimeParkRecoreds.OvertimeParkRecoreds.parseFrom(bytes);
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#getOvertimeParkRecordsInArea is error ", e);
            return LCOvertimeParkRecoreds.OvertimeParkRecoreds.getDefaultInstance();
        }
    }

    private int delOvertimeParkRecords(final long accessTocken, final String parkId, final long recordDate) {
        logger.info("Call cloud interface delOvertimeParkRecords start");
        logger.info("parameters is accessTocken:{}, parkId:{}, recordDate:{}", accessTocken, parkId, recordDate);

        int resultCode = -1;
        try {
            byte[] result = cloudRmiClient.getDataAnalysisWebService().delOvertimeParkRecords(accessTocken, parkId, recordDate);
            if (ArrayUtils.isNotEmpty(result)) {
                resultCode = LCDelOvertimeParkResult.DelOvertimeParkResult.parseFrom(result).getStatusCode();
            }
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#delOvertimeParkRecords is error:", e);
        }
        logger.info("Call cloud interface delOvertimeParkRecords end");
        return resultCode;
    }

    private byte[] getOvertimeParkRecords(final List<Long> terminalCommIds, final List<Long> districtCodes,
                                          final CommonParameterSerializer commonParameter, final long[] timeRanges) {
        logger.info("Call cloud interface getOvertimeParkRecords start");
        logger.info("parameters is startDate:{}, endDate:{}, districtCodes:{}, terminalCommIds:{}", timeRanges[0], timeRanges[1], districtCodes, terminalCommIds);
        byte[] bytes = null;
        try {
            bytes = cloudRmiClient.getDataAnalysisWebService().getOvertimeParkRecords(terminalCommIds, districtCodes, timeRanges[0], timeRanges[1], commonParameter);
        } catch (Exception e) {
            logger.error("StationOverTimeAlarmsServiceImpl#getOvertimeParkRecords is error:", e);
        }
        logger.info("Call cloud interface getOvertimeParkRecords result size is {}", CollectionUtils.size(bytes));
        return bytes;
    }
}
