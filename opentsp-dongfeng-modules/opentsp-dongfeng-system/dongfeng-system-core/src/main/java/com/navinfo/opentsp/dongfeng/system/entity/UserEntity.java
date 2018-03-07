package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/08.
 */
@Entity
@Table(name = "hy_account")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false, name = "ACCOUNT_ID", columnDefinition="bigint")
    private BigInteger accountId;

    @Column(nullable = true, name = "DISTRICT")
    private int district;

    @Column(nullable = true, name = "ACCOUNT_NAME")
    private String accountName;

    @Column(nullable = true, name = "ACCOUNT_NICKNAME")
    private String accountNickname;

    @Column(nullable = true, name = "ACCOUNT_PWD")
    private String accountPwd;

    @Column(nullable = true, name = "ACCOUNT_DATE", columnDefinition="bigint")
    private BigInteger accountDate;

    @Column(nullable = true, name = "ACCOUNT_STATE")
    private String accountState;

    @Column(nullable = true, name = "ACCOUNT_ORG_ID", columnDefinition="bigint")
    private BigInteger accountOrgId;

    @Column(nullable = true, name = "ACCOUNT_LINKMAN")
    private String accountLinkman;

    @Column(nullable = true, name = "ACCOUNT_DESCRIBE")
    private String accountDescribe;

    @Column(nullable = true, name = "ACCOUNT_LINKTEL")
    private String accountLinktel;

    @Column(nullable = true, name = "ACCOUNT_COMPANY")
    private String accountCompany;

    @Column(nullable = true, name = "DEL_FLAG")
    private Integer delFlag;

    @Column(nullable = true, name = "ACCOUNT_TYPE")
    private Integer accountType;

    @Column(nullable = true, name = "create_account", columnDefinition="bigint")
    private BigInteger createAccount;

    @Column(nullable = true, name = "JOB_TYPE")
    private Integer jobType;

    @Column(nullable = true, name = "LOCK_ACCOUNT")
    private String lockAccount;

    @Column(nullable = true, name = "ERROR_COUNT", columnDefinition="bigint")
    private BigInteger errorCount;

    @Column(nullable = true, name = "TERM_VILIDATE", columnDefinition="bigint")
    private BigInteger termVilidate;

    @Column(nullable = true, name = "LOGIN_FAILED_TIME", columnDefinition="bigint")
    private BigInteger loginFailedTime;

    @Column(nullable = true, name = "ACCOUNT_EMAIL")
    private String email;

    @Column(nullable = true, name = "ACCOUNT_IMAGE")
    private String image;

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNickname() {
        return accountNickname;
    }

    public void setAccountNickname(String accountNickname) {
        this.accountNickname = accountNickname;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public BigInteger getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(BigInteger accountDate) {
        this.accountDate = accountDate;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public BigInteger getAccountOrgId() {
        return accountOrgId;
    }

    public void setAccountOrgId(BigInteger accountOrgId) {
        this.accountOrgId = accountOrgId;
    }

    public String getAccountLinkman() {
        return accountLinkman;
    }

    public void setAccountLinkman(String accountLinkman) {
        this.accountLinkman = accountLinkman;
    }

    public String getAccountDescribe() {
        return accountDescribe;
    }

    public void setAccountDescribe(String accountDescribe) {
        this.accountDescribe = accountDescribe;
    }

    public String getAccountLinktel() {
        return accountLinktel;
    }

    public void setAccountLinktel(String accountLinktel) {
        this.accountLinktel = accountLinktel;
    }

    public String getAccountCompany() {
        return accountCompany;
    }

    public void setAccountCompany(String accountCompany) {
        this.accountCompany = accountCompany;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public BigInteger getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(BigInteger createAccount) {
        this.createAccount = createAccount;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(String lockAccount) {
        this.lockAccount = lockAccount;
    }

    public BigInteger getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(BigInteger errorCount) {
        this.errorCount = errorCount;
    }

    public BigInteger getTermVilidate() {
        return termVilidate;
    }

    public void setTermVilidate(BigInteger termVilidate) {
        this.termVilidate = termVilidate;
    }

    public BigInteger getLoginFailedTime() {
        return loginFailedTime;
    }

    public void setLoginFailedTime(BigInteger loginFailedTime) {
        this.loginFailedTime = loginFailedTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
