package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 所属销售区域弹窗对象
 *
 * @wenya
 * @create 2017-03-24 17:31
 **/
public class QueryDistrictPojo {
    //主键
    private BigInteger id;
    //所属区域
    private String districtName;
    //上级销售区域
    private String pdistrictName;
    //联系人
    private String linkMan;
    private BigInteger pid;

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPdistrictName() {
        return pdistrictName;
    }

    public void setPdistrictName(String pdistrictName) {
        this.pdistrictName = pdistrictName;
    }
}
