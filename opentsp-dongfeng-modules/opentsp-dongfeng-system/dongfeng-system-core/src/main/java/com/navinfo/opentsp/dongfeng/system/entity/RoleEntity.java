package com.navinfo.opentsp.dongfeng.system.entity;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by yaocy on 2017/03/13.
 */
@Entity
@Table(name = "hy_role")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false, name = "R_ID", columnDefinition="bigint")
    private BigInteger rId;

    @Column(nullable = true, name = "DISTRICT")
    private int district;

    @Column(nullable = true, name = "R_NAME")
    private String rName;

    @Column(nullable = true, name = "R_CODE")
    private Integer rCode;

    @Column(nullable = true, name = "R_DESCRIBE")
    private String rDescribe;

    @Column(nullable = true, name = "ORG_ID", columnDefinition="bigint")
    private BigInteger orgId;

    @Column(nullable = true, name = "R_DEFAULT")
    private Integer rDefault;

    @Column(nullable = true, name = "create_account", columnDefinition="bigint")
    private BigInteger createAccount;

    @Column(nullable = true, name = "role_type", columnDefinition="bigint")
    private BigInteger roleType;

    @Column(nullable = true, name = "create_time")
    private Date createTime;

    @Column(nullable = true, name = "update_time")
    private Date updateTime;
    
    public RoleEntity() {
    	this.orgId = StringUtil.toBigInteger(1L);
    	this.rDefault = 0;
	}

    public BigInteger getrId() {
        return rId;
    }

    public void setrId(BigInteger rId) {
        this.rId = rId;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public Integer getrCode() {
        return rCode;
    }

    public void setrCode(Integer rCode) {
        this.rCode = rCode;
    }

    public String getrDescribe() {
        return rDescribe;
    }

    public void setrDescribe(String rDescribe) {
        this.rDescribe = rDescribe;
    }

    public BigInteger getOrgId() {
        return orgId;
    }

    public void setOrgId(BigInteger orgId) {
        this.orgId = orgId;
    }

    public Integer getrDefault() {
        return rDefault;
    }

    public void setrDefault(Integer rDefault) {
        this.rDefault = rDefault;
    }

    public BigInteger getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(BigInteger createAccount) {
        this.createAccount = createAccount;
    }

    public BigInteger getRoleType() {
        return roleType;
    }

    public void setRoleType(BigInteger roleType) {
        this.roleType = roleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
