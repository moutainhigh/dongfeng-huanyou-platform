package com.navinfo.opentsp.dongfeng.kafka.pojo;

import java.math.BigInteger;

/**
 * @wenya
 * @create 2017-04-01 10:06
 **/
public class CarInfoPojo {
    private BigInteger carId;     //车辆id
    private BigInteger terminalId; //终端id
    private Integer lockStatue;  //锁车状态
    private Integer terminalType;   //终端类型
    private String operateUser;  //操作人

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public Integer getLockStatue() {
        return lockStatue;
    }

    public void setLockStatue(Integer lockStatue) {
        this.lockStatue = lockStatue;
    }

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }
}
