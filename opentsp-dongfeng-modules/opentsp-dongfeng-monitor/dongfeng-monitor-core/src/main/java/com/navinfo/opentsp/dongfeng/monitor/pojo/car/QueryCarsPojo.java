package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryCarsPojo {
    //车辆主键
    private BigInteger id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }
}
