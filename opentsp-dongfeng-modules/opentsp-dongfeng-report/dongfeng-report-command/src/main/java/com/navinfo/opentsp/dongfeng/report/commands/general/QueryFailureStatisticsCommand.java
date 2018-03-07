package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangtiantong on 2018/1/31/031.
 * 后装车辆详情信息
 *
 * @author ztt
 * @version 1.0
 * @date 2018-01-31
 * @modify
 * @copyright Navinfo
 */
public class QueryFailureStatisticsCommand extends BaseReportCommand {

    /**
     * 查询时间段
     */
    @NotNull(message = "查询时间段", groups = {GroupCommand.class})
    private String queryTime;

    /**
     * SPN
     */
    private String spn;

    /**
     * FMI
     */
    private String fmi;


    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getSpn() {
        return spn;
    }

    public void setSpn(String spn) {
        this.spn = spn;
    }

    public String getFmi() {
        return fmi;
    }

    public void setFmi(String fmi) {
        this.fmi = fmi;
    }

    @Override
    public String toString() {
        return "QueryFailureStatisticsCommand{" +
                "queryTime='" + queryTime + '\'' +
                ", spn='" + spn + '\'' +
                ", fmi='" + fmi + '\'' +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}