package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryTracesDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTracesPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间段内总里程、时间段内总油耗  POJO>>DTO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTracesConverter {
    /**
     * JPJO>>DTO
     * @param entityList
     * @return
     */
    public static List<QueryTracesDto> convertToDtoList(List<QueryTracesPojo> entityList) {
        List<QueryTracesDto> result = new ArrayList<QueryTracesDto>();
        for (QueryTracesPojo entity : entityList) {
            QueryTracesDto dto = new QueryTracesDto();
            dto.setAlarm(entity.getAlarm());
            dto.setFault(entity.getFault());
            dto.setGpsdate(entity.getGpsdate());
            dto.setLat(entity.getLat());
            dto.setLng(entity.getLng());
            dto.setOilwear(entity.getOilwear());
            dto.setStatue(entity.getStatue());
            dto.setTotalFuelConsumption(entity.getTotalFuelConsumption());
            dto.setTotolmileage(entity.getTotolmileage());
            dto.setVelocity(entity.getVelocity());
            result.add(dto);
        }
        return result;
    }
}