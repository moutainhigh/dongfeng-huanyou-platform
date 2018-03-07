package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

import java.math.BigInteger;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
public class VehiclePojo {
    private BigInteger carId;

    private BigInteger commId;//终端通信号

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public BigInteger getCommId() {
        return commId;
    }

    public void setCommId(BigInteger commId) {
        this.commId = commId;
    }
}
