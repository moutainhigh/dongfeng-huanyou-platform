package com.navinfo.opentsp.dongfeng.openapi.commands.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

public class StationSynQueryCommand extends BaseOpenApiCommand {

    @NotNull(message = "开始时间 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "开始时间 不能为空", groups = {GroupCommand.class})
    private String beginTime;
    @NotNull(message = "结束时间 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "结束时间 不能为空", groups = {GroupCommand.class})
    private String endTime;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}