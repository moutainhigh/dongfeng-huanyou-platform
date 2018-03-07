package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 防控激活pojo
 *
 * @wenya
 * @create 2017-03-29 11:24
 **/
public class TamperPojo {
    //车辆ID
    private BigInteger carId;
    //北斗通讯号
    private BigInteger bdCommId;
    //FK通讯号
    private BigInteger fkCommId;
    //发动机类型
    private String engineType;
    //一体机终端id
    private BigInteger terminalId;
    //fk终端id
    private BigInteger car_termianlId;
    //fk终端code
    private String fkCode;
    //车牌号
    private String carNum;
    //底盘号
    private String chassisNum;
    //经销商
    private String team;
    //所属客户
    private String reCustom;
    //在线状态
    private Integer carState;

    public Integer getCarState() {
        return carState;
    }

    public void setCarState(Integer carState) {
        this.carState = carState;
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

    public String getReCustom() {
        return reCustom;
    }

    public void setReCustom(String reCustom) {
        this.reCustom = reCustom;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFkCode() {
        return fkCode;
    }

    public void setFkCode(String fkCode) {
        this.fkCode = fkCode;
    }

    public BigInteger getBdCommId() {
        return bdCommId;
    }

    public void setBdCommId(BigInteger bdCommId) {
        this.bdCommId = bdCommId;
    }

    public BigInteger getCar_termianlId() {
        return car_termianlId;
    }

    public void setCar_termianlId(BigInteger car_termianlId) {
        this.car_termianlId = car_termianlId;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigInteger getFkCommId() {
        return fkCommId;
    }

    public void setFkCommId(BigInteger fkCommId) {
        this.fkCommId = fkCommId;
    }

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }
}
