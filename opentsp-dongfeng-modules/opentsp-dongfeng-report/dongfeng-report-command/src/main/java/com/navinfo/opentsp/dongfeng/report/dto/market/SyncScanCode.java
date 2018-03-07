package com.navinfo.opentsp.dongfeng.report.dto.market;

/**
 * @author wt
 * @version 1.0
 * @date 2017/10/16
 * @modify
 * @copyright
 */
public class SyncScanCode {
    private String chassisNum;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    @Override
    public String toString() {
        return "SyncScanCode{" +
                "chassisNum='" + chassisNum + '\'' +
                '}';
    }
}
