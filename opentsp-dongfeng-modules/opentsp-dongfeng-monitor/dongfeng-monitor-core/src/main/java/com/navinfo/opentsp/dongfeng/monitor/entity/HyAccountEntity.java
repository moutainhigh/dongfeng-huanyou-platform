package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Entity
@Table(name = "hy_account")
public class HyAccountEntity {
    private long accountId;
    private int district;
    private String accountName;
    private String accountNickname;
    private String accountPwd;
    private Long accountDate;
    private String accountState;
    private Long accountOrgId;
    private String accountLinkman;
    private String accountDescribe;
    private String accountLinktel;
    private String accountCompany;
    private Integer delFlag;
    private Integer accountType;
    private Long createAccount;
    private Integer jobType;
    private String lockAccount;
    private Long errorCount;
    private Long termVilidate;
    private Long loginFailedTime;

    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "DISTRICT", nullable = false)
    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Basic
    @Column(name = "ACCOUNT_NAME", nullable = true, length = 50)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = "ACCOUNT_NICKNAME", nullable = true, length = 200)
    public String getAccountNickname() {
        return accountNickname;
    }

    public void setAccountNickname(String accountNickname) {
        this.accountNickname = accountNickname;
    }

    @Basic
    @Column(name = "ACCOUNT_PWD", nullable = true, length = 50)
    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    @Basic
    @Column(name = "ACCOUNT_DATE", nullable = true)
    public Long getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Long accountDate) {
        this.accountDate = accountDate;
    }

    @Basic
    @Column(name = "ACCOUNT_STATE", nullable = true, length = 50)
    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    @Basic
    @Column(name = "ACCOUNT_ORG_ID", nullable = true)
    public Long getAccountOrgId() {
        return accountOrgId;
    }

    public void setAccountOrgId(Long accountOrgId) {
        this.accountOrgId = accountOrgId;
    }

    @Basic
    @Column(name = "ACCOUNT_LINKMAN", nullable = true, length = 200)
    public String getAccountLinkman() {
        return accountLinkman;
    }

    public void setAccountLinkman(String accountLinkman) {
        this.accountLinkman = accountLinkman;
    }

    @Basic
    @Column(name = "ACCOUNT_DESCRIBE", nullable = true, length = 2000)
    public String getAccountDescribe() {
        return accountDescribe;
    }

    public void setAccountDescribe(String accountDescribe) {
        this.accountDescribe = accountDescribe;
    }

    @Basic
    @Column(name = "ACCOUNT_LINKTEL", nullable = true, length = 50)
    public String getAccountLinktel() {
        return accountLinktel;
    }

    public void setAccountLinktel(String accountLinktel) {
        this.accountLinktel = accountLinktel;
    }

    @Basic
    @Column(name = "ACCOUNT_COMPANY", nullable = true, length = 200)
    public String getAccountCompany() {
        return accountCompany;
    }

    public void setAccountCompany(String accountCompany) {
        this.accountCompany = accountCompany;
    }

    @Basic
    @Column(name = "DEL_FLAG", nullable = true)
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "ACCOUNT_TYPE", nullable = true)
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "create_account", nullable = true)
    public Long getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(Long createAccount) {
        this.createAccount = createAccount;
    }

    @Basic
    @Column(name = "JOB_TYPE", nullable = true)
    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "LOCK_ACCOUNT", nullable = true, length = 100)
    public String getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(String lockAccount) {
        this.lockAccount = lockAccount;
    }

    @Basic
    @Column(name = "ERROR_COUNT", nullable = true)
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Basic
    @Column(name = "TERM_VILIDATE", nullable = true)
    public Long getTermVilidate() {
        return termVilidate;
    }

    public void setTermVilidate(Long termVilidate) {
        this.termVilidate = termVilidate;
    }

    @Basic
    @Column(name = "LOGIN_FAILED_TIME", nullable = true)
    public Long getLoginFailedTime() {
        return loginFailedTime;
    }

    public void setLoginFailedTime(Long loginFailedTime) {
        this.loginFailedTime = loginFailedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HyAccountEntity that = (HyAccountEntity) o;

        if (accountId != that.accountId) return false;
        if (district != that.district) return false;
        if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null) return false;
        if (accountNickname != null ? !accountNickname.equals(that.accountNickname) : that.accountNickname != null)
            return false;
        if (accountPwd != null ? !accountPwd.equals(that.accountPwd) : that.accountPwd != null) return false;
        if (accountDate != null ? !accountDate.equals(that.accountDate) : that.accountDate != null) return false;
        if (accountState != null ? !accountState.equals(that.accountState) : that.accountState != null) return false;
        if (accountOrgId != null ? !accountOrgId.equals(that.accountOrgId) : that.accountOrgId != null) return false;
        if (accountLinkman != null ? !accountLinkman.equals(that.accountLinkman) : that.accountLinkman != null)
            return false;
        if (accountDescribe != null ? !accountDescribe.equals(that.accountDescribe) : that.accountDescribe != null)
            return false;
        if (accountLinktel != null ? !accountLinktel.equals(that.accountLinktel) : that.accountLinktel != null)
            return false;
        if (accountCompany != null ? !accountCompany.equals(that.accountCompany) : that.accountCompany != null)
            return false;
        if (delFlag != null ? !delFlag.equals(that.delFlag) : that.delFlag != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (createAccount != null ? !createAccount.equals(that.createAccount) : that.createAccount != null)
            return false;
        if (jobType != null ? !jobType.equals(that.jobType) : that.jobType != null) return false;
        if (lockAccount != null ? !lockAccount.equals(that.lockAccount) : that.lockAccount != null) return false;
        if (errorCount != null ? !errorCount.equals(that.errorCount) : that.errorCount != null) return false;
        if (termVilidate != null ? !termVilidate.equals(that.termVilidate) : that.termVilidate != null) return false;
        if (loginFailedTime != null ? !loginFailedTime.equals(that.loginFailedTime) : that.loginFailedTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + district;
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (accountNickname != null ? accountNickname.hashCode() : 0);
        result = 31 * result + (accountPwd != null ? accountPwd.hashCode() : 0);
        result = 31 * result + (accountDate != null ? accountDate.hashCode() : 0);
        result = 31 * result + (accountState != null ? accountState.hashCode() : 0);
        result = 31 * result + (accountOrgId != null ? accountOrgId.hashCode() : 0);
        result = 31 * result + (accountLinkman != null ? accountLinkman.hashCode() : 0);
        result = 31 * result + (accountDescribe != null ? accountDescribe.hashCode() : 0);
        result = 31 * result + (accountLinktel != null ? accountLinktel.hashCode() : 0);
        result = 31 * result + (accountCompany != null ? accountCompany.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (createAccount != null ? createAccount.hashCode() : 0);
        result = 31 * result + (jobType != null ? jobType.hashCode() : 0);
        result = 31 * result + (lockAccount != null ? lockAccount.hashCode() : 0);
        result = 31 * result + (errorCount != null ? errorCount.hashCode() : 0);
        result = 31 * result + (termVilidate != null ? termVilidate.hashCode() : 0);
        result = 31 * result + (loginFailedTime != null ? loginFailedTime.hashCode() : 0);
        return result;
    }
}
