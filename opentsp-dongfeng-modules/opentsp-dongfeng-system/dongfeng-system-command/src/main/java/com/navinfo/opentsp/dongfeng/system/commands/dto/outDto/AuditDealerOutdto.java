package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class AuditDealerOutdto {

    /**
     * 上报用户
     */
    private String accountName;
    /**
     * 上报时间
     */
    private String createTime;
    /**
     * 经销商ID
     */
    private BigInteger dealerId;
    /**
     * 所属经销商
     */
    private String dealerName;
    /**
     * 经销商code
     */
    private String code;
    /**
     * 经销商名称
     */
    private String name;
    /**
     * 占地半径
     */
    private int radius;
    /**
     * 老纬度
     */
    private double oldLat;
    /**
     * 老经度
     */
    private double oldLng;
    /**
     * 老地址
     */
    private String oldAddress;
    /**
     * 新维度
     */
    private double newLat;
    /**
     * 新经度
     */
    private double newLng;
    /**
     * 新地址
     */
    private String newAddress;
    /**
     * 热线
     */
    private String fax;
    /**
     * 手机
     */
    private String phone;
    /**
     * 经销商类型（一级or二级）
     */
    private int type;
    /**
     * 审核ID
     */
    private BigInteger auditId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigInteger getDealerId() {
        return dealerId;
    }

    public void setDealerId(BigInteger dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getOldLat() {
        return oldLat;
    }

    public void setOldLat(double oldLat) {
        this.oldLat = oldLat;
    }

    public double getOldLng() {
        return oldLng;
    }

    public void setOldLng(double oldLng) {
        this.oldLng = oldLng;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public double getNewLat() {
        return newLat;
    }

    public void setNewLat(double newLat) {
        this.newLat = newLat;
    }

    public double getNewLng() {
        return newLng;
    }

    public void setNewLng(double newLng) {
        this.newLng = newLng;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigInteger getAuditId() {
        return auditId;
    }

    public void setAuditId(BigInteger auditId) {
        this.auditId = auditId;
    }
}
