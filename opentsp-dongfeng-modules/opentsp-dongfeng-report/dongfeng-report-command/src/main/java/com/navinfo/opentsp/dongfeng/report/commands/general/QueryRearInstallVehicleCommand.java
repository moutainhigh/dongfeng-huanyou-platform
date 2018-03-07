package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 后装车辆统计状态
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class QueryRearInstallVehicleCommand extends BaseCommand<HttpCommandResultWithData> {

    private String queryTime;


    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    @Override
    public String toString() {
        return "QueryRearInstallVehicleCommand{" +
                "queryTime='" + queryTime + '\'' +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}