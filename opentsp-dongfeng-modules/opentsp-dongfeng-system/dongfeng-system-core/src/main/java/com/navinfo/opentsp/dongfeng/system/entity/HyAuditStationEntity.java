package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-07
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_audit_station")
public class HyAuditStationEntity {
    private BigInteger id;
    private BigInteger aeraId;
    private Integer aeraType;
    private BigInteger longItude;
    private BigInteger latItude;
    private String address;
    private BigInteger auditStatus;
    private BigInteger createTime;
    private BigInteger accountId;
    private String opinion;
    private BigInteger auditTime;
    private BigInteger auditUser;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, columnDefinition = "bigint")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "AERA_ID", nullable = true, columnDefinition = "bigint")
    public BigInteger getAeraId() {
        return aeraId;
    }

    public void setAeraId(BigInteger aeraId) {
        this.aeraId = aeraId;
    }

    @Basic
    @Column(name = "AERA_TYPE", nullable = true)
    public Integer getAeraType() {
        return aeraType;
    }

    public void setAeraType(Integer aeraType) {
        this.aeraType = aeraType;
    }

    @Basic
    @Column(name = "LONG_ITUDE", nullable = true, columnDefinition = "bigint")
    public BigInteger getLongItude() {
        return longItude;
    }

    public void setLongItude(BigInteger longItude) {
        this.longItude = longItude;
    }

    @Basic
    @Column(name = "LAT_ITUDE", nullable = true, columnDefinition = "bigint")
    public BigInteger getLatItude() {
        return latItude;
    }

    public void setLatItude(BigInteger latItude) {
        this.latItude = latItude;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "AUDIT_STATUS", nullable = true, columnDefinition = "bigint")
    public BigInteger getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(BigInteger auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true, columnDefinition = "bigint")
    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "ACCOUNT_ID", nullable = true, columnDefinition = "bigint")
    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "OPINION", nullable = true, columnDefinition = "TEXT", length = 65535)
    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @Basic
    @Column(name = "AUDIT_TIME", nullable = true, columnDefinition = "bigint")
    public BigInteger getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(BigInteger auditTime) {
        this.auditTime = auditTime;
    }

    @Basic
    @Column(name = "AUDIT_USER", nullable = true, columnDefinition = "bigint")
    public BigInteger getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(BigInteger auditUser) {
        this.auditUser = auditUser;
    }

}
