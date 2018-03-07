package com.navinfo.opentsp.dongfeng.monitor.dto.car;

import org.apache.commons.lang.StringUtils;

/**
 * 查询实时数据
 * @author fengwm
 * @version 1.0
 * @date 2017-10-19
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTimelyDataDto {
    //时间
    private String gpsTime;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //高程
    private int height;
    //发动机输出扭矩
    private  double engineOutputTorque;
    //综合车速
    private double speed;
    //油门
    private double accelerator;
    //制动信号
    private String brake;
    //发动机转速
    private int rotation;
    //挡位
    private int gear;
    //离合器开关
    private String clutchSwitch;
    //发动机瞬时油耗
    private double realTimeOilConsumption;
    //发动机燃油消耗率
    private double fuelConsumptionRate;
   //位置
    private String address;

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getEngineOutputTorque() {
        return engineOutputTorque;
    }

    public void setEngineOutputTorque(double engineOutputTorque) {
        this.engineOutputTorque = engineOutputTorque;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAccelerator() {
        return accelerator;
    }

    public void setAccelerator(double accelerator) {
        this.accelerator = accelerator;
    }

    public String getBrake() {
        return brake;
    }

    public void setBrake(String brake) {
        if(StringUtils.isNotEmpty(brake)){
            if("0".equals(brake)){
                this.brake = "制动踏板被松开";
            }
            if("1".equals(brake)){
                this.brake = "制动踏板被踩下";
            }
            if("2".equals(brake)){
                this.brake = "出错";
            }
            if("3".equals(brake)){
                this.brake = "不可用";
            }
        }
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public String getClutchSwitch() {
        return clutchSwitch;
    }

    public void setClutchSwitch(String clutchSwitch) {
        if(StringUtils.isNotEmpty(clutchSwitch)){
            if("0".equals(clutchSwitch)){
                this.clutchSwitch = "离合器踏板被松开";
            }
            if("1".equals(clutchSwitch)){
                this.clutchSwitch = "离合器踏板被踩下";
            }
        }
    }

    public double getRealTimeOilConsumption() {
        return realTimeOilConsumption;
    }

    public void setRealTimeOilConsumption(double realTimeOilConsumption) {
        this.realTimeOilConsumption = realTimeOilConsumption;
    }

    public double getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(double fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}