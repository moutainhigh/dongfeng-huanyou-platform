package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 报警提醒 DTO后台接收类
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryAlarmRemindsPojo {

    private BigInteger carId;       //车辆id
    private BigInteger commId;         //通信号
    private String chassisNum;   //底盘号
    private Long alarmDate;     //报警时间
    private String alarmDateStr;     //报警时间str
    private Long alarmtimes;    //报警持续时间
    private double lat;   //报警纬度
    private double lng;  //报警经度
    private Integer alarmType;      //报警类型(1:出区域限速 2:滞留超时3:一体机拆出报警4:车辆锁车报警5:ID不匹配报警)
    private String alarmTypeStr;
    private String oldTerminalid; //旧终端id
    private String newTerminalid; //新终端id
    private Integer alarmOverType; //报警结束标志 （0：未结束  1：结束）
    private Double distance;//距离
    private String lockCarReason; //锁车原因
    private String lockCar;// 解锁车

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public Long getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Long alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmDateStr() {
        return alarmDateStr;
    }

    public void setAlarmDateStr(String alarmDateStr) {
        this.alarmDateStr = alarmDateStr;
    }

    public Long getAlarmtimes() {
        return alarmtimes;
    }

    public void setAlarmtimes(Long alarmtimes) {
        this.alarmtimes = alarmtimes;
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

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTypeStr() {
        return alarmTypeStr;
    }

    public void setAlarmTypeStr(String alarmTypeStr) {
        this.alarmTypeStr = alarmTypeStr;
    }

    public String getOldTerminalid() {
        return oldTerminalid;
    }

    public void setOldTerminalid(String oldTerminalid) {
        this.oldTerminalid = oldTerminalid;
    }

    public String getNewTerminalid() {
        return newTerminalid;
    }

    public void setNewTerminalid(String newTerminalid) {
        this.newTerminalid = newTerminalid;
    }

    public Integer getAlarmOverType() {
        return alarmOverType;
    }

    public void setAlarmOverType(Integer alarmOverType) {
        this.alarmOverType = alarmOverType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getLockCarReason() {
        return lockCarReason;
    }

    public void setLockCarReason(String lockCarReason) {
        this.lockCarReason = lockCarReason;
    }

    public String getLockCar() {
        return lockCar;
    }

    public void setLockCar(String lockCar) {
        this.lockCar = lockCar;
    }
}