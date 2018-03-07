package com.navinfo.opentsp.dongfeng.system.pojo;


import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/13.
 * 分组列表Pojo
 */
public class TeamPojo {

    private BigInteger id;

    private String name;

    private BigInteger pid;

    private String isParent;

    private String open;

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

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
