package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 * 服务站树dto
 */
public class StationTreeOutdto {

    private BigInteger id;

    private String name;

    private Integer pid;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
