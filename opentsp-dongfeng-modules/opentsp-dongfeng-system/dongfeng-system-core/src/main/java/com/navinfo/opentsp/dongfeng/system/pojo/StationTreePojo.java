package com.navinfo.opentsp.dongfeng.system.pojo;


import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 * 服务站树Pojo
 */
public class StationTreePojo {

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
