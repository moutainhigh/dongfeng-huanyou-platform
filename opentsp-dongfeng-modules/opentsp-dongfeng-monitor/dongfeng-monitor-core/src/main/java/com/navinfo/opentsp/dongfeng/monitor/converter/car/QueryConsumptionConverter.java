package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryConsumptionDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryConsumptionPojo;

/**
 * 时间段内总里程、时间段内总油耗  POJO>>DTO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryConsumptionConverter {
    /**
     * JPJO>>DTO
     * @param consumptionPojo
     * @return
     */
    public static QueryConsumptionDto consumptionConverter(QueryConsumptionPojo consumptionPojo){
        QueryConsumptionDto consumptionDto = new QueryConsumptionDto();
        if(null!=consumptionPojo){
            consumptionDto.setMileage(consumptionPojo.getMileage());
            consumptionDto.setOilConsumption(consumptionPojo.getOilConsumption());
        }
        return consumptionDto;
    }
}