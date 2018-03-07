package com.navinfo.opentsp.dongfeng.monitor.dto.risk;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
public class Point {
    private Double lat;//纬度
    private Double lng;//经度

    public Point() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
