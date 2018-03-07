package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryDealerVehicleDetailDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.DealerVehicleDetailPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
public class DealerVehicleDetailConverter {
    public static List<QueryDealerVehicleDetailDto> convert(List<DealerVehicleDetailPojo> datas) {
        List<QueryDealerVehicleDetailDto> results = new ArrayList<>();
        if (ListUtil.isEmpty(datas)){
            return  results;
        }

        for (DealerVehicleDetailPojo vehicleDetail : datas) {
            QueryDealerVehicleDetailDto dto = new QueryDealerVehicleDetailDto();
            dto.setChassisNum(vehicleDetail.getChassisNum());
            dto.setPlateNum(vehicleDetail.getPlateNum());
            dto.setCarType(vehicleDetail.getCarType());
            dto.setEngineType(vehicleDetail.getEngineType());
            dto.setCountMilleage(vehicleDetail.getCountMilleage());
            dto.setCumulativeRunningTime(vehicleDetail.getCumulativeRunningTime());
            if (StringUtil.isNotEmpty(vehicleDetail.getDmsSaleDate())) {
                dto.setDmsSaleDate(DateUtils.formatDate(vehicleDetail.getDmsSaleDate().longValue() * 1000));
            }
            if (StringUtil.isNotEmpty(vehicleDetail.getAccessDate())) {
                dto.setAccessDate(DateUtils.formatDate(vehicleDetail.getAccessDate().longValue() * 1000));
            }
            if (StringUtil.isNotEmpty(vehicleDetail.getLastLocationDate())) {
                dto.setLastLocationDate(DateUtils.formatDate(vehicleDetail.getLastLocationDate() * 1000));
            }
            dto.setLastLat(StringUtil.nvl(vehicleDetail.getLastLat()));
            dto.setLastLon(StringUtil.nvl(vehicleDetail.getLastLon()));
            dto.setLockStauts(StringUtil.nvl(vehicleDetail.getLockStauts()));
            dto.setDirection(StringUtil.nvl(vehicleDetail.getDirection()));
            dto.setCarStatus(StringUtil.nvl(vehicleDetail.getCarStatus()));
            results.add(dto);
        }
        return  results;
    }
}
