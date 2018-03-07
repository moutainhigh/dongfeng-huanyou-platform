package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by yaocy on 2017/03/15.
 */
public class RoleListPojo {

    private BigInteger id;

    private String name;

    private Integer code;

    private String createAccount;

    private BigInteger roleType;

    private Date createTime;

    private Date updateTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
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
