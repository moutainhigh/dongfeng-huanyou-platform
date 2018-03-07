package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/11
 */
@Entity
@Table(name = "hy_business")
public class HyBusinessEntity {
    private BigInteger cid;
    private String businessName;
    private String businessCode;
    private String addressCode;
    private String linkPerson;
    private String linkTelpone;
    private String fax;
    private String addressCommon;
    private String common;
    private Short cType;

    @Id
    @Column(name = "cid", nullable = false, columnDefinition="bigint")
    public BigInteger getCid() {
        return cid;
    }

    public void setCid(BigInteger cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "business_name", nullable = true, length = 200)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Basic
    @Column(name = "business_code", nullable = true, length = 50)
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    @Basic
    @Column(name = "address_code", nullable = true, length = 50)
    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    @Basic
    @Column(name = "link_person", nullable = true, length = 50)
    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    @Basic
    @Column(name = "link_telpone", nullable = true, length = 50)
    public String getLinkTelpone() {
        return linkTelpone;
    }

    public void setLinkTelpone(String linkTelpone) {
        this.linkTelpone = linkTelpone;
    }

    @Basic
    @Column(name = "fax", nullable = true, length = 50)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "address_common", nullable = true, length = 200)
    public String getAddressCommon() {
        return addressCommon;
    }

    public void setAddressCommon(String addressCommon) {
        this.addressCommon = addressCommon;
    }

    @Basic
    @Column(name = "common", nullable = true, length = 200)
    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    @Basic
    @Column(name = "c_type", nullable = true)
    public Short getcType() {
        return cType;
    }

    public void setcType(Short cType) {
        this.cType = cType;
    }

}
