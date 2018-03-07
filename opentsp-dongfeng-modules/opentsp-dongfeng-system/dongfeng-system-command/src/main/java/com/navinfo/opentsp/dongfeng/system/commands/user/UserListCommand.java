package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Created by yaocy on 2017/03/08.
 * 用户查询
 */
public class UserListCommand extends BaseReportCommand {

    private String accountName; //账号

    private String accountType; //用户类型

    private String lockAccount; //锁定状态

    private String pastDue; //过期状态

    private String createAccount; //创建人

    private String businessName; //关联客户

    private String dealerName; //关联经销商

    private String stationName; //关联服务站

    private String roleName; //关联角色

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getPastDue() {
        return pastDue;
    }

    public void setPastDue(String pastDue) {
        this.pastDue = pastDue;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "UserListCommand{" +
                "accountName='" + accountName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", lockAccount='" + lockAccount + '\'' +
                ", pastDue='" + pastDue + '\'' +
                ", createAccount='" + createAccount + '\'' +
                ", businessName='" + businessName + '\'' +
                ", dealerName='" + dealerName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
