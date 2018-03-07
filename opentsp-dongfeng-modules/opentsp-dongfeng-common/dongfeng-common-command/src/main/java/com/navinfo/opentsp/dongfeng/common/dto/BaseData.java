package com.navinfo.opentsp.dongfeng.common.dto;


import java.math.BigInteger;

/**
 * Created by yaocy on 2016/03/11.
 */
public class BaseData {

    private BigInteger id;

    private String code;

    private String value;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
