package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * 终端关联的车辆，经销商，客户信息
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-16
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalCompleteInfoPojo {
    private BigInteger tid;//终端自增主键ID
    private String terminalId;//终端ID
    private BigInteger tCommunicationId;//北斗一体机终端通讯号
    private BigInteger tAutoCommunicationId;//FK控制器终端通讯号
    private Integer tType;//终端类型
    private String simNo;//simNo
    private BigInteger cid;//车辆主键自增ID
    private BigInteger carTerminal;//车辆绑定的北斗一体机终端主键ID
    private BigInteger carTerminalId;//车辆绑定的FK控制器终端主键ID
    private BigInteger communicationId;//车辆有效的终端通讯号
    private String carNum;//车牌号
    private String chassisNum;//底盘号
    private BigInteger carOwnerId;//车主ID
    private String carOwnerName;//车主名称
    private BigInteger teamId;//所属经销商ID
    private String teamName;//所属经销商名称

    public BigInteger getTid() {
        return tid;
    }

    public void setTid(BigInteger tid) {
        this.tid = tid;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public BigInteger gettCommunicationId() {
        return tCommunicationId;
    }

    public void settCommunicationId(BigInteger tCommunicationId) {
        this.tCommunicationId = tCommunicationId;
    }

    public BigInteger gettAutoCommunicationId() {
        return tAutoCommunicationId;
    }

    public void settAutoCommunicationId(BigInteger tAutoCommunicationId) {
        this.tAutoCommunicationId = tAutoCommunicationId;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public BigInteger getCid() {
        return cid;
    }

    public void setCid(BigInteger cid) {
        this.cid = cid;
    }

    public BigInteger getCarTerminal() {
        return carTerminal;
    }

    public void setCarTerminal(BigInteger carTerminal) {
        this.carTerminal = carTerminal;
    }

    public BigInteger getCarTerminalId() {
        return carTerminalId;
    }

    public void setCarTerminalId(BigInteger carTerminalId) {
        this.carTerminalId = carTerminalId;
    }

    public BigInteger getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(BigInteger communicationId) {
        this.communicationId = communicationId;
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

    public BigInteger getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(BigInteger carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
