package com.navinfo.opentsp.dongfeng.report.service.location.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.dataaccess.terminal.LCLastestLocationDataRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListPageUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.location.QueryLastLocationCommand;
import com.navinfo.opentsp.dongfeng.report.converter.location.VehicleLastLocationCvt;
import com.navinfo.opentsp.dongfeng.report.pojo.location.VehicleLastLocationPojo;
import com.navinfo.opentsp.dongfeng.report.service.location.ILastLocationService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
@Service
public class LastLocationServiceImpl extends BaseService implements ILastLocationService {
    private static final Logger logger = LoggerFactory.getLogger(LastLocationServiceImpl.class);

    private static final String KEY_DATAS = "datas";

    private static final String KEY_ERRORS = "errors";

    private static final int CLOUD_VALID_LOCATION_TYPE = 3;

    private static final long DEFAULT_INVALID_TIME_VALUE = -1;

    private static final ExecutorService NEW_FIXED_THREAD_POOL = Executors.newFixedThreadPool(5);

    private static CompletionService<Map<String, List<VehicleLastLocationPojo>>> completionService = new ExecutorCompletionService<Map<String, List<VehicleLastLocationPojo>>>(NEW_FIXED_THREAD_POOL);

    @Value("${lastlocation.gpsCacheQueryEnable:true}")
    private boolean gpsCacheQueryEnable;

    @Value("${lastlocation.thread.process.data.size:2000}")
    private int threadProcessDataSize;

    @Resource
    private GpsInfoCache gpsInfoCache;

    @Resource
    private CloudDataRmiClientConfiguration cloudService;

    @Override
    public HttpCommandResultWithData query(final QueryLastLocationCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> datas = new HashMap<>(16);
        Map<String, List<VehicleLastLocationPojo>> vehicles = null;
        if (StringUtil.isEmpty(command.getVin())) {
            vehicles = queryNoVins(command);
        } else {
            vehicles = queryWithVins(command);
        }

        PagingInfo pagingInfo = new PagingInfo();
        if (isQueryAll) {
            pagingInfo.setList(VehicleLastLocationCvt.convert(vehicles.get(KEY_DATAS)));
            datas.put(KEY_DATAS, pagingInfo);
        } else {
            pagingInfo = ListPageUtil.getPagingInfo(command, VehicleLastLocationCvt.convert(vehicles.get(KEY_DATAS)));
            datas.put(KEY_DATAS, pagingInfo);
        }

        datas.put(KEY_ERRORS, VehicleLastLocationCvt.convert(vehicles.get(KEY_ERRORS)));
        result.setData(datas);

        clear(null, null, null, vehicles);
        return result;
    }

    private Map<String, List<VehicleLastLocationPojo>> queryNoVins(final QueryLastLocationCommand command) {
        final long[] gpsTimes = cvtGpsFilterTimes(command);
        Map<String, List<VehicleLastLocationPojo>> filterVehicles = filter(command, new ArrayList<>());

        return processLastLocations(filterVehicles, gpsTimes[0], gpsTimes[1]);
    }

    private Map<String, List<VehicleLastLocationPojo>> queryWithVins(final QueryLastLocationCommand command) {
        List<String> vins = Arrays.asList(StringUtil.split(command.getVin(), ","));
        logger.info("queryWithVins for vins size is {}", CollectionUtils.size(vins));

        List<List<String>> splitVins = ListUtil.splitList(vins, threadProcessDataSize);
        final long[] gpsTimes = cvtGpsFilterTimes(command);
        for (final List<String> vinNums : splitVins) {
            Callable task = new Callable<Map<String, List<VehicleLastLocationPojo>>>() {
                @Override
                public Map<String, List<VehicleLastLocationPojo>> call() throws Exception {
                    Map<String, List<VehicleLastLocationPojo>> filterVehicles = filter(command, vinNums);
                    return processLastLocations(filterVehicles, gpsTimes[0], gpsTimes[1]);
                }
            };
            completionService.submit(task);
        }

        return processPoolResult(splitVins.size());
    }

