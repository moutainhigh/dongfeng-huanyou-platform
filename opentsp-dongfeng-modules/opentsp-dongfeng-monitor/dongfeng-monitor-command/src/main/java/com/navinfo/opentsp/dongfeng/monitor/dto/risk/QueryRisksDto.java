package com.navinfo.opentsp.dongfeng.monitor.dto.risk;

import java.math.BigInteger;
import java.util.List;

/**
 * 防控区域选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryRisksDto {
    private BigInteger regionId;//区域id
    private Integer regionType;//区域类型
    private String regionName; //风控区名称
    private Integer radius;//半径(圆形风控区域半径，其他为空)
    private List<QueryRisksPoint> points;

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

    public List<QueryRisksPoint> getPoints() {
        return points;
    }

    public void setPoints(List<QueryRisksPoint> points) {
        this.points = points;
    }
}
