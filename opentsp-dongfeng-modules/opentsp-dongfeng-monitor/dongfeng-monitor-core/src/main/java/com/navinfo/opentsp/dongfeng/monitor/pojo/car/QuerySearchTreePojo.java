package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import com.navinfo.opentsp.dongfeng.monitor.entity.HyCarEntity;

import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
public class QuerySearchTreePojo extends HyCarEntity {
    private BigInteger commId;//终端通信号

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }
}
