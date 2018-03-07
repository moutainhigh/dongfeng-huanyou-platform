package com.navinfo.opentsp.dongfeng.report.service.station.impl;

import com.lc.core.protocol.webservice.statisticsdata.LCVehiclePassInAreaRecords;
import com.lc.core.protocol.webservice.statisticsdata.entity.LCVehiclePassInArea;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationLoadCommand;
import com.navinfo.opentsp.dongfeng.report.converter.station.StationLoadToDto;
import com.navinfo.opentsp.dongfeng.report.pojo.station.StationLoadPojo;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationLoadService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StationLoadServiceImpl extends BaseService implements IStationLoadService {
    private static final Logger logger = LoggerFactory.getLogger(StationLoadServiceImpl.class);

    @Resource
    private CloudDataRmiClientConfiguration cloudRmiClient;

    private static final int DISTRICT_SERVICE_STATION_TYPE = 5;

    private static final String QUERY_SQL_ID = "queryStationLoadInfos";

    @Override
    public HttpCommandResultWithData queryStationLoadInfos(final QueryStationLoadCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        parameters.put("stationName", command.getName());
        parameters.put("stationEnable", command.getStationEnable());
        configProvinceCityIDs(command, parameters);

        PagingInfo<StationLoadPojo> datas = new PagingInfo<>();
        if (isQueryAll) {
            List<StationLoadPojo> stations = dao.sqlFind(QUERY_SQL_ID, parameters, StationLoadPojo.class);
            datas.setList(stations);
        } else {
            datas = dao.sqlPagelFind(QUERY_SQL_ID, parameters,
                    NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), StationLoadPojo.class);
        }

        final long startTime = DateUtil.getFirstDayForMonth(command.getStatsDate(), DateUtil.date_pattern_mm);
        final long endTime = DateUtil.getLastDayForMonth(command.getStatsDate(), DateUtil.date_pattern_mm);
        configStationActualPassCounts(startTime, endTime, datas);
        PagingInfo page = new PagingInfo();
        page.setList(StationLoadToDto.convert(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);

        return result;
    }

    private void configStationActualPassCounts(final long startTime, final long endTime, final PagingInfo<StationLoadPojo> datas) {
        List<StationLoadPojo> stations = datas.getList();
        if (ListUtil.isEmpty(stations)) {
            logger.warn("StationLoadServiceImpl#configStationActualPassCounts stations is empty");
            return;
        }

        Map<Integer, StationLoadPojo> stationMaps = new HashMap<>();
        for (final StationLoadPojo station : stations) {
            stationMaps.put(station.getStationId().intValue(), station);
        }

        Map<Long, Integer> maps = getVehiclePassCountsInArea(new ArrayList<Integer>(stationMaps.keySet()), startTime, endTime);
        for (final Map.Entry<Long, Integer> entry : maps.entrySet()) {
            StationLoadPojo station = stationMaps.get(entry.getKey().intValue());
            if (!StringUtil.isEmpty(station)) {
                station.setActualTrains(entry.getValue());
            }
        }
    }

    private Map<Long, Integer> getVehiclePassCountsInArea(final List<Integer> districtCodes, final long startDate, final long endDate) {
        try {
            final byte[] bytes = getVehiclePassInArea(districtCodes, DISTRICT_SERVICE_STATION_TYPE, startDate, endDate);
            if (ArrayUtils.isEmpty(bytes)) {
                return Collections.emptyMap();
            }

            LCVehiclePassInAreaRecords.VehiclePassInAreaRecords vPassInAreaRecords = LCVehiclePassInAreaRecords.VehiclePassInAreaRecords.parseFrom(bytes);
            logger.info("StationLoadServiceImpl#getVehiclePassCountsInArea vPassInAreaRecords is {}", vPassInAreaRecords.getDataListList());
            Map<Long, Integer> passCounts = new HashMap<Long, Integer>();
            for (final LCVehiclePassInArea.VehiclePassInArea vPassInArea : vPassInAreaRecords.getDataListList()) {
                passCounts.put(vPassInArea.getId(), vPassInArea.getTimes());
            }
            return passCounts;
        } catch (Exception e) {
            logger.error("StationLoadServiceImpl#getVehiclePassCountsInArea is error ", e);
            return Collections.emptyMap();
        }
    }

    private byte[] getVehiclePassInArea(final List<Integer> districtCodes, final int type, final long startDate, final long endDate) {
        logger.info("Call cloud interface getVehiclePassInArea start");
        logger.info("parameters startDate:{}, endDate：{}, districtCodes:{}", startDate, endDate, districtCodes);
        byte[] bytes = null;
        try {
            bytes = cloudRmiClient.getDataAnalysisWebService().getVehiclePassInArea(districtCodes, type, startDate, endDate);
        } catch (Exception e) {
            logger.error("StationLoadServiceImpl#getVehiclePassInArea is error:", e);
        }
        logger.info("Call cloud interface getVehiclePassInArea results is {}", CollectionUtils.size(bytes));
        return bytes;
    }

    private void configProvinceCityIDs(final QueryStationLoadCommand command, final Map<String, Object> parameters) {
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
    }
}
