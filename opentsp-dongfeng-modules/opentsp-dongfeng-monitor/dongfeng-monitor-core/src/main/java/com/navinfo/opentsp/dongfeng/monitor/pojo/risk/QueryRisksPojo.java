package com.navinfo.opentsp.dongfeng.monitor.pojo.risk;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 防控区域
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/4/11
 */
public class QueryRisksPojo {
    private BigInteger regionId;//区域id
    private Integer regionType;//区域类型
    private String regionName; //风控区名称
    private Integer radius;//半径(圆形风控区域半径，其他为空)
    private BigDecimal lat;//纬度
    private BigDecimal lng;//经度

    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
}
