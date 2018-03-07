package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_secdstation")
public class HySecdstationEntity {
    private BigInteger id;
    private String name;
    private String code;
    private BigInteger longitude;
    private BigInteger latitude;
    private String address;
    private Integer workRadius;
    private String stationCode;
    private BigInteger stationId;
    private Integer tagSyncStatus;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "longitude", nullable = true, columnDefinition = "bigint")
    public BigInteger getLongitude() {
        return longitude;
    }

    public void setLongitude(BigInteger longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, columnDefinition = "bigint")
    public BigInteger getLatitude() {
        return latitude;
    }

    public void setLatitude(BigInteger latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "work_radius", nullable = true)
    public Integer getWorkRadius() {
        return workRadius;
    }

    public void setWorkRadius(Integer workRadius) {
        this.workRadius = workRadius;
    }

    @Basic
    @Column(name = "station_code", nullable = true, length = 50)
    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Basic
    @Column(name = "station_id", nullable = true, columnDefinition = "bigint")
    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }


    @Basic
    @Column(name = "TAG_SYNC_STATUS", nullable = false)
    public Integer getTagSyncStatus() {
        return tagSyncStatus;
    }

    public void setTagSyncStatus(Integer tagSyncStatus) {
        this.tagSyncStatus = tagSyncStatus;
    }
}
