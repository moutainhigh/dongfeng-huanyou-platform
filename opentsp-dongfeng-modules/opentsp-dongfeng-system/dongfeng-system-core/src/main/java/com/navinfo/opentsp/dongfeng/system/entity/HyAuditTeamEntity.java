package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
@Entity
@Table(name = "hy_audit_team")
public class HyAuditTeamEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "ID", columnDefinition="bigint")
    private BigInteger id;//主键ID

    @Column(nullable = false, name = "AERA_ID", columnDefinition="bigint")
    private BigInteger aeraId;//经销商ID

    @Column(nullable = false, name = "AERA_TYPE")
    private int aeraType;//区域类型(1-一级服务站，2-二级服务站)

    @Column(nullable = false, name = "LONG_ITUDE", columnDefinition="bigint")
    private BigInteger lng;//纬度

    @Column(nullable = false, name = "LAT_ITUDE", columnDefinition="bigint")
    private BigInteger lat;//经度

    @Column(nullable = false, name = "ADDRESS")
    private String address;//地址

    @Column(nullable = false, name = "AUDIT_STATUS", columnDefinition="bigint")
    private BigInteger auditStatus;//审核状态

    @Column(nullable = false, name = "CREATE_TIME", columnDefinition="bigint")
    private BigInteger createTime;//上报时间

    @Column(nullable = false, name = "ACCOUNT_ID", columnDefinition="bigint")
    private BigInteger accountId;//上报人

    @Column(nullable = false, name = "OPINION", columnDefinition="TEXT", length = 65535)
    private String opition;//审核意见

    @Column(nullable = false, name = "AUDIT_TIME", columnDefinition="bigint")
    private BigInteger auditTime;//审核时间

    @Column(nullable = false, name = "AUDIT_USER", columnDefinition="bigint")
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