package com.navinfo.opentsp.dongfeng.monitor.dto.car;

/**
 * 报警提醒 DTO前台接收类
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryAlarmRemindsDto {

    private Long carId;       //车辆id
    private String chassisNum;   //底盘号
    private String alarmDate;     //报警时间
    private Long alarmTimes;    //报警持续时间
    private double lat;   //报警纬度
    private double lng;  //报警经度
    private Integer alarmType;      //报警类型(1:出区域限速 2:滞留超时3:一体机拆出报警4:车辆锁车报警5:ID不匹配报警)
    private String alarmTypeStr;
    private String lockCarReason; //锁车原因
    private String lockCar;// 解锁车

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public Long getAlarmTimes() {
        return alarmTimes;
    }

    public void setAlarmTimes(Long alarmTimes) {
        this.alarmTimes = alarmTimes;
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