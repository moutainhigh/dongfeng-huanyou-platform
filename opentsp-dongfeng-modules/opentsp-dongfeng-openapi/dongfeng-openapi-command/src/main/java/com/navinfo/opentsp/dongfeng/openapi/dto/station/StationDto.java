package com.navinfo.opentsp.dongfeng.openapi.dto.station;

/**
 * @author fwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
public class StationDto {
    private String id;
    private String stationCode;//服务站编码
    private String stationName;//服务站名称
    private String stationShortName;//服务站简称
    private double stationLon;    //经度
    private double stationLat;    //纬度
    private String stationPhoto;//图片
    private String centralFlag;//是否是核心站
    private Integer stationLevel;    //服务站星级
    private String stationTel;    //服务热线
    private String stationPhone;    //服务手机
    private String stationAddress;//服务站地址
    private String provinceId;//省编码
    private String cityId;//城市编码
    private String stationIntroduce;//简介
    private String serviceType;//服务类型
    private String createTime;//创建时间
    private String updateTime;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

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

    public double getStationLon() {
        return stationLon;
    }

    public void setStationLon(double stationLon) {
        this.stationLon = stationLon;
    }

    public double getStationLat() {
        return stationLat;
    }

    public void setStationLat(double stationLat) {
        this.stationLat = stationLat;
    }

    public String getStationPhoto() {
        return stationPhoto;
    }

    public void setStationPhoto(String stationPhoto) {
        this.stationPhoto = stationPhoto;
    }

    public String getCentralFlag() {
        return centralFlag;
    }

    public void setCentralFlag(String centralFlag) {
        this.centralFlag = centralFlag;
    }

    public Integer getStationLevel() {
        return stationLevel;
    }

    public void setStationLevel(Integer stationLevel) {
        this.stationLevel = stationLevel;
    }

    public String getStationTel() {
        return stationTel;
    }

    public void setStationTel(String stationTel) {
        this.stationTel = stationTel;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStationIntroduce() {
        return stationIntroduce;
    }

    public void setStationIntroduce(String stationIntroduce) {
        this.stationIntroduce = stationIntroduce;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
