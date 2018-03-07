package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


import java.math.BigInteger;
import java.util.List;

/**
 * Created by yaocy on 2016/03/15.
 */
public class UserDetailOutDto {

    private BigInteger accountId;

    private String accountName;

    private String accountNickname;

    private String accountPwd;

    private String accountLinktel;

    private String accountType;

    private String jobType;

    private String accountDescribe;

    private String lockAccount;

    private String termVilidate;

    private BigInteger createAccount;

    private String email;

    private List<UserAttributeOutDto> role;

    private UserAttributeOutDto customer;

    private List<UserAttributeOutDto> team;

    private List<UserAttributeOutDto> serviceStation;

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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getAccountDescribe() {
        return accountDescribe;
    }

    public void setAccountDescribe(String accountDescribe) {
        this.accountDescribe = accountDescribe;
    }

    public List<UserAttributeOutDto> getRole() {
        return role;
    }

    public void setRole(List<UserAttributeOutDto> role) {
        this.role = role;
    }

    public UserAttributeOutDto getCustomer() {
        return customer;
    }

    public void setCustomer(UserAttributeOutDto customer) {
        this.customer = customer;
    }

    public List<UserAttributeOutDto> getTeam() {
        return team;
    }

    public void setTeam(List<UserAttributeOutDto> team) {
        this.team = team;
    }

    public List<UserAttributeOutDto> getServiceStation() {
        return serviceStation;
    }

    public void setServiceStation(List<UserAttributeOutDto> serviceStation) {
        this.serviceStation = serviceStation;
    }

    public BigInteger getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(BigInteger createAccount) {
        this.createAccount = createAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
