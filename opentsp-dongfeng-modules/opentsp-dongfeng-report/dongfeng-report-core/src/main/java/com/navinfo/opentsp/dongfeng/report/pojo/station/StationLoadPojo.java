package com.navinfo.opentsp.dongfeng.report.pojo.station;


import java.math.BigDecimal;
import java.math.BigInteger;

public class StationLoadPojo {
    private BigInteger stationId;
    private String name;
    private String address;
    private String phone;
    private String manager;
    private Integer serviceLevel;
    private BigDecimal reviewsLevel;
    private Integer stationEnable;
    private Integer actualTrains;

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public BigDecimal getReviewsLevel() {
        return reviewsLevel;
    }

    public void setReviewsLevel(BigDecimal reviewsLevel) {
        this.reviewsLevel = reviewsLevel;
    }

    public Integer getStationEnable() {
        return stationEnable;
    }

    public void setStationEnable(Integer stationEnable) {
        this.stationEnable = stationEnable;
    }

    public Integer getActualTrains() {
        return actualTrains;
    }

    public void setActualTrains(Integer actualTrains) {
        this.actualTrains = actualTrains;
    }

    @Override
    public String toString() {
        return "StationLoadPojo{" +
                "stationId=" + stationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", manager='" + manager + '\'' +
                ", serviceLevel=" + serviceLevel +
                ", reviewsLevel=" + reviewsLevel +
                ", stationEnable=" + stationEnable +
                ", actualTrains=" + actualTrains +
                '}';
    }
}
