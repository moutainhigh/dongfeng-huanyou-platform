package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-18
 **/
public class CanBusDataPojo {
    private BigInteger pid;
    private String canId;
    private Integer beginIndex;
    private Integer dataLength;

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }
}