    private Map<String, List<VehicleLastLocationPojo>> processLastLocations(final Map<String, List<VehicleLastLocationPojo>> filterVehicles,
                                                                            final long gpsStartDate, final long gpsEndDate) {
        logger.info("processLastLocations start");
        Map<String, List<VehicleLastLocationPojo>> locations = new HashMap<>(16);
        final List<VehicleLastLocationPojo> vehicles = filterVehicles.get(KEY_DATAS);
        List<VehicleLastLocationPojo> datas = null;
        if (gpsCacheQueryEnable) {
            datas = getLastLocationByCache(gpsStartDate, gpsEndDate, vehicles);
        } else {
            datas = getLastLocationByCloud(gpsStartDate, gpsEndDate, vehicles);
        }

        locations.put(KEY_DATAS, datas);
        locations.put(KEY_ERRORS, filterVehicles.get(KEY_ERRORS));
        clear(vehicles, null, null, filterVehicles);
        logger.info("processLastLocations end");
        return locations;
    }

    private List<VehicleLastLocationPojo> getLastLocationByCloud(final long gpsStartDate, final long gpsEndDate, final List<VehicleLastLocationPojo> vehicles) {
        List<VehicleLastLocationPojo> datas = new ArrayList<>();
        final List<Long> commIds = getCommunicationIds(vehicles);
        Map<String, LCLocationData.LocationData> locationDatas = getTerminalLocationData(commIds);
        for (VehicleLastLocationPojo vehicle : vehicles) {
            if (vehicle.getCommId() != null) {
                LCLocationData.LocationData locationData = locationDatas.get(StringUtil.valueOf(vehicle.getCommId().longValue()));
                if (locationData != null) {
                    vehicle.setGpsTime(locationData.getGpsDate());
                    vehicle.setLastValidLon(locationData.getLongitude());
                    vehicle.setLastValidLat(locationData.getLatitude());
                    vehicle.setStandardMileage(locationData.getStandardMileage());
                    vehicle.setStandardFuelCon(locationData.getStandardFuelCon());
                }
            }

            if (filterGpsTime(gpsStartDate, gpsEndDate, vehicle.getGpsTime())) {
                datas.add(vehicle);
            }
        }
        clear(null, null, locationDatas, null);
        return datas;
    }

    private List<VehicleLastLocationPojo> getLastLocationByCache(final long gpsStartDate, final long gpsEndDate, final List<VehicleLastLocationPojo> vehicles) {
        List<VehicleLastLocationPojo> datas = new ArrayList<>();
        logger.info("gpsInfoCache start");
        Map<String, GpsInfoData> gpsInfoDataMap = gpsInfoCache.getAllGpsInfoMap();
        logger.info("gpsInfoCache is size {}", CollectionUtils.size(gpsInfoDataMap));
        for (VehicleLastLocationPojo vehicle : vehicles) {
            if (vehicle.getCommId() != null) {
                GpsInfoData locationData = gpsInfoDataMap.get(StringUtil.valueOf(vehicle.getCommId().longValue()));
                if (locationData != null) {
                    if (locationData.getGpsDate() != null) {
                        vehicle.setGpsTime(locationData.getGpsDate().longValue());
                    }
                    if (locationData.getLng() != null && locationData.getLat() != null) {
                        vehicle.setLastValidLon(locationData.getLng().intValue());
                        vehicle.setLastValidLat(locationData.getLat().intValue());
                    }
                    vehicle.setStandardMileage(locationData.getTempMileage().floatValue());
                    if (locationData.getTempOilCons() != null) {
                        vehicle.setStandardFuelCon(locationData.getTempOilCons().floatValue());
                    }
                }
            }

            if (filterGpsTime(gpsStartDate, gpsEndDate, vehicle.getGpsTime())) {
                datas.add(vehicle);
            }
        }
        clear(null, gpsInfoDataMap, null, null);
        return datas;
    }


