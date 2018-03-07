package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-24
 * @modify
 * @copyright Navi Tsp
 */
public class StationBasicInfoOutdto {
    private String stationName;//服务站名称
    private String stationShortName;//服务站简称
    private String stationCode;//服务站编码
    private String address;//服务站地址
    private BigInteger longitude;    //经度
    private BigInteger latitude;    //纬度
    private Integer starLever;    //服务站星级
    private Integer radius;//占地半径
    private Integer serviceRadius;//服务半径
    private BigInteger strandedMaxTime;//滞留超时阀值
    private String telephone;    //服务热线
    private String mobilePhone;    //服务手机
    private String serviceManager;    //服务经理
    private Integer province;//省编码
    private Integer city;//城市编码
    private String picture;//图片
    private BigInteger isCore;//是否是核心站
    private Integer switchStatus;
    private String switchStatusStr;
    private String andNet;//并网（1：轻卡 2：锡柴 3：雍柴 4：大柴）
    private String remark;//备注
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationShortName() {
        return stationShortName;
    }

    public void setStationShortName(String stationShortName) {
        this.stationShortName = stationShortName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
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

    public Integer getStarLever() {
        return starLever;
    }

    public void setStarLever(Integer starLever) {
        this.starLever = starLever;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(Integer serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

    public BigInteger getStrandedMaxTime() {
        return strandedMaxTime;
    }

    public void setStrandedMaxTime(BigInteger strandedMaxTime) {
        this.strandedMaxTime = strandedMaxTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(String serviceManager) {
        this.serviceManager = serviceManager;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigInteger getIsCore() {
        return isCore;
    }

    public void setIsCore(BigInteger isCore) {
        this.isCore = isCore;
    }

    public Integer getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getSwitchStatusStr() {
        return switchStatusStr;
    }

    public void setSwitchStatusStr(String switchStatusStr) {
        this.switchStatusStr = switchStatusStr;
    }

    public String getAndNet() {
        return andNet;
    }

    public void setAndNet(String andNet) {
        this.andNet = andNet;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
