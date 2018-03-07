package com.navinfo.opentsp.dongfeng.monitor.dto.risk;

import java.math.BigInteger;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
public class RiskRegionDto {
    private BigInteger regionId;//区域id
    private Integer regionType;//区域类型
    private String regionName; //风控区名称
    private BigInteger parentId;//所属经销商/区域
    private String parentName;//所属经销商/区域名称
    private String remake;//备注信息
    private Integer radius;//半径(圆形风控区域半径，其他为空)
    private String modifyUser;//修改人;
    private BigInteger createTime;//创建时间
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

    public List<QueryRisksPoint> getPoints() {
        return points;
    }

    public void setPoints(List<QueryRisksPoint> points) {
        this.points = points;
    }
}
