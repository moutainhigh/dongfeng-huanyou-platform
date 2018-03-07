package com.navinfo.opentsp.dongfeng.monitor.pojo.risk;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class RiskRegionPojo {
    private BigInteger regionId;//区域id
    private Integer regionType;//区域类型
    private String regionName; //风控区名称
    private BigInteger parentId;//所属经销商/区域
    private String parentName;//所属经销商/区域名称
    private String remake;//备注信息
    private Integer radius;//半径(圆形风控区域半径，其他为空)
    private String modifyUser;//修改人;
    private BigInteger createTime;//创建时间
    private BigDecimal latitude;//纬度
    private BigDecimal longitude;//经度

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

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}

