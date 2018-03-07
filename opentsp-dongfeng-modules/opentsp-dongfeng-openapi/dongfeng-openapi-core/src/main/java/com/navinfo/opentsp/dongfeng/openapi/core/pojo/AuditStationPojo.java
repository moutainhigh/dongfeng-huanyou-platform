package com.navinfo.opentsp.dongfeng.openapi.core.pojo;


import java.math.BigInteger;

public class AuditStationPojo {
    private BigInteger stationId;//服务站ID
    private Integer stationType;//服务站类型 1-一级服务站  2-二级服务站
    private String address;//地址
    private BigInteger longitude;//地址经度
    private BigInteger latitude;//地址纬度
    private BigInteger accountId;// 上报用户
    private BigInteger createTime;//上报用户


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getLongitude() {
        return longitude;
    }

    public void setLongitude(BigInteger longitude) {
        this.longitude = longitude;
    }

    public BigInteger getLatitude() {
        return latitude;
    }

    public void setLatitude(BigInteger latitude) {
        this.latitude = latitude;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }
}