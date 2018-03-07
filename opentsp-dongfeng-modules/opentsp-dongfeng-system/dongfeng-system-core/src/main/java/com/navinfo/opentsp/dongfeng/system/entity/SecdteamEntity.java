package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Sunyu on 2017/3/17.
 */
@Entity
@Table(name = "hy_secdteam")
public class SecdteamEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition = "bigint")
    private BigInteger id;

    /**
     * 二级经销商名称
     */
    @Column(nullable = true, name = "name")
    private String name;

    /**
     * 二级经销商编码
     */
    @Column(nullable = true, name = "code")
    private String code;

    /**
     * 工作类型
     */
    @Column(nullable = true, name = "work_type")
    private Integer workType;

    /**
     * 经度
     */
    @Column(nullable = true, name = "longitude")
    private Long longitude;

    /**
     * 纬度
     */
    @Column(nullable = true, name = "latitude")
    private Long latitude;

    /**
     * 逆地理地址(供APP)
     */
    @Column(nullable = true, name = "address")
    private String address;

    /**
     * 工作半经
     */
    @Column(nullable = true, name = "work_radius")
    private Integer workRadius;

    /**
     * 所属经销商编码
     */
    @Column(nullable = true, name = "dealer_code")
    private String dealerCode;

    /**
     * 所属经销商id
     */
    @Column(nullable = true, name = "dealer_id")
    private Long dealerId;
    /**
     * 载重类型
     */
    @Column(nullable = true, name = "load_type", columnDefinition="int")
    private Integer loadType;
    /**
     * 同步状态
     */
    @Column(nullable = true, name = "SYNC_STATUS")
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

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
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

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
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