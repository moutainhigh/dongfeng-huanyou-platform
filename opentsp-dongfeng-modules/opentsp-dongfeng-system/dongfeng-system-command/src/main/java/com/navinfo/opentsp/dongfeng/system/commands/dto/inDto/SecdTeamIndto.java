package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.math.BigInteger;

/**
 * Created by liht on 2017/3/16.
 */
public class SecdTeamIndto {
    private BigInteger id;

    private String name;

    private String code;

    private int workType;

    private BigInteger longitude;

    private BigInteger latitude;

    private String address;

    private int workRadius;

    private String dealerCode;

    private BigInteger dealerId;

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
