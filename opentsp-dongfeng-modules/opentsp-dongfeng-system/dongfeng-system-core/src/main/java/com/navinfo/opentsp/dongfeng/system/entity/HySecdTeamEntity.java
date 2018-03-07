package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by liht on 2017/3/16.
 */
@Entity
@Table(name = "hy_secdteam")
public class HySecdTeamEntity implements Serializable{


    @Column(nullable = true, name = "name")
    private String name;

    @Column(nullable = true, name = "code")
    private String code;

    @Column(nullable = true, name = "work_type")
    private int workType;

    @Column(nullable = true, name = "longitude", columnDefinition="bigint")
    private BigInteger longitude;

    @Column(nullable = true, name = "latitude", columnDefinition="bigint")
    private BigInteger latitude;

    @Column(nullable = true, name = "address")
    private String address;

    @Column(nullable = true, name = "work_radius")
    private int workRadius;

    @Column(nullable = true, name = "dealer_code")
    private String dealerCode;

    @Column(nullable = true, name = "dealer_id", columnDefinition="bigint")
    private BigInteger dealerId;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition="bigint")
    private BigInteger id;


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

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
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

    public int getWorkRadius() {
        return workRadius;
    }

    public void setWorkRadius(int workRadius) {
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
}
