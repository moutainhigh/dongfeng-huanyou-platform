package com.navinfo.opentsp.dongfeng.report.pojo.general;

import java.math.BigInteger;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class RearInstallVehicleDetailsPojo {
    private String serviceStation;

    private String serviceStationCode;

    private String lat;

    private String lon;

    private String date;

    private BigInteger count;

    public String getServiceStation() {
        return serviceStation;
    }

    public void setServiceStation(String serviceStation) {
        this.serviceStation = serviceStation;
    }

    public String getServiceStationCode() {
        return serviceStationCode;
    }

    public void setServiceStationCode(String serviceStationCode) {
        this.serviceStationCode = serviceStationCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RearInstallVehicleDetailsPojo{" +
                "serviceStation='" + serviceStation + '\'' +
                ", serviceStationCode='" + serviceStationCode + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", date='" + date + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
