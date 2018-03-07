package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */

public class QueryTimelyMonitorDto {

    //日期
    private String gpsdate;
    //速度
    private double velocity;
    //剩余油量
    private double oil;
    //当日里程
    private double todaymileage;
    //车辆状态
    private String accStatus;
    //车辆方向
    private String direction;
    //车门状态
    private String doorStatus;
    //报警
    private String alarm;
    //经度
    private double lat;
    //纬度
    private double lng;
    //转速
    private double rev;
    //车辆在线状态
    private Integer carStauts;
    //天然气or柴油车：0表示燃气，1表示燃油
    private Integer fuel;

    public String getGpsdate() {
        return gpsdate;
    }

    public void setGpsdate(String gpsdate) {
        this.gpsdate = gpsdate;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getOil() {
        return oil;
    }

    public void setOil(double oil) {
        this.oil = oil;
    }

    public double getTodaymileage() {
        return todaymileage;
    }

    public void setTodaymileage(double todaymileage) {
        this.todaymileage = todaymileage;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
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

    public double getRev() {
        return rev;
    }

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setRev(double rev) {
        this.rev = rev;
    }
}