package com.navinfo.opentsp.dongfeng.report.dto.saleremoval;

import java.math.BigInteger;

/**
 * 出库销售报表DTO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */

public class QuerySaleRemovalDto {

    //车辆ID
    private BigInteger carId;
    //底盘号
    private String chassisNum;
    //车牌号
    private String carCph;
    // 终端CODE
    private String terminalBDCode;
    // FK终端code
    private String terminalFKCode;
    // 北斗终端通讯号
    private BigInteger bdCommunicationId;
    // FK终端通讯号
    private BigInteger fkCommunicationId;
//    //经销商ID
//    private BigInteger teamId;
    //经销商名称
    private String teamName;
    //经销商编码
    private String teamCode;
//    // 所属客户ID
//    private String carOwner;
    // 所属客户名称
    private String carOwnerName;
//    //车辆型号ID
//    private Integer carType;
    //车辆型号名称
    private String carTypeName;
    //发动机类型ID
    private String engType;
    //发动机类型名称
    private String engTypeName;
    //发动机型号
    private String engNumber;
    //销售日期
    private String saleDate;
//    // 销售状态
//    private Integer saleStatus;
    // 销售状态
    private String saleStatusName;
//    //录入方式
//    private Integer autoFlag;
    //录入方式名称
    private String autoFlagName;
    // 出库时间
    private String removalTime;
//    //发票号
//    private String invoiceNumber;
//    //证件号
//    private String businessCode;
//    //末次注册时间
//    private String registerTime;
    //入网时间（首次有效位置时间）
    private String nettingTime;
    //首次有效位置经度
    private double log;
    //首次有效位置纬度
    private double lat;
//    //下线时间
//    private String offlineTime;
    //末次有效时间
    private String lastTime;
    //末次经纬度
    private double elat;
    private double elog;

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

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public String getTerminalBDCode() {
        return terminalBDCode;
    }

    public void setTerminalBDCode(String terminalBDCode) {
        this.terminalBDCode = terminalBDCode;
    }

    public String getTerminalFKCode() {
        return terminalFKCode;
    }

    public void setTerminalFKCode(String terminalFKCode) {
        this.terminalFKCode = terminalFKCode;
    }

    public BigInteger getBdCommunicationId() {
        return bdCommunicationId;
    }

    public void setBdCommunicationId(BigInteger bdCommunicationId) {
        this.bdCommunicationId = bdCommunicationId;
    }

    public BigInteger getFkCommunicationId() {
        return fkCommunicationId;
    }

    public void setFkCommunicationId(BigInteger fkCommunicationId) {
        this.fkCommunicationId = fkCommunicationId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getEngType() {
        return engType;
    }

    public void setEngType(String engType) {
        this.engType = engType;
    }

    public String getEngTypeName() {
        return engTypeName;
    }

    public void setEngTypeName(String engTypeName) {
        this.engTypeName = engTypeName;
    }

    public String getEngNumber() {
        return engNumber;
    }

    public void setEngNumber(String engNumber) {
        this.engNumber = engNumber;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleStatusName() {
        return saleStatusName;
    }

    public void setSaleStatusName(String saleStatusName) {
        this.saleStatusName = saleStatusName;
    }

    public String getAutoFlagName() {
        return autoFlagName;
    }

    public void setAutoFlagName(String autoFlagName) {
        this.autoFlagName = autoFlagName;
    }

    public String getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(String removalTime) {
        this.removalTime = removalTime;
    }

    public String getNettingTime() {
        return nettingTime;
    }

    public void setNettingTime(String nettingTime) {
        this.nettingTime = nettingTime;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public double getElat() {
        return elat;
    }

    public void setElat(double elat) {
        this.elat = elat;
    }

    public double getElog() {
        return elog;
    }

    public void setElog(double elog) {
        this.elog = elog;
    }
}