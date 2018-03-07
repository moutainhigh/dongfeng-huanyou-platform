package com.navinfo.opentsp.dongfeng.monitor.pojo.instruct;

import java.math.BigInteger;

/**
 * 获取锁车方案下拉菜单对象
 *
 * @wenya
 * @create 2017-03-23 17:54
 **/
public class QueryLockMenuPojo {
    //车辆id
    private BigInteger carId;
    //一体机终端id
    private BigInteger terminalId;
    //fk终端id
    private BigInteger car_termianlId;

    public BigInteger getCar_termianlId() {
        return car_termianlId;
    }

    public void setCar_termianlId(BigInteger car_termianlId) {
        this.car_termianlId = car_termianlId;
    }

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }
}
