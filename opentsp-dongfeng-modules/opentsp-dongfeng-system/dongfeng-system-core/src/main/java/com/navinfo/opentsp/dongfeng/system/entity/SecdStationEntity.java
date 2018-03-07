package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;

/**
 * Created by Rex on 2017/3/13.
 */
@Entity
@Table(name = "hy_secdstation")
public class SecdStationEntity {
    //二级经销商id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "id", columnDefinition = "bigint")
    private Integer secdId;

    //二级经销商名称
    @Column(nullable = true, name = "name")
    private String name;

    //二级经销商编码
    @Column(nullable = true, name = "code")
    private String code;

    //工作类型
   // private Integer workType;//(1—入库、2—盘库、3—出库、4—完成、5—退车)

    //经度
    @Column(nullable = true, name = "longitude")
    private Long lon;
    //纬度
    @Column(nullable = true, name = "latitude")
    private Long lat;
    //逆地理地址(供APP用)
    @Column(nullable = true, name = "address")
    private String address;
    //工作半经
    @Column(nullable = true, name = "work_radius")
    private Integer workRadius;
    //所属经销商编码
    @Column(nullable = true, name = "station_code")
    private String dealerCode;
    //所属经销商id(外键)
    @Column(nullable = true, name = "station_id")
    private Long dealerId;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getSecdId() {
        return secdId;
    }

    public void setSecdId(Integer secdId) {
        this.secdId = secdId;
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


    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
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

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }


}
