package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * 轨迹回放:时间段内总里程、时间段内总油耗 DTO前台接收类
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryConsumptionDto {
    //时间段内总里程
    double mileage;
    //时间段内总油耗
    Float oilConsumption;

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public Float getOilConsumption() {
        return oilConsumption;
    }

    public void setOilConsumption(Float oilConsumption) {
        this.oilConsumption = oilConsumption;
    }
}