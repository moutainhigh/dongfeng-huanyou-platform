package com.navinfo.opentsp.dongfeng.system.pojo;


import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 * 省份树Pojo
 */
public class PovinceTreePojo {

    private Integer id;

    private String name;

    private BigInteger pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }
}
