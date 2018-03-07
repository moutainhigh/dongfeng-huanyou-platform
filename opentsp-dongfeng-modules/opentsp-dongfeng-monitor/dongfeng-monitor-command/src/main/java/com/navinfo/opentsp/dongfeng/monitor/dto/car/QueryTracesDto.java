package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * 轨迹回放:轨迹点 DTO前台接收类
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTracesDto {
    private String address;
    //报警
    private String alarm;
    //故障
    private String fault;
    //时间
    private String gpsdate;
    //经度
    private  double lat;
    //纬度
    private double lng;
    //剩余油量
    private String oilwear;
    //状态
    private String statue;
    //整车油耗
    private String totalFuelConsumption;
    //整车里程
    private String totolmileage;
    //速度
    private String velocity;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getGpsdate() {
        return gpsdate;
    }

    public void setGpsdate(String gpsdate) {
        this.gpsdate = gpsdate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getOilwear() {
        return oilwear;
    }

    public void setOilwear(String oilwear) {
        this.oilwear = oilwear;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public void setTotalFuelConsumption(String totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public String getTotolmileage() {
        return totolmileage;
    }

    public void setTotolmileage(String totolmileage) {
        this.totolmileage = totolmileage;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }
}