package com.navinfo.opentsp.dongfeng.system.pojo;


import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/08.
 * 用户列表Pojo
 */
public class UserListPojo {

    private BigInteger accountId;

    private String accountName;

    private BigInteger accountDate;

    private String accountLinktel;

    private Integer accountType;

    private Integer jobType;

    private String createAccount;

    private String lockAccount;

    private BigInteger termVilidate;

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

    public BigInteger getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(BigInteger accountDate) {
        this.accountDate = accountDate;
    }

    public String getAccountLinktel() {
        return accountLinktel;
    }

    public void setAccountLinktel(String accountLinktel) {
        this.accountLinktel = accountLinktel;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
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

    public BigInteger getTermVilidate() {
        return termVilidate;
    }

    public void setTermVilidate(BigInteger termVilidate) {
        this.termVilidate = termVilidate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
