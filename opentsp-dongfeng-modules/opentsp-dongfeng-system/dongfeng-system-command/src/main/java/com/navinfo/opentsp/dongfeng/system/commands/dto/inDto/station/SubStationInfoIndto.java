package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station;

/**
 * 二级服务站网点信息
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class SubStationInfoIndto {
    private String stationId; //服务站ID
    private String name; //服务站名称
    private String code; //服务站编码
    private String longitude; //经度
    private String latitude; //纬度
    private String address; //服务站地址
    private String workRadius; //服务站半径
    private String parentId; //父服务站ID
    private String parentCode; //父服务站编码

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkRadius() {
        return workRadius;
    }

    public void setWorkRadius(String workRadius) {
        this.workRadius = workRadius;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
