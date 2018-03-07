package com.navinfo.opentsp.dongfeng.report.commands.oil;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 油量异常报表折线图
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */
public class QueryOilChartCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "终端通讯号不能为空", groups = {GroupCommand.class})
    private Long communicationId;
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String dateStr;
    @NotNull(message = "邮箱容量不能为空", groups = {GroupCommand.class})
    private Double oilCapacity;

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

    public Double getOilCapacity() {
        return oilCapacity;
    }

    public void setOilCapacity(Double oilCapacity) {
        this.oilCapacity = oilCapacity;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}