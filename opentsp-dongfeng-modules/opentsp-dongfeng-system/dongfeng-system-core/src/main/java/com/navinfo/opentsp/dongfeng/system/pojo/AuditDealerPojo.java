package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */

public class AuditDealerPojo {
    /**
     * 上报用户
     */
    private String accountName;
    /**
     * 上报时间
     */
    private BigInteger createTime;
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
    private BigInteger oldLat;
    /**
     * 老经度
     */
    private BigInteger oldLng;
    /**
     * 老地址
     */
    private String oldAddress;
    /**
     * 新维度
     */
    private BigInteger newLat;
    /**
     * 新经度
     */
    private BigInteger newLng;
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
    /**
     * 经销商权限ID（一级经销商==一级经销商||二级经销商==二级经销商所属的经销商）
     */
    private BigInteger dealerAuthId;

    /**
     * 省编码
     */
    private String province;
    /**
     * 城市编码
     */
    private String city;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
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

    public BigInteger getOldLat() {
        return oldLat;
    }

    public void setOldLat(BigInteger oldLat) {
        this.oldLat = oldLat;
    }

    public BigInteger getOldLng() {
        return oldLng;
    }

    public void setOldLng(BigInteger oldLng) {
        this.oldLng = oldLng;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public BigInteger getNewLat() {
        return newLat;
    }

    public void setNewLat(BigInteger newLat) {
        this.newLat = newLat;
    }

    public BigInteger getNewLng() {
        return newLng;
    }

    public void setNewLng(BigInteger newLng) {
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

    public BigInteger getDealerAuthId() {
        return dealerAuthId;
    }

    public void setDealerAuthId(BigInteger dealerAuthId) {
        this.dealerAuthId = dealerAuthId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}