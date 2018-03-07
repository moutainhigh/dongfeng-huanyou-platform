package com.navinfo.opentsp.dongfeng.report.converter.location;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.LocationUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.dto.location.VehicleLastLocationDto;
import com.navinfo.opentsp.dongfeng.report.pojo.location.VehicleLastLocationPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
public class VehicleLastLocationCvt {
    private static final long DEFAULT_INVALID_VALUE = 0;

    public static List<VehicleLastLocationDto> convert(List<VehicleLastLocationPojo> datas) {
        if (ListUtil.isEmpty(datas)) {
            return Collections.emptyList();
        }
        List<VehicleLastLocationDto> results = new ArrayList<>();
        for (VehicleLastLocationPojo vehicle : datas) {
            VehicleLastLocationDto dto = new VehicleLastLocationDto();
            dto.setPlateNum(vehicle.getPlateNum());
            dto.setChassisNum(vehicle.getChassisNum());
            dto.setVinNum(vehicle.getVinNum());
            dto.setOutDate(DateUtil.formatTime(vehicle.getOutDate()));
            dto.setDealerCode(vehicle.getDealerCode());
            dto.setDealerName(vehicle.getDealerName());

            if (vehicle.getGpsTime() != DEFAULT_INVALID_VALUE) {
                dto.setGpsTime(DateUtil.timeStr(vehicle.getGpsTime()));
            }
            if (vehicle.getLastValidLat() != DEFAULT_INVALID_VALUE) {
                dto.setLastValidLat(StringUtil.valueOf(LocationUtil.cvtCoordinate(vehicle.getLastValidLat())));
            }
            if (vehicle.getLastValidLon() != DEFAULT_INVALID_VALUE) {
                dto.setLastValidLon(StringUtil.valueOf(LocationUtil.cvtCoordinate(vehicle.getLastValidLon())));
            }
            dto.setStandardMileage(StringUtil.nvlDef(vehicle.getStandardMileage()));
            dto.setStandardFuelCon(StringUtil.nvlDef(vehicle.getStandardFuelCon()));
            results.add(dto);
        }
        return results;
    }
}
