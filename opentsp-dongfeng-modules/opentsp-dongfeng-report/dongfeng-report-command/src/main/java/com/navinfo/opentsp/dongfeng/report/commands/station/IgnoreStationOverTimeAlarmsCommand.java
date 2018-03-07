package com.navinfo.opentsp.dongfeng.report.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Ignore Service Station OverTimeAlarms Command
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-19
 * @modify
 * @copyright Navi Tsp
 */
public class IgnoreStationOverTimeAlarmsCommand extends BaseCommand<HttpCommandResultWithData> {

    private String parkId;

    private String inboundTime;

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(String inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "IgnoreStationOverTimeAlarmsCommand{" +
                "parkId='" + parkId + '\'' +
                ", inboundTime='" + inboundTime + '\'' +
                '}';
    }
}