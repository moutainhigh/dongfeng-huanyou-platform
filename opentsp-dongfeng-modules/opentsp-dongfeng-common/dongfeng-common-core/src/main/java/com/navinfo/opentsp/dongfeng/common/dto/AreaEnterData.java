package com.navinfo.opentsp.dongfeng.common.dto;

/**
 * 进出区域数据模型
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-19
 * @modify
 * @copyright Navi Tsp
 */
public class AreaEnterData {
    private String simNo;//终端通讯号
    private Long areaId;//区域ID
    private Integer areaType;//区域类型：11-一级服务站、12-二级服务站、21-一级经销商、22-二级经销商
    private Long inTime; //车辆进入区域时间
    private Long outTime;//车辆出区域时间

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public Long getInTime() {
        return inTime;
    }

    public void setInTime(Long inTime) {
        this.inTime = inTime;
    }

    public Long getOutTime() {
        return outTime;
    }

    public void setOutTime(Long outTime) {
        this.outTime = outTime;
    }
}
