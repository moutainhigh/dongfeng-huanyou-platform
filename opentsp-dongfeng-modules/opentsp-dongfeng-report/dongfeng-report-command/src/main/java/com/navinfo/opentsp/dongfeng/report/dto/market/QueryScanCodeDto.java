package com.navinfo.opentsp.dongfeng.report.dto.market;

/**
 * Vehicle Scan Code DTO
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
public class QueryScanCodeDto {
    private String chassisNum;
    private String plateNum;
    private String terminalId;
    private String sim;
    private String dealer;
    private String scanOpera;
    private String operator;
    private String operaDate;
    private String lon = "0.0";
    private String lat = "0.0";
    private String remark;
    private Integer syncState;
    private String syncStateDesc;

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

    public String getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(String operaDate) {
        this.operaDate = operaDate;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSyncState() {
        return syncState;
    }

    public void setSyncState(Integer syncState) {
        this.syncState = syncState;
    }

    public String getSyncStateDesc() {
        return syncStateDesc;
    }

    public void setSyncStateDesc(String syncStateDesc) {
        this.syncStateDesc = syncStateDesc;
    }

    @Override
    public String toString() {
        return "QueryScanCodeDto{" +
                "chassisNum='" + chassisNum + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", sim='" + sim + '\'' +
                ", dealer='" + dealer + '\'' +
                ", scanOpera='" + scanOpera + '\'' +
                ", operator='" + operator + '\'' +
                ", operaDate='" + operaDate + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", remark='" + remark + '\'' +
                ", syncState=" + syncState +
                ", syncStateDesc='" + syncStateDesc + '\'' +
                '}';
    }
}
