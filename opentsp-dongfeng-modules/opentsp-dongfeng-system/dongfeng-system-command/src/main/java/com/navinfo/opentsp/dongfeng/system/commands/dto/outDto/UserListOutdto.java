package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


import java.math.BigInteger;

/**
 * Created by yaocy on 2016/03/08.
 */
public class UserListOutdto {

    private BigInteger accountId;

    private String accountName;

    private String accountType;

    private String jobType;

    private String accountLinktel;

    private String lockAccount;

    private String expiredStatus;

    private String termVilidate;

    private String createAccount;

    private String accountDate;

    private String password;

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getAccountLinktel() {
        return accountLinktel;
    }

    public void setAccountLinktel(String accountLinktel) {
        this.accountLinktel = accountLinktel;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(String lockAccount) {
        this.lockAccount = lockAccount;
    }

    public String getTermVilidate() {
        return termVilidate;
    }

    public void setTermVilidate(String termVilidate) {
        this.termVilidate = termVilidate;
    }

    public String getExpiredStatus() {
        return expiredStatus;
    }

    public void setExpiredStatus(String expiredStatus) {
        this.expiredStatus = expiredStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
