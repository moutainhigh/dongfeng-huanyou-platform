package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-09-08
 * @modify
 * @copyright Navi Tsp
 */
public class SecdteamPojo {

    /**
     * 主键
     */
    private BigInteger id;

    /**
     * 二级经销商名称
     */
    private String name;

    /**
     * 二级经销商编码
     */
    private String code;

    /**
     * 工作类型
     */
    private Integer workType;

    /**
     * 经度
     */
    private BigInteger longitude;

    /**
     * 纬度
     */
    private BigInteger latitude;

    /**
     * 逆地理地址(供APP)
     */
    private String address;

    /**
     * 工作半经
     */
    private Integer workRadius;

    /**
     * 所属经销商编码
     */
    private String dealerCode;

    /**
     * 所属经销商id
     */
    private BigInteger dealerId;
    /**
     * 载重类型
     */
    private Integer loadType;
    /**
     * 同步状态
     */
    private Integer syncStatus;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWorkRadius() {
        return workRadius;
    }

    public void setWorkRadius(Integer workRadius) {
        this.workRadius = workRadius;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public BigInteger getDealerId() {
        return dealerId;
    }

    public void setDealerId(BigInteger dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getLoadType() {
        return loadType;
    }

    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }
}