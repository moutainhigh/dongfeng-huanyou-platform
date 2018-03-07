package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


import java.math.BigInteger;

/**
 * Created by yaocy on 2016/03/15.
 */
public class UserAttributeOutDto {

    private BigInteger code;

    private String name;

    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
