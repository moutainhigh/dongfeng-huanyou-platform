package com.navinfo.opentsp.dongfeng.monitor.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询服务站评价
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
public class QueryStationCommentsCommand extends BaseCommand<HttpCommandResultWithData> {
    //服务站ID
    @NotNull(message = "服务站ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站ID 不能为空", groups = {GroupCommand.class})
    private String stationId;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationCommentsCommand{" +
                "stationId='" + stationId + '\'' +
                '}';
    }
}