package com.navinfo.opentsp.dongfeng.report.commands.oil;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 胎温胎压
 *
 * @author sunlin
 * @version 1.0
 * @date 2018-02-28
 */
public class QueryTireCommand extends BaseReportCommand {
    @NotNull(message = "通信号不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "通信号不能为空", groups = {GroupCommand.class})
    private String communicationId;//北斗一体机ID
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String dateStr;//时间


    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override

    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}