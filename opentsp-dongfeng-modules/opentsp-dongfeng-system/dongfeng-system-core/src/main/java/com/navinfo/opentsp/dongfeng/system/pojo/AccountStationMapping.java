package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * Created by yaocy on 2017/03/09.
 */

public class AccountStationMapping {

    private BigInteger accountId;

    private BigInteger stationId;

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public BigInteger getStationId() {
        return stationId;
    }

    public void setStationId(BigInteger stationId) {
        this.stationId = stationId;
    }
}
