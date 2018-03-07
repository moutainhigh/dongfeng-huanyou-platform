package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 实时数据采集汇报设置
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-20
 **/
public class RealTimeParam extends BaseTerminalParam {
    /**
     * 实时数据采集时间间隔,单位：ms
     */
    private Integer dataCollectionInterval;
    /**
     * 实时数据上报时间间隔,单位：s
     */
    private Integer dataReportInterval;
    /**
     * 实时数据采集生命周期,单位：min
     */
    private Integer dataCollectionLifeCycle;

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

    public Integer getDataCollectionLifeCycle() {
        return dataCollectionLifeCycle;
    }

    public void setDataCollectionLifeCycle(Integer dataCollectionLifeCycle) {
        this.dataCollectionLifeCycle = dataCollectionLifeCycle;
    }
}
