package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 全国车次统计
 *
 * @wenya
 * @create 2017-03-28 13:45
 **/
public class QueryCarCountCommand extends BaseReportCommand {
    //省市编码 0：全国
    @NotNull(message = "省市编码不能为空", groups = {GroupCommand.class})
    private String govCode;
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String time;//查询时间 yyyy年MM月
    //查询方式 1：地图 2：图表
    @NotNull(message = "查询方式", groups = {GroupCommand.class})
    private String type;

    public String getGovCode() {
        return govCode;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryCarCountCommand{" +
                "govCode='" + govCode + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
