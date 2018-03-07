package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询异常油耗事件
 *
 * @author: zhangtiantong
 * @version: 1.0
 * @since: 2018-02-28
 */
public class QueryStealOilCommand extends BaseReportCommand {

    @NotNull(message = "终端标识 不能为空", groups = {GroupCommand.class})
    private Long communicationId;//终端标识
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String dateStr;//查询时间段
    @NotNull(message = "类型 不能为空", groups = {GroupCommand.class})
    private Integer type;//类型（1加油 2 减油 3 全部）

    public Long getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(Long communicationId) {
        this.communicationId = communicationId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QueryStealOilCommand{" +
                "communicationId=" + communicationId +
                ", dateStr='" + dateStr + '\'' +
                ", type=" + type +
                '}';
    }
}