    private Map<String, List<VehicleLastLocationPojo>> processPoolResult(final int size) {
        Map<String, List<VehicleLastLocationPojo>> locations = new HashMap<>(16);
        List<VehicleLastLocationPojo> lastLocations = new ArrayList<>();
        List<VehicleLastLocationPojo> errors = new ArrayList<>();
        try {
            for (int i = 0; i < size; i++) {
                Future<Map<String, List<VehicleLastLocationPojo>>> future = completionService.take();
                lastLocations.addAll(future.get().get(KEY_DATAS));
                errors.addAll(future.get().get(KEY_ERRORS));
            }
        } catch (Exception ex) {
            logger.error("processPoolResult is error", ex);
        }
        if (NEW_FIXED_THREAD_POOL.isShutdown()) {
            NEW_FIXED_THREAD_POOL.shutdown();
        }

        logger.info("processPoolResult sort for data start");
        Collections.sort(lastLocations);
        logger.info("processPoolResult end");
        locations.put(KEY_DATAS, lastLocations);
        locations.put(KEY_ERRORS, errors);
        return locations;
    }

    private Map<String, List<VehicleLastLocationPojo>> filter(final QueryLastLocationCommand command, final List<String> vinNums) {
        logger.info("filter#queryLastLocationVehicles start");
        Map<String, List<VehicleLastLocationPojo>> result = new HashMap<>(16);
        Map<String, Object> parameters = cvtQueryParas(command, vinNums);
        List<VehicleLastLocationPojo> vehicles = dao.sqlFind("queryLastLocationVehicles", parameters, VehicleLastLocationPojo.class);
        if (CollectionUtils.isEmpty(vinNums)) {
            logger.warn("filter parameters for vinNums is empty");
            result.put(KEY_DATAS, vehicles);
            result.put(KEY_ERRORS, Collections.emptyList());
            return result;
        }
        List<VehicleLastLocationPojo> errors = getErrorVehicles(vinNums, vehicles);

        result.put(KEY_DATAS, vehicles);
        result.put(KEY_ERRORS, errors);
        logger.info("filter#queryLastLocationVehicles end");
        return result;
    }

    private List<VehicleLastLocationPojo> getErrorVehicles(List<String> vinNums, List<VehicleLastLocationPojo> vehicles) {
        List<String> errorVins = new ArrayList<>();
        for (String vin : vinNums) {
            if (!checkVinExist(vehicles, vin)) {
                errorVins.add(vin);
            }
        }

        Map<String, Object> parameters = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(errorVins)) {
            parameters.put("vins", errorVins);
        }
        List<VehicleLastLocationPojo> errors = new ArrayList<>();
        List<VehicleLastLocationPojo> vehicleLastLocations = dao.sqlFind("queryErrorVehicles", parameters, VehicleLastLocationPojo.class);
        for (String vin : errorVins) {
            if (!checkVinExist(vehicleLastLocations, vin)) {
                VehicleLastLocationPojo errorVehicle = new VehicleLastLocationPojo();
                errorVehicle.setVinNum(vin);
                errors.add(errorVehicle);
            }
        }
        return errors;
    }

    private boolean checkVinExist(final List<VehicleLastLocationPojo> vehicles, final String vin) {
        if (CollectionUtils.isEmpty(vehicles)) {
            return false;
        }
        for (VehicleLastLocationPojo vehicle : vehicles) {
            if (vin.equalsIgnoreCase(vehicle.getVinNum())) {
                return true;
            }
        }
        return false;
    }

    private long[] cvtGpsFilterTimes(final QueryLastLocationCommand command) {
        final long[] times = new long[]{DEFAULT_INVALID_TIME_VALUE, DEFAULT_INVALID_TIME_VALUE};
        if (StringUtil.isNotEmpty(command.getLastLocationStartDate())) {
            times[0] = DateUtil.strTimeChangeLong2(command.getLastLocationStartDate());
        }

        if (StringUtil.isNotEmpty(command.getLastLocationEndDate())) {
            times[1] = DateUtil.strTimeChangeLong2(command.getLastLocationEndDate());
        }
        logger.info("cvtGpsFilterTimes for times is {}", times);
        return times;
    }

    private boolean filterGpsTime(final long gpsStartDate, final long gpsEndDate, final long gpsTime) {
        if (gpsStartDate == DEFAULT_INVALID_TIME_VALUE || gpsEndDate == DEFAULT_INVALID_TIME_VALUE) {
            return true;
        }
        return gpsTime > gpsStartDate && gpsTime <= gpsEndDate;
    }

    private List<Long> getCommunicationIds(final List<VehicleLastLocationPojo> vehicles) {
        if (CollectionUtils.isEmpty(vehicles)) {
            logger.warn("getCommunicationIds for vehicles is empty");
            return Collections.emptyList();
        }

        List<Long> commIds = new ArrayList<>();
        for (VehicleLastLocationPojo location : vehicles) {
            if (location.getCommId() != null) {
                commIds.add(location.getCommId().longValue());
            }
        }
        return commIds;
    }

    private void clear(final List<VehicleLastLocationPojo> vehicles, final Map<String, GpsInfoData> gpsInfoDataMap,
                       final Map<String, LCLocationData.LocationData> locationDatas,
                       final Map<String, List<VehicleLastLocationPojo>> filterVehicles) {
        if (gpsInfoDataMap != null) {
            gpsInfoDataMap.clear();
        }
        if (vehicles != null) {
            vehicles.clear();
        }
        if (locationDatas != null) {
            locationDatas.clear();
        }
        if (filterVehicles != null) {
            filterVehicles.clear();
        }
    }

    private Map<String, Object> cvtQueryParas(final QueryLastLocationCommand command, final List<String> vinNums) {
        Map<String, Object> parameters = new HashMap<>(16);
        if (StringUtil.isNotEmpty(command.getBeginOutDate()) && StringUtil.isNotEmpty(command.getEndOutDate())) {
            parameters.put("beginOutDate", command.getBeginOutDate());
            parameters.put("endOutDate", DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(command.getEndOutDate()), 1)));
        }

        if (CollectionUtils.isNotEmpty(vinNums)) {
            parameters.put("vins", vinNums);
        }
        parameters.put("dealer", command.getDealer());
        return parameters;
    }

    private Map<String, LCLocationData.LocationData> getTerminalLocationData(final List<Long> commIds) {
        logger.info("call cloud interface for getTerminalLocationData start");
        Map<String, LCLocationData.LocationData> result = new HashMap<>(16);
        try {
            BasicDataQueryWebService basicDataQueryWebService = cloudService.getBasicDataQueryWebService();

            CommonParameterSerializer commonParameter = new CommonParameterSerializer();
            commonParameter.setMultipage(false);
            byte[] data = basicDataQueryWebService.getLastestLocationData(commIds, "", CLOUD_VALID_LOCATION_TYPE, commonParameter);
            if (data != null) {
                LCLastestLocationDataRes.LastestLocationDataRes builder = LCLastestLocationDataRes.LastestLocationDataRes
                        .parseFrom(data);
                List<LCTerminalLocationData.TerminalLocationData> list = builder.getDatasList();
                for (LCTerminalLocationData.TerminalLocationData tld : list) {
                    result.put(StringUtil.valueOf(tld.getTerminalId()), tld.getLocationData());
                }
            }
        } catch (Exception e) {
            logger.error("call cloud interface for getTerminalLocationData is error", e);
            result = Collections.emptyMap();
        }

        logger.info("call cloud interface for getTerminalLocationData end");
        return result;
    }
}
