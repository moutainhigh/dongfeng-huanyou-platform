package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 远程锁车pojo
 *
 * @wenya
 * @create 2017-03-29 11:24
 **/
public class LockCarPojo {
    //车辆ID
    private BigInteger carId;
    //北斗通讯号
    private BigInteger bdCommId;
    //FK通讯号
    private BigInteger fkCommId;
    //发动机类型
    private String engineType;
    //一体机终端id
    private BigInteger bdTerId;
    //fk终端id
    private BigInteger fkTerId;
    //fk终端code
    private String fkCode;
    //车牌号
    private String plateNum;
    //底盘号
    private String chassisNum;
    //经销商
    private String dealer;
    //所属客户
    private String customer;
    //在线状态
    private Integer carState;
    //终端通信号
    private BigInteger commId;
    //锁车方案
    private String lockMethod;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public BigInteger getBdCommId() {
        return bdCommId;
    }

    public void setBdCommId(BigInteger bdCommId) {
        this.bdCommId = bdCommId;
    }

    public BigInteger getFkCommId() {
        return fkCommId;
    }

    public void setFkCommId(BigInteger fkCommId) {
        this.fkCommId = fkCommId;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigInteger getBdTerId() {
        return bdTerId;
    }

    public void setBdTerId(BigInteger bdTerId) {
        this.bdTerId = bdTerId;
    }

    public BigInteger getFkTerId() {
        return fkTerId;
    }

    public void setFkTerId(BigInteger fkTerId) {
        this.fkTerId = fkTerId;
    }

    public String getFkCode() {
        return fkCode;
    }

    public void setFkCode(String fkCode) {
        this.fkCode = fkCode;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }

    public String getLockMethod() {
        return lockMethod;
    }

    public void setLockMethod(String lockMethod) {
        this.lockMethod = lockMethod;
    }
}
