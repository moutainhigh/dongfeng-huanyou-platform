package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 */
public class BusinessOutdto {

    private BigInteger cid;

    private String businessName;

    private String addressCode;

    private String linkTelpone;

    private String addressCommon;
    /**
     * 联系人
     */
    private String linkPerson;
    /**
     * 企业许可证号
     */
    private String businessCode;
    /**
     * 传真
     */
    private String fax;
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
     * 区域名称
     */
    private String addressName;
    /**
     * 客户类型 1-企业 2-个人
     */
    private Short cType;
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

    public String getLinkTelpone() {
        return linkTelpone;
    }

    public void setLinkTelpone(String linkTelpone) {
        this.linkTelpone = linkTelpone;
    }

    public String getAddressCommon() {
        return addressCommon;
    }

    public void setAddressCommon(String addressCommon) {
        this.addressCommon = addressCommon;
    }

    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public Short getCType() {
        return cType;
    }
    public void setCType(Short cType) {
        this.cType = cType;
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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
