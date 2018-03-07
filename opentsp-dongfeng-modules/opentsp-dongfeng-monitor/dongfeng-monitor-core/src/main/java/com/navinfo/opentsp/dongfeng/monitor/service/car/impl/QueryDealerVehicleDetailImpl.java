package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.LocationUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerVehicleDetailCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.DealerVehicleDetailConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.DealerVehicleDetailPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealerVehicleDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryDealerVehicleDetailImpl extends BaseService implements IQueryDealerVehicleDetailService {

    private static final Logger logger = LoggerFactory.getLogger(QueryDealerVehicleDetailImpl.class);
    private static final long DEFAULT_INVALID_COMMID = 0;

    @Autowired
    GpsCache gpsCache;

    @Autowired
    GpsInfoCache gpsInfoCache;

    @Override
    public HttpCommandResultWithData query(final QueryDealerVehicleDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("teamId", command.getDealerId());
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("accountType", command.getUserInfor().getType());
        PagingInfo<DealerVehicleDetailPojo> datas = dao.sqlPagelFind("queryDealerVehicleDetail", parameters,
                NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), DealerVehicleDetailPojo.class);
        configGpsLocationInfos(datas);
        PagingInfo page = new PagingInfo<>();
        page.setList(DealerVehicleDetailConverter.convert(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        return result;
    }

    private void configGpsLocationInfos(final PagingInfo<DealerVehicleDetailPojo> datas) {
        List<DealerVehicleDetailPojo> vehicleDetails = datas.getList();
        if (CollectionUtils.isEmpty(vehicleDetails)) {
            logger.warn("configGpsLocationInfos#dealerVehicleDetails is empty");
            return;
        }

        for (DealerVehicleDetailPojo vehicle : vehicleDetails) {
            final long commId = getTerminalCommID(vehicle);
            if (DEFAULT_INVALID_COMMID == commId) {
                continue;
            }

            final LCLocationData.LocationData gpsData = gpsCache.getLastGpsVlid(String.valueOf(commId));
            if (StringUtil.isEmpty(gpsData)) {
                continue;
            }

            final GpsInfoData gpsInfoData = gpsInfoCache.getGpsInfo(String.valueOf(commId));
            configVehicleGpsInfos(gpsInfoData, vehicle, gpsData);
        }
    }

    private void configVehicleGpsInfos(final GpsInfoData gpsInfoData, final DealerVehicleDetailPojo vehicle, final LCLocationData.LocationData gpsData) {
        vehicle.setLastLocationDate(gpsData.getGpsDate());
        vehicle.setLastLon(LocationUtil.cvtCoordinate(gpsData.getLongitude()));
        vehicle.setLastLat(LocationUtil.cvtCoordinate(gpsData.getLatitude()));
        if (gpsData.hasStatusAddition()) {
            final List<LCVehicleStatusData.VehicleStatusData> vehicleStatus = gpsData.getStatusAddition().getStatusList();
            for (LCVehicleStatusData.VehicleStatusData status : vehicleStatus) {
                if (LCStatusType.StatusType.mileage == status.getTypes()) {
                    vehicle.setCountMilleage(String.valueOf(status.getStatusValue() / 100));
                }
                if (LCStatusType.StatusType.cumulativeRunningTime == status.getTypes()) {
                    vehicle.setCumulativeRunningTime(String.valueOf(status.getStatusValue() / 100));
                }
            }
        }
        vehicle.setDirection(gpsData.getDirection());
        if (StringUtil.isNotEmpty(gpsInfoData)) {
            vehicle.setCarStatus(gpsInfoData.getCarStatue());
        }
    }

    // 获取终端通讯号: F9优先
    private long getTerminalCommID(final DealerVehicleDetailPojo vehicle) {
        if (isValidCommID(vehicle.getFkCommunicationId())) {
            return vehicle.getFkCommunicationId().longValue();
        } else {
            return isValidCommID(vehicle.getBeidouCommunicationId()) ? vehicle.getBeidouCommunicationId().longValue() : DEFAULT_INVALID_COMMID;
        }
    }

    private boolean isValidCommID(final BigInteger communicationId) {
        return communicationId != null && communicationId.longValue() != DEFAULT_INVALID_COMMID;
    }
}
