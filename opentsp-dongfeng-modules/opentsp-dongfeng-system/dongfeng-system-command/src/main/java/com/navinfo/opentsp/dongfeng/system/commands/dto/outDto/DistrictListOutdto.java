package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

/**
 * Created by Sunyu on 2017/3/15.
 */
public class DistrictListOutdto {

    /**
     * 销售区域ID
     */
    private BigInteger id;
    /**
     * 分组名称
     */
    private String tName;
    /**
     * 上级分组
     */
    private BigInteger parentId;
    /**
     * 联系人
     */
    private String tlinkMan;
    /**
     * 联系电话
     */
    private String tlinkTel;
    /**
     * 所在区域
     */
    private String pName;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTlinkMan() {
        return tlinkMan;
    }

    public void setTlinkMan(String tlinkMan) {
        this.tlinkMan = tlinkMan;
    }

    public String getTlinkTel() {
        return tlinkTel;
    }

    public void setTlinkTel(String tlinkTel) {
        this.tlinkTel = tlinkTel;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
}