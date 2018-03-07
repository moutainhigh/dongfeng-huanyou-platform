package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by yaocy on 2017/03/10.
 * 修改用户
 */
public class EditUserCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "账号ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号ID不能为空白", groups = {GroupCommand.class})
    private String accountId;

    @NotNull(message = "账号名不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号名不能为空白", groups = {GroupCommand.class})
    private String accountType;

    private String accountName;

    private String accountNickname;

    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "密码不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "密码长度为8到16位，必须包含大、小写字母、数字", groups = {GroupCommand.class})
    private String accountPwd;

    @NotNull(message = "确认密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "确认密码不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "密码长度为8到16位，必须包含大、小写字母、数字", groups = {GroupCommand.class})
    private String confirmPwd;

    private String accountLinktel;

    private String accountDescribe;

    private String lockAccount;

    private String role;

    private String team;

    private String customer;

    private String serviceStation;

    private String jobType;

    private String termVilidate;

    private String createAccount;

    private String email;

    @AssertTrue(message = "密码和确认密码不一致", groups = {GroupCommand.class})
    private boolean isMather() {
        return accountPwd.equals(confirmPwd) ? true : false;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getAccountLinktel() {
        return accountLinktel;
    }

    public void setAccountLinktel(String accountLinktel) {
        this.accountLinktel = accountLinktel;
    }

    public String getAccountDescribe() {
        return accountDescribe;
    }

    public void setAccountDescribe(String accountDescribe) {
        this.accountDescribe = accountDescribe;
    }

    public String getLockAccount() {
        return lockAccount;
    }

    public void setLockAccount(String lockAccount) {
        this.lockAccount = lockAccount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getServiceStation() {
        return serviceStation;
    }

    public void setServiceStation(String serviceStation) {
        this.serviceStation = serviceStation;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getTermVilidate() {
        return termVilidate;
    }

    public void setTermVilidate(String termVilidate) {
        this.termVilidate = termVilidate;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    @Override
    public String toString() {
        return "EditUserCommand{" +
                "accountId='" + accountId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountNickname='" + accountNickname + '\'' +
                ", accountPwd='" + accountPwd + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                ", accountLinktel='" + accountLinktel + '\'' +
                ", accountDescribe='" + accountDescribe + '\'' +
                ", lockAccount='" + lockAccount + '\'' +
                ", role='" + role + '\'' +
                ", team='" + team + '\'' +
                ", customer='" + customer + '\'' +
                ", serviceStation='" + serviceStation + '\'' +
                ", jobType='" + jobType + '\'' +
                ", termVilidate='" + termVilidate + '\'' +
                ", createAccount='" + createAccount + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
