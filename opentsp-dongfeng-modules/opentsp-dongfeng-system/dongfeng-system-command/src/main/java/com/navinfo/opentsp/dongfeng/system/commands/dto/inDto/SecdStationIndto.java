package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

/**
 * 二级经销商dto
 * @Author zhaoming@mapbar.com
 * @Date 2017/3/15 9:22
 **/

public class SecdStationIndto {
    //二级经销商id
    private Integer secdId;

    //二级经销商名称
    private String name;

    //二级经销商编码
    private String code;

    //工作类型
    // private Integer workType;//(1—入库、2—盘库、3—出库、4—完成、5—退车)

    //经度
    private Long lon;
    //纬度
    private Long lat;
    //逆地理地址(供APP用)
    private String address;
    //工作半经
    private Integer workRadius;
    //所属经销商编码
    private String dealerCode;
    //所属经销商id(外键)
    private Long dealerId;


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

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }
}
