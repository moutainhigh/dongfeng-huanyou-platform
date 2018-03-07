package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */

public class BusinessPojo {


    /**
     * 所属客户ID
     */
    private BigInteger cid;

    /**
     * 企业许可证号
     */
    private String businessCode;
    /**
     * 所属客户名称
     */
    private String businessName;

    /**
     * 区域ID
     */
    private String addressCode;

    /**
     * 区域名称
     */
    private String addressName;

    /**
     * 联系人
     */
    private String linkPerson;
    /**
     * 联系电话
     */
    private String linkTelpone;
    /**
     * 传真
     */
    private String fax;
    /**
     * 详细地址
     */
    private String addressCommon;
    /**
     * 备注
     */
    private String common;

    /**
     * 从业资格证号-个人
     */
    private String certificateCode;

    /**
     * 机动车驾驶证号-个人
     */
    private String vehicleLicCode;

    /**
     * 道路运输证号-个人
     */
    private String roadTransLicCode;

    /**
     * 道路运输经营许可证号-企业
     */
    private String organizationCode;
    /**
     * 客户类型 1-企业 2-个人
     */
    private Short ctype;

    /**
     * 客户类型
     */
    private String customerType;

    public BigInteger getCid() {
        return cid;
    }

    public void setCid(BigInteger cid) {
        this.cid = cid;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getLinkTelpone() {
        return linkTelpone;
    }

    public void setLinkTelpone(String linkTelpone) {
        this.linkTelpone = linkTelpone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddressCommon() {
        return addressCommon;
    }

    public void setAddressCommon(String addressCommon) {
        this.addressCommon = addressCommon;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getVehicleLicCode() {
        return vehicleLicCode;
    }

    public void setVehicleLicCode(String vehicleLicCode) {
        this.vehicleLicCode = vehicleLicCode;
    }

    public String getRoadTransLicCode() {
        return roadTransLicCode;
    }

    public void setRoadTransLicCode(String roadTransLicCode) {
        this.roadTransLicCode = roadTransLicCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Short getCtype() {
        return ctype;
    }

    public void setCtype(Short ctype) {
        this.ctype = ctype;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}