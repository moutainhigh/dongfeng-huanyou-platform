package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-08
 * @modify
 * @copyright Navi Tsp
 */
public class AuditStationOutdto implements Serializable {
    private static final long serialVersionUID = -230542921183678508L;
    private BigInteger stationId;//服务站ID
    private Integer stationType;//服务站类型 1-一级服务站  2-二级服务站
    private String stationName;//服务站名称
    private String subStationName;//服务站二级网点名称
    private String stationCode;//服务站编码
    private String oldAddress;//旧地址
    private Double oldLong;//旧地址经度
    private Double oldLat;//旧地址纬度
    private String mobilePhone;//服务手机
    private String telephone;//服务热线
    private Integer radius;//占地半径
    private String newAddress;//新地址
    private Double newLong;//新地址
    private Double newLat;//新地址
    private String accountName;// 上报用户
    private String createTimeStr;//上报用户
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

    public Double getOldLong() {
        return oldLong;
    }

    public void setOldLong(Double oldLong) {
        this.oldLong = oldLong;
    }

    public Double getOldLat() {
        return oldLat;
    }

    public void setOldLat(Double oldLat) {
        this.oldLat = oldLat;
    }

    public void setNewLong(Double newLong) {
        this.newLong = newLong;
    }

    public void setNewLat(Double newLat) {
        this.newLat = newLat;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Double getNewLong() {
        return newLong;
    }

    public Double getNewLat() {
        return newLat;
    }

    public BigInteger getAuditId() {
        return auditId;
    }

    public void setAuditId(BigInteger auditId) {
        this.auditId = auditId;
    }
}
