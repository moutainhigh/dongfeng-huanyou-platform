package com.navinfo.opentsp.dongfeng.report.dto.oil;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */
public class OilDto implements Serializable {
    private static final long serialVersionUID = 8147457477411610349L;
    //车辆基本信息
    private BigInteger carId; //车辆ID
    private String chassisNum;//底盘号
    private String carCph;//车牌号
    private String terId; //终端ID
    private String fkId; //FK控制器ID
    private String companyName;//所属经销商
    private String carOwner; //所属客户（企业，车主）
    private String carType; //车辆型号
    private String engineType; //发动机类型
    private String engineNumber; //发动机型号
    private String communicationId;//通讯号
    //位置云获取的油耗信息
    private Long beginDate;//起始时间
    private String beginDateStr;
    private Long endDate;//结束时间
    private String endDateStr;//结束时间
    private String beginLocation;//起始位置
    private String endLocation;//结束位置
    private Double bLog;//开始经度
    private Double bLat;//开始纬度
    private Double eLog;//结束经度
    private Double eLat;//结束纬度
    private String mileage;//行驶里程：can里程
    private String fuelCspDaily;//油量消耗：每天燃油消耗(毫升)
    private String fuelCspRate;//发动机油耗：发动机燃油消耗率（毫升/小时）
    private String fuelCspTotalRate;//平均百公里能耗=整车油耗/整车里程*100）
    private String isException;//是否有疑似异常：1表示正常，0表示异常

    private String lastTirePressure;// 最新胎压
    private String lastTireTempreture;// 最新胎温


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

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getBeginLocation() {
        return beginLocation;
    }

    public void setBeginLocation(String beginLocation) {
        this.beginLocation = beginLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Double getbLog() {
        return bLog;
    }

    public void setbLog(Double bLog) {
        this.bLog = bLog;
    }

    public Double getbLat() {
        return bLat;
    }

    public void setbLat(Double bLat) {
        this.bLat = bLat;
    }

    public Double geteLog() {
        return eLog;
    }

    public void seteLog(Double eLog) {
        this.eLog = eLog;
    }

    public Double geteLat() {
        return eLat;
    }

    public void seteLat(Double eLat) {
        this.eLat = eLat;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getFuelCspDaily() {
        return fuelCspDaily;
    }

    public void setFuelCspDaily(String fuelCspDaily) {
        this.fuelCspDaily = fuelCspDaily;
    }

    public String getFuelCspRate() {
        return fuelCspRate;
    }

    public void setFuelCspRate(String fuelCspRate) {
        this.fuelCspRate = fuelCspRate;
    }

    public String getFuelCspTotalRate() {
        return fuelCspTotalRate;
    }

    public void setFuelCspTotalRate(String fuelCspTotalRate) {
        this.fuelCspTotalRate = fuelCspTotalRate;
    }

    public String getIsException() {
        return isException;
    }

    public void setIsException(String isException) {
        this.isException = isException;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getLastTirePressure() {
        return lastTirePressure;
    }

    public void setLastTirePressure(String lastTirePressure) {
        this.lastTirePressure = lastTirePressure;
    }

    public String getLastTireTempreture() {
        return lastTireTempreture;
    }

    public void setLastTireTempreture(String lastTireTempreture) {
        this.lastTireTempreture = lastTireTempreture;
    }
}
