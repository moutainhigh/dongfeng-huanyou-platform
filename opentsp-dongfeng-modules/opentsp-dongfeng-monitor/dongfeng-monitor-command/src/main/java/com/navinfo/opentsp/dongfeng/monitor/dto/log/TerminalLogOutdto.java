package com.navinfo.opentsp.dongfeng.monitor.dto.log;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalLogOutdto {
    private BigInteger logId;//日志自增id
    private BigInteger carId;//车辆自增id
    private String chassisNo;//底盘号
    private String carNum;//车牌号
    private BigInteger simNo;//SIM卡
    private String terminalId;//终端ID
    private String team;//经销商
    private String reCustom;//所属客户
    private String dir;//指令方向
    private String type;//指令类型
    private String status;//状态
    private String operateUser;//操作人
    private String operateIp;//操作人IP
    private String operateTime;//操作时间
    private String operateTimeStr;//操作时间
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

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
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

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getOperateTimeStr() {
        return operateTimeStr;
    }

    public void setOperateTimeStr(String operateTimeStr) {
        this.operateTimeStr = operateTimeStr;
    }
}
