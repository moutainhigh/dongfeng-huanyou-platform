package com.navinfo.opentsp.dongfeng.report.dto.oil;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author sunlin
 * @version 1.0
 * @date 2018-02-28
 * @modify
 */
public class TireDto implements Serializable {
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

    private String tirePressurePercent;// 胎压
    private String tireTempreturePercent;// 最新胎温
    private String timeStr;// GPS时间
    private String tireLocation;// 轮胎位置


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

    public String getTirePressurePercent() {
        return tirePressurePercent;
    }

    public void setTirePressurePercent(String tirePressurePercent) {
        this.tirePressurePercent = tirePressurePercent;
    }

    public String getTireTempreturePercent() {
        return tireTempreturePercent;
    }

    public void setTireTempreturePercent(String tireTempreturePercent) {
        this.tireTempreturePercent = tireTempreturePercent;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTireLocation() {
        return tireLocation;
    }

    public void setTireLocation(String tireLocation) {
        this.tireLocation = tireLocation;
    }
}
