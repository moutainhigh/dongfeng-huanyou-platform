package com.navinfo.opentsp.dongfeng.monitor.dto.car;


/**
 * @wenya
 * @create 2017-03-24 17:58
 **/
public class QueryDistrictDto {
    //主键
    private Long id;
    //所属区域
    private String districtName;
    //上级销售区域
    private String pdistrictName;
    //联系人
    private String linkMan;
    private Long pid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
