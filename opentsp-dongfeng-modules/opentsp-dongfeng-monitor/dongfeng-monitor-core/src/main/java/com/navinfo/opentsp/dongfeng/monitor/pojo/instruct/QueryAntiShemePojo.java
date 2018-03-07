package com.navinfo.opentsp.dongfeng.monitor.pojo.instruct;

import java.math.BigInteger;

/**
 * @wenya
 * @create 2017-03-23 16:02
 **/
//防拆方案中用到的对象
public class QueryAntiShemePojo {
    //车辆id
    private BigInteger carId;
    //一体机终端id
    private BigInteger terminalId;
    //fk终端id
    private BigInteger car_termianlId;
    //一体机适用车辆类型
    private BigInteger bdApplianceCarType;
    //fk适用车辆类型
    private BigInteger fkApplianceCarType;

    public BigInteger getBdApplianceCarType() {
        return bdApplianceCarType;
    }

    public void setBdApplianceCarType(BigInteger bdApplianceCarType) {
        this.bdApplianceCarType = bdApplianceCarType;
    }

    public BigInteger getCar_termianlId() {
        return car_termianlId;
    }

    public void setCar_termianlId(BigInteger car_termianlId) {
        this.car_termianlId = car_termianlId;
    }

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public BigInteger getFkApplianceCarType() {
        return fkApplianceCarType;
    }

    public void setFkApplianceCarType(BigInteger fkApplianceCarType) {
        this.fkApplianceCarType = fkApplianceCarType;
    }

    public BigInteger getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(BigInteger terminalId) {
        this.terminalId = terminalId;
    }
}
