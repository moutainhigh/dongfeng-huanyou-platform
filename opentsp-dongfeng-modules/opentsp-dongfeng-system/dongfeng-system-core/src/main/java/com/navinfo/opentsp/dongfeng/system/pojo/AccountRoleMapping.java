package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/09.
 */

public class AccountRoleMapping {

    private BigInteger accountId;

    private int rCode;

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public int getrCode() {
        return rCode;
    }

    public void setrCode(int rCode) {
        this.rCode = rCode;
    }
}
