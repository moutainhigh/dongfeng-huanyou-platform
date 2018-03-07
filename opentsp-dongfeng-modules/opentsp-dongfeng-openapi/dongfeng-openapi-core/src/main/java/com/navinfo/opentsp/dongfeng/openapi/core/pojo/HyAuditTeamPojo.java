package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
public class HyAuditTeamPojo {

    private BigInteger id;//主键ID

    private BigInteger aeraId;//经销商ID

    private int aeraType;//区域类型(1-一级经销商，2-二级经销商)

    private BigInteger lng;//纬度

    private BigInteger lat;//经度

    private String address;//地址

    private BigInteger auditStatus;//审核状态

    private BigInteger createTime;//上报时间

    private BigInteger accountId;//上报人

    private String opition;//审核意见

    private BigInteger auditTime;//审核时间

    private BigInteger auditUser;//审核人

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getAeraId() {
        return aeraId;
    }

    public void setAeraId(BigInteger aeraId) {
        this.aeraId = aeraId;
    }

    public int getAeraType() {
        return aeraType;
    }

    public void setAeraType(int aeraType) {
        this.aeraType = aeraType;
    }

    public BigInteger getLng() {
        return lng;
    }

    public void setLng(BigInteger lng) {
        this.lng = lng;
    }

    public BigInteger getLat() {
        return lat;
    }

    public void setLat(BigInteger lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(BigInteger auditStatus) {
        this.auditStatus = auditStatus;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public String getOpition() {
        return opition;
    }

    public void setOpition(String opition) {
        this.opition = opition;
    }

    public BigInteger getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(BigInteger auditTime) {
        this.auditTime = auditTime;
    }

    public BigInteger getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(BigInteger auditUser) {
        this.auditUser = auditUser;
    }
}