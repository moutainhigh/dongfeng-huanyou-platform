package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 经销商选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryDealersPojo {
    //经销商主键
    private BigInteger id;
    //名称
    private String name;
    //经度
    private BigDecimal lng;
    //纬度
    private BigDecimal lat;

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

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
}
