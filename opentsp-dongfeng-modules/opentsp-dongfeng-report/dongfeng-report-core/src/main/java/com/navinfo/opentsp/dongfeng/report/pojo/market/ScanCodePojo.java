package com.navinfo.opentsp.dongfeng.report.pojo.market;

import java.math.BigInteger;
import java.util.Date;

/**
 * Vehicle Scan Code DO
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
public class ScanCodePojo {
    private String chassisNum;
    private String plateNum;
    private String terminalId;
    private String sim;
    private String dealer;
    private String scanOpera;
    private String operator;
    private Date operaDate;
    private String location;
    private String remark;
    private BigInteger syncState;

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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getScanOpera() {
        return scanOpera;
    }

    public void setScanOpera(String scanOpera) {
        this.scanOpera = scanOpera;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigInteger getSyncState() {
        return syncState;
    }

    public void setSyncState(BigInteger syncState) {
        this.syncState = syncState;
    }
}
