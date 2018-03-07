package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

/**
 * 查询实时数据
 * @author fengwm
 * @version 1.0
 * @date 2017-10-19
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTimelyDataPojo {
    //时间
    private long gpsTime;
    //纬度
    private int latitude;
    //经度
    private int longitude;
    //高程
    private int height;
    //发动机输出扭矩
    private  int engineOutputTorque;
    //综合车速
    private int speed;
    //油门
    private int accelerator;
    //制动信号
    private int brake;
    //发动机转速
    private int rotation;
    //挡位
    private int gear;
    //离合器开关
    private int clutchSwitch;
    //发动机瞬时油耗
    private int realTimeOilConsumption;
    //发动机燃油消耗率
    private int fuelConsumptionRate;
   //位置
    private String address;

    public long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getEngineOutputTorque() {
        return engineOutputTorque;
    }

    public void setEngineOutputTorque(int engineOutputTorque) {
        this.engineOutputTorque = engineOutputTorque;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAccelerator() {
        return accelerator;
    }

    public void setAccelerator(int accelerator) {
        this.accelerator = accelerator;
    }

    public int getBrake() {
        return brake;
    }

    public void setBrake(int brake) {
        this.brake = brake;
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

    public int getClutchSwitch() {
        return clutchSwitch;
    }

    public void setClutchSwitch(int clutchSwitch) {
        this.clutchSwitch = clutchSwitch;
    }

    public int getRealTimeOilConsumption() {
        return realTimeOilConsumption;
    }

    public void setRealTimeOilConsumption(int realTimeOilConsumption) {
        this.realTimeOilConsumption = realTimeOilConsumption;
    }

    public int getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }

    public void setFuelConsumptionRate(int fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}