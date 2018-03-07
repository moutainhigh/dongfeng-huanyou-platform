package com.navinfo.opentsp.dongfeng.report.dto.station;


public class QueryStationLoadDto {
    private String stationId;
    private String name;
    private String address;
    private String phone;
    private String manager;
    private String serviceLevel;
    private String reviewsLevel;
    private String stationEnable;
    private String actualTrains;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
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

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getReviewsLevel() {
        return reviewsLevel;
    }

    public void setReviewsLevel(String reviewsLevel) {
        this.reviewsLevel = reviewsLevel;
    }

    public String getStationEnable() {
        return stationEnable;
    }

    public void setStationEnable(String stationEnable) {
        this.stationEnable = stationEnable;
    }

    public String getActualTrains() {
        return actualTrains;
    }

    public void setActualTrains(String actualTrains) {
        this.actualTrains = actualTrains;
    }

    @Override
    public String toString() {
        return "QueryStationLoadDto{" +
                "stationId='" + stationId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", manager='" + manager + '\'' +
                ", serviceLevel='" + serviceLevel + '\'' +
                ", reviewsLevel='" + reviewsLevel + '\'' +
                ", stationEnable='" + stationEnable + '\'' +
                ", actualTrains='" + actualTrains + '\'' +
                '}';
    }
}
