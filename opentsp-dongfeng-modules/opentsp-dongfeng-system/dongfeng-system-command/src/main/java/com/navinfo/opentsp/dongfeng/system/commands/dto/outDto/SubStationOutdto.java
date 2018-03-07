package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-24
 * @modify
 * @copyright Navi Tsp
 */
public class SubStationOutdto {
    private BigInteger id;
    private String name;
    private String code;
    private BigInteger longitude;
    private BigInteger latitude;
    private String address;
    private Integer workRadius;

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
}
