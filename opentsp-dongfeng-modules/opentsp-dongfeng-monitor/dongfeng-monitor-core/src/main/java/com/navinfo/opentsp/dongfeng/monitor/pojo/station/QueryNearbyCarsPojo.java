package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import java.math.BigInteger;

/**
 * 服务站附近车辆
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */

public class QueryNearbyCarsPojo {

    //车辆主键
    private BigInteger id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //车型
    private String carModel;
    //发动机类型
    private String engineType;
    //通信号
    private BigInteger commId;
    //距离
    private Double distance;
    //最近上报时间
    private String lastGpsTime;
    //末次有效位置经度
    private Double lastGpsLng;
    //末次有效位置纬度
    private Double lastGpsLat;
    //整车里程
    private Double totalMilleage;
    //预约号
    private String appoId;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getLastGpsTime() {
        return lastGpsTime;
    }

    public void setLastGpsTime(String lastGpsTime) {
        this.lastGpsTime = lastGpsTime;
    }

    public Double getLastGpsLng() {
        return lastGpsLng;
    }

    public void setLastGpsLng(Double lastGpsLng) {
        this.lastGpsLng = lastGpsLng;
    }

    public Double getLastGpsLat() {
        return lastGpsLat;
    }

    public void setLastGpsLat(Double lastGpsLat) {
        this.lastGpsLat = lastGpsLat;
    }

    public Double getTotalMilleage() {
        return totalMilleage;
    }

    public void setTotalMilleage(Double totalMilleage) {
        this.totalMilleage = totalMilleage;
    }

    public String getAppoId() {
        return appoId;
    }

    public void setAppoId(String appoId) {
        this.appoId = appoId;
    }
}