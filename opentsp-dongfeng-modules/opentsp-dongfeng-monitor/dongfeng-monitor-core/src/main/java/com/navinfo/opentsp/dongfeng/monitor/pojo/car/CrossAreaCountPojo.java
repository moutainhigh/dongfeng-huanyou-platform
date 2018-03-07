package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */

public class CrossAreaCountPojo {

    private BigInteger commId; //通讯号ID
    private int count; //次数
    private String chassisNum;//底盘号
    private String engineType;//发动机型号
    private Integer carType; //车型
    private String engineTypeValue;//发动机型号
    private String carTypeValue; //车型
    private Double mileage; //里程数
    private String engineTime; //发动机运行时长
    private String isBreakDown; //当月是否有故障
    private int carCount; //车次

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public String getEngineTypeValue() {
        return engineTypeValue;
    }

    public void setEngineTypeValue(String engineTypeValue) {
        this.engineTypeValue = engineTypeValue;
    }

    public String getCarTypeValue() {
        return carTypeValue;
    }

    public void setCarTypeValue(String carTypeValue) {
        this.carTypeValue = carTypeValue;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getEngineTime() {
        return engineTime;
    }

    public void setEngineTime(String engineTime) {
        this.engineTime = engineTime;
    }

    public String getIsBreakDown() {
        return isBreakDown;
    }

    public void setIsBreakDown(String isBreakDown) {
        this.isBreakDown = isBreakDown;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }
}