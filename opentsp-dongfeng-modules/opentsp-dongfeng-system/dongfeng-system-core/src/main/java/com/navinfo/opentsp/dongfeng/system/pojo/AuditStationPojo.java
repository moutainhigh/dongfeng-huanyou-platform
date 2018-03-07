package com.navinfo.opentsp.dongfeng.system.pojo;


import java.math.BigInteger;

public class AuditStationPojo {
    private BigInteger stationId;//服务站ID
    private Integer stationType;//服务站类型 1-一级服务站  2-二级服务站
    private String stationName;//服务站名称
    private String subStationName;//服务站二级网点名称
    private String stationCode;//服务站编码
    private String oldAddress;//旧地址
    private BigInteger oldLongitude;//旧地址经度
    private BigInteger oldLatitude;//旧地址纬度
    private String mobilePhone;//服务手机
    private String telephone;//服务热线
    private Integer radius;//占地半径
    private String newAddress;//新地址
    private BigInteger newLongitude;//新地址
    private BigInteger newLatitude;//新地址
    private String accountName;// 上报用户
    private BigInteger createTime;//上报用户
    private BigInteger auditId;//审核ID

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }

    public Integer getStationType() {
        return stationType;
    }

    public void setStationType(Integer stationType) {
        this.stationType = stationType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSubStationName() {
        return subStationName;
    }

    public void setSubStationName(String subStationName) {
        this.subStationName = subStationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public BigInteger getOldLongitude() {
        return oldLongitude;
    }

    public void setOldLongitude(BigInteger oldLongitude) {
        this.oldLongitude = oldLongitude;
    }

    public BigInteger getOldLatitude() {
        return oldLatitude;
    }

    public void setOldLatitude(BigInteger oldLatitude) {
        this.oldLatitude = oldLatitude;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public BigInteger getNewLongitude() {
        return newLongitude;
    }

    public void setNewLongitude(BigInteger newLongitude) {
        this.newLongitude = newLongitude;
    }

    public BigInteger getNewLatitude() {
        return newLatitude;
    }

    public void setNewLatitude(BigInteger newLatitude) {
        this.newLatitude = newLatitude;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    public BigInteger getAuditId() {
        return auditId;
    }

    public void setAuditId(BigInteger auditId) {
        this.auditId = auditId;
    }
}