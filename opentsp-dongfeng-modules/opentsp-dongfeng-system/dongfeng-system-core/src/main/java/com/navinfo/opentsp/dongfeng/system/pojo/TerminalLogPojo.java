package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalLogPojo {
    private BigInteger logId;//日志自增id
    private BigInteger carId;//车辆自增id
    private String chassisNum;//底盘号
    private String carNum;//车牌号
    private BigInteger simNo;//SIM卡
    private String terminalId;//终端ID
    private String team;//经销商
    private String reCustom;//所属客户
    private Integer dir;//指令方向
    private String type;//指令类型
    private Integer status;//状态
    private String operateUser;//操作人
    private String operateIp;//操作人IP
    private BigInteger operateTime;//操作时间
    private String des;//操作描述

    public BigInteger getLogId() {
        return logId;
    }

    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
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

    public BigInteger getSimNo() {
        return simNo;
    }

    public void setSimNo(BigInteger simNo) {
        this.simNo = simNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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

    public Integer getDir() {
        return dir;
    }

    public void setDir(Integer dir) {
        this.dir = dir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public BigInteger getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(BigInteger operateTime) {
        this.operateTime = operateTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
