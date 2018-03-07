package com.navinfo.opentsp.dongfeng.report.pojo.market;

import java.math.BigInteger;

/**
 * 在库车辆实体
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-05
 * @modify
 * @copyright Navi Tsp
 */
public class ScanCodeVehiclePojo {
    private BigInteger carId;

    private String plateNum;

    private String chassisNum;

    private String code;

    private String sim;

    private BigInteger carTeamId;

    public BigInteger getCarId() {
        return carId;
    }

    public void setCarId(BigInteger carId) {
        this.carId = carId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public BigInteger getCarTeamId() {
        return carTeamId;
    }

    public void setCarTeamId(BigInteger carTeamId) {
        this.carTeamId = carTeamId;
    }
}
