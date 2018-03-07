package com.navinfo.opentsp.dongfeng.report.dto.online;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
public class OnlineDto {
    //车辆基本信息
    private BigInteger carId; //车辆ID
    private String chassisNum;//底盘号
    private String carCph;//车牌号
    private String terId; //终端ID
    private String fkId; //FK控制器ID
    private String companyName;//所属经销商
    private String dealerCode; //经销商代码
    private String carOwner; //所属客户（企业，车主）
    private String carType; //车辆型号
    private String engineType; //发动机类型
    private String engineNumber; //发动机型号
    private String saleDateStr;//销售时间
    private String saleStatus;//销售状态
    private String aakSaleDateStr;//aak销售时间
    private String aakSaleStatusStr;//aak销售状态
    private String autoFlag;//录入方式（DMS，平台)
    private String syncDateStr;//同步时间
    private Double firstLog;//入网经度
    private Double firstLat;//入网纬度
    private String firstLocation;//首次位置信息
    private String firstLocationDateStr;//首次位置时间
    private Double lastLon;//最新经度
    private Double lastLat;//最新纬度
    private String lastLocation;//末次位置信息
    private String lastLocationDateStr;//末次位置时间
    private String firstLinkTimeStr;//首次连线时间
    private String communicationId;//通讯号
    private boolean locationStatus;//定位状态

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

    public String getTerId() {
        return terId;
    }

    public void setTerId(String terId) {
        this.terId = terId;
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getSaleDateStr() {
        return saleDateStr;
    }

    public void setSaleDateStr(String saleDateStr) {
        this.saleDateStr = saleDateStr;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(String autoFlag) {
        this.autoFlag = autoFlag;
    }

    public String getSyncDateStr() {
        return syncDateStr;
    }

    public void setSyncDateStr(String syncDateStr) {
        this.syncDateStr = syncDateStr;
    }

    public Double getFirstLog() {
        return firstLog;
    }

    public void setFirstLog(Double firstLog) {
        this.firstLog = firstLog;
    }

    public Double getFirstLat() {
        return firstLat;
    }

    public void setFirstLat(Double firstLat) {
        this.firstLat = firstLat;
    }

    public String getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Double getLastLon() {
        return lastLon;
    }

    public void setLastLon(Double lastLon) {
        this.lastLon = lastLon;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getLastLocationDateStr() {
        return lastLocationDateStr;
    }

    public void setLastLocationDateStr(String lastLocationDateStr) {
        this.lastLocationDateStr = lastLocationDateStr;
    }

    public String getFirstLinkTimeStr() {
        return firstLinkTimeStr;
    }

    public void setFirstLinkTimeStr(String firstLinkTimeStr) {
        this.firstLinkTimeStr = firstLinkTimeStr;
    }

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public boolean isLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(boolean locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getFirstLocationDateStr() {
        return firstLocationDateStr;
    }

    public void setFirstLocationDateStr(String firstLocationDateStr) {
        this.firstLocationDateStr = firstLocationDateStr;
    }

    public String getAakSaleDateStr() {
        return aakSaleDateStr;
    }

    public void setAakSaleDateStr(String aakSaleDateStr) {
        this.aakSaleDateStr = aakSaleDateStr;
    }

    public String getAakSaleStatusStr() {
        return aakSaleStatusStr;
    }

    public void setAakSaleStatusStr(String aakSaleStatusStr) {
        this.aakSaleStatusStr = aakSaleStatusStr;
    }
}
