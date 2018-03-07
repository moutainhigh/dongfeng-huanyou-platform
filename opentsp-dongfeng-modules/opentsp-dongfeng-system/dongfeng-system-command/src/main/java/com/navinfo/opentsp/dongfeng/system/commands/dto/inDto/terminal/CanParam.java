package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * can数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class CanParam extends BaseTerminalParam {
    private Integer canReportTimeWindow;// CAN上报时间间隔
    private Integer dataCollectionInterval;// CAN上报距离间隔
    private Integer canReportWay;// CAN上报通道

    public Integer getCanReportTimeWindow() {
        return canReportTimeWindow;
    }

    public void setCanReportTimeWindow(Integer canReportTimeWindow) {
        this.canReportTimeWindow = canReportTimeWindow;
    }

    public Integer getDataCollectionInterval() {
        return dataCollectionInterval;
    }

    public void setDataCollectionInterval(Integer dataCollectionInterval) {
        this.dataCollectionInterval = dataCollectionInterval;
    }

    public Integer getCanReportWay() {
        return canReportWay;
    }

    public void setCanReportWay(Integer canReportWay) {
        this.canReportWay = canReportWay;
    }
}
