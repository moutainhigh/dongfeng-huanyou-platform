package com.navinfo.opentsp.dongfeng.monitor.pojo.risk;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
public class RiskRulePojo {
    private BigInteger ruleId;//防控规则ID
    private BigInteger regionId;//区域ID
    private BigInteger vehicleId;//车辆ID
    private String chassisNum;//底盘号
    private String carNum;//车牌号
    private String carOwner;//客户
    private Integer type;//防控类型
    private Integer value;//防控值
    private Timestamp operateTime;//操作时间
    private String operateUser;//操作人
    private String mark;//描述

    public BigInteger getRuleId() {
        return ruleId;
    }

    public void setRuleId(BigInteger ruleId) {
        this.ruleId = ruleId;
    }

    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    public BigInteger getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(BigInteger vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }
}
