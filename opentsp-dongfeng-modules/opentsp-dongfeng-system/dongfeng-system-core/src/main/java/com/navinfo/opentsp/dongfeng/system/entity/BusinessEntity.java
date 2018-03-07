package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/14.
 */
@Entity
@Table(name = "hy_business")
public class BusinessEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "cid", columnDefinition = "bigint")
    private BigInteger cid;

    @Column(nullable = true, name = "business_name")
    private String businessName;

    @Column(nullable = true, name = "business_code")
    private String businessCode;

    @Column(nullable = true, name = "address_code")
    private String addressCode;

    @Column(nullable = true, name = "link_person")
    private String linkPerson;

    @Column(nullable = true, name = "link_telpone")
    private String linkTelpone;

    @Column(nullable = true, name = "fax")
    private String fax;

    @Column(nullable = true, name = "address_common")
    private String addressCommon;

    @Column(nullable = true, name = "common")
    private String common;

    /**
     * 从业资格证号-个人
     */
    @Column(nullable = true, name = "certificate_code")
    private String certificateCode;

    /**
     * 机动车驾驶证号-个人
     */
    @Column(nullable = true, name = "vehiclelic_code")
    private String vehicleLicCode;

    /**
     * 道路运输证号-个人
     */
    @Column(nullable = true, name = "roadtranslic_code")
    private String roadTransLicCode;

    /**
     * 道路运输经营许可证号-企业
     */
    @Column(nullable = true, name = "organization_code")
    private String organizationCode;


    @Column(nullable = true, name = "c_type")
    private Short cType;

    /**
     * 创建用户类型
     */
    @Column(nullable = true, name = "ACCOUNT_TYPE")
    private Integer accountType;

    /**
     * 创建用户ID
     */
    @Column(nullable = true, name = "ACCOUNT_ID")
    private Long accountId;

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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
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

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Short getcType() {
        return cType;
    }

    public void setcType(Short cType) {
        this.cType = cType;
    }
}
