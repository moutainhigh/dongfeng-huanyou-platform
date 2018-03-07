package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 特殊数据采集汇报设置
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-20
 **/
public class SpecialDataParam extends BaseTerminalParam {
    protected Integer dataCollectionLifeCycle;
    protected Integer canWay;
    protected Integer collectionWay;
    protected Integer dataFormat;
    protected Integer frameType;
    protected Integer baudRate;
    protected Integer dataCollectionInterval;
    protected Integer dataReportInterval;
    protected Integer alarmLimit;
    private String pidStr;

    public Integer getDataCollectionLifeCycle() {
        return dataCollectionLifeCycle;
    }

    public void setDataCollectionLifeCycle(Integer dataCollectionLifeCycle) {
        this.dataCollectionLifeCycle = dataCollectionLifeCycle;
    }

    public Integer getCanWay() {
        return canWay;
    }

    public void setCanWay(Integer canWay) {
        this.canWay = canWay;
    }

    public Integer getCollectionWay() {
        return collectionWay;
    }

    public void setCollectionWay(Integer collectionWay) {
        this.collectionWay = collectionWay;
    }

    public Integer getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(Integer dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Integer getFrameType() {
        return frameType;
    }

    public void setFrameType(Integer frameType) {
        this.frameType = frameType;
    }

    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
    }

    public Integer getDataCollectionInterval() {
        return dataCollectionInterval;
    }

    public void setDataCollectionInterval(Integer dataCollectionInterval) {
        this.dataCollectionInterval = dataCollectionInterval;
    }

    public Integer getDataReportInterval() {
        return dataReportInterval;
    }

    public void setDataReportInterval(Integer dataReportInterval) {
        this.dataReportInterval = dataReportInterval;
    }

    public Integer getAlarmLimit() {
        return alarmLimit;
    }

    public void setAlarmLimit(Integer alarmLimit) {
        this.alarmLimit = alarmLimit;
    }

    public String getPidStr() {
        return pidStr;
    }

    public void setPidStr(String pidStr) {
        this.pidStr = pidStr;
    }
}
