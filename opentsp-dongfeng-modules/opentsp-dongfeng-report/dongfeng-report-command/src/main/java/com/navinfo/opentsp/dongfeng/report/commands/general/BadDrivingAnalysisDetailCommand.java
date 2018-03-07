package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * Created by zhangtiantong on 2018/2/28/028.
 */
public class BadDrivingAnalysisDetailCommand extends BaseReportCommand {

    /**
     * 底盘号
     */
    @NotNull(message = "底盘号 不能为空", groups = {GroupCommand.class})
    private String chassisNum;

    /**
     * 时间范围
     */
    @NotNull(message = "查询日期 不能为空", groups = {GroupCommand.class})
    private String dateStr;


    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public String toString() {
        return "BadDrivingAnalysisDetailCommand{" +
                "chassisNum='" + chassisNum + '\'' +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }
}