package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-27
 * @modify
 * @copyright Navi Tsp
 */

public class VoiceMonitoringPojo {

    //车辆ID
    private BigInteger carId;
    //北斗通讯号
    private BigInteger bdCommId;
    //FK通讯号
    private BigInteger fkCommId;
    //车牌号
    private String carNum;
    //底盘号
    private String chassisNum;
    //经销商
    private String team;
    //所属客户
    private String reCustom;

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

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getReCustom() {
        return reCustom;
    }

    public void setReCustom(String reCustom) {
        this.reCustom = reCustom;
    }

    public void setFkCommId(BigInteger fkCommId) {
        this.fkCommId = fkCommId;
    }
}