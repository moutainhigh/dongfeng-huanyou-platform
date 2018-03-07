package com.navinfo.opentsp.dongfeng.monitor.dto.risk;

import java.math.BigDecimal;

/**
 * 防控区域选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryRisksPoint {
    private BigDecimal lat;//纬度
    private BigDecimal lng;//经度

    public QueryRisksPoint() {
    }

    public QueryRisksPoint(BigDecimal lat, BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
}
