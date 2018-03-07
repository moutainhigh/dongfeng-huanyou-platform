package com.navinfo.opentsp.dongfeng.common.dto;

/**
 * 省市编码
 *
 * @wenya
 * @create 2017-03-28 14:55
 **/
public class GovData {
    //省市编码
    private Integer govCode;
    //省市名称
    private String govName;
    //上级省市编码
    private Integer govParCode;

    public Integer getGovCode() {
        return govCode;
    }

    public void setGovCode(Integer govCode) {
        this.govCode = govCode;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public Integer getGovParCode() {
        return govParCode;
    }

    public void setGovParCode(Integer govParCode) {
        this.govParCode = govParCode;
    }
}
