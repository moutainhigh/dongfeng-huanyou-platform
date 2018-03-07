package com.navinfo.opentsp.dongfeng.report.pojo.saleremoval;

import java.math.BigInteger;

/**
 * 销售状态报表POJO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */

public class QuerySaleRemovalPojo {

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
    //经销商ID
    private BigInteger teamId;
    //经销商名称
    private String teamName;
    //经销商编码
    private String teamCode;
    // 所属客户ID
    private String carOwner;
    // 所属客户名称
    private String carOwnerName;
    //车辆型号ID
    private Integer carType;
    //车辆型号名称
    private String carTypeName;
    //发动机类型ID
    private String engType;
    //发动机类型名称
    private String engTypeName;
    //发动机型号
    private String engNumber;
    //销售日期
    private BigInteger saleDate;
    //销售日期
    private String saleDateStr;
    // 销售状态
    private Integer saleStatus;
    // 销售状态
    private String saleStatusName;
    //录入方式
    private Integer autoFlag;
    //录入方式名称
    private String autoFlagName;
    // 出库时间
    private BigInteger removalTime;
    // 出库时间
    private String removalTimeStr;
    //发票号
    private String invoiceNumber;
    //证件号
    private String businessCode;
    //末次注册时间
    private BigInteger registerTime;
    //入网时间（首次有效位置时间）
    private BigInteger nettingTime;
    //首次有效位置经度
    private BigInteger nettingLog;
    //首次有效位置纬度
    private BigInteger nettingLat;
    //下线时间
    private BigInteger offlineTime;
    //首次有效位置时间STR
    private String firstTime;
    //末次有效时间
    private String lastTime;
    //末次经纬度
    private double elat;
    //末次经纬度
    private double elog;
    //首次位置
    private String firstLocation;
    //末次位置
    private String lastLocation;

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

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
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

    public BigInteger getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(BigInteger saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSaleStatusName() {
        return saleStatusName;
    }

    public void setSaleStatusName(String saleStatusName) {
        this.saleStatusName = saleStatusName;
    }

    public Integer getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(Integer autoFlag) {
        this.autoFlag = autoFlag;
    }

    public String getAutoFlagName() {
        return autoFlagName;
    }

    public void setAutoFlagName(String autoFlagName) {
        this.autoFlagName = autoFlagName;
    }

    public BigInteger getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(BigInteger removalTime) {
        this.removalTime = removalTime;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public BigInteger getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(BigInteger registerTime) {
        this.registerTime = registerTime;
    }

    public BigInteger getNettingTime() {
        return nettingTime;
    }

    public void setNettingTime(BigInteger nettingTime) {
        this.nettingTime = nettingTime;
    }

    public BigInteger getNettingLog() {
        return nettingLog;
    }

    public void setNettingLog(BigInteger nettingLog) {
        this.nettingLog = nettingLog;
    }

    public BigInteger getNettingLat() {
        return nettingLat;
    }

    public void setNettingLat(BigInteger nettingLat) {
        this.nettingLat = nettingLat;
    }

    public BigInteger getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(BigInteger offlineTime) {
        this.offlineTime = offlineTime;
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

    public String getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public String getSaleDateStr() {
        return saleDateStr;
    }

    public void setSaleDateStr(String saleDateStr) {
        this.saleDateStr = saleDateStr;
    }

    public String getRemovalTimeStr() {
        return removalTimeStr;
    }

    public void setRemovalTimeStr(String removalTimeStr) {
        this.removalTimeStr = removalTimeStr;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }
}