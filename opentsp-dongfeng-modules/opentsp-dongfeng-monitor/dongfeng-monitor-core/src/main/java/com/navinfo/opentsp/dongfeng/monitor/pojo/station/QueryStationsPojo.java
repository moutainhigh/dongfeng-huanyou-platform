package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 服务站
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */

public class QueryStationsPojo {

    private BigInteger id;//服务站ID

    private String stationName;//服务站名称

    private BigDecimal lng;//经度

    private BigDecimal lat;//纬度

    private Integer radius;//占地半径

    private Integer serviceRadius;//服务半径

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
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
}