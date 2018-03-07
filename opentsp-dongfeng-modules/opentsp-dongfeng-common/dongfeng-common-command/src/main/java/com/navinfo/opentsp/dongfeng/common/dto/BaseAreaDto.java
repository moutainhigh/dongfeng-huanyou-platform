package com.navinfo.opentsp.dongfeng.common.dto;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-12
 **/
public class BaseAreaDto {
    //省市编码
    private String govCode;
    //省市名称
    private String govName;
    //上级省市编码
    private String govParCode;

    public String getGovCode() {
        return govCode;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getGovParCode() {
        return govParCode;
    }

    public void setGovParCode(String govParCode) {
        this.govParCode = govParCode;
    }
}
