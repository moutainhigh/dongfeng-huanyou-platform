package com.navinfo.opentsp.dongfeng.report.pojo.station;

import java.math.BigInteger;

public class StationPojo {
    private BigInteger stationId;
    private String name;
    private String address;

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

    @Override
    public String toString() {
        return "StationPojo{" +
                "stationId=" + stationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
