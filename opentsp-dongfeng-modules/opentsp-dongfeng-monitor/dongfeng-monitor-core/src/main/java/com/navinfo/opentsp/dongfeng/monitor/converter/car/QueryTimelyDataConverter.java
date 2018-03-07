package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryTimelyDataDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTimelyDataPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-10-25
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTimelyDataConverter {
    /**
     * JPJO>>DTO
     * @param entityList
     * @return
     */
    public static List<QueryTimelyDataDto> convertToDtoList(List<QueryTimelyDataPojo> entityList) {
        List<QueryTimelyDataDto> result = new ArrayList<QueryTimelyDataDto>();
        for (QueryTimelyDataPojo po : entityList) {
            QueryTimelyDataDto dto = new QueryTimelyDataDto();
			dto.setGpsTime(DateUtil.timeStrSSS(po.getGpsTime()));
            dto.setLatitude((double)po.getLatitude() / 1000000L);
            dto.setLongitude((double)po.getLongitude()/ 1000000L);
            dto.setHeight(po.getHeight());
            dto.setEngineOutputTorque(NumberFormatUtil.getDoubleValueData((double)po.getEngineOutputTorque()/100,2));
            dto.setSpeed(NumberFormatUtil.getDoubleValueData((double)po.getSpeed() / 100, 2));
            dto.setAccelerator(NumberFormatUtil.getDoubleValueData((double)po.getAccelerator() / 100, 2));
            dto.setBrake(String.valueOf(po.getBrake()/100));
            dto.setRotation(po.getRotation()/100);
            dto.setGear(po.getGear()/100);
            dto.setClutchSwitch(String.valueOf((int)po.getClutchSwitch()/100));
            dto.setRealTimeOilConsumption(NumberFormatUtil.getDoubleValueData((double) po.getRealTimeOilConsumption() / 100, 2));
            dto.setFuelConsumptionRate(NumberFormatUtil.getDoubleValueData((double) po.getFuelConsumptionRate() / 100, 2));
            dto.setAddress((double)po.getLongitude()/ 1000000L+ ";" + (double)po.getLatitude()/ 1000000L);
            result.add(dto);
        }
        return result;
    }
}