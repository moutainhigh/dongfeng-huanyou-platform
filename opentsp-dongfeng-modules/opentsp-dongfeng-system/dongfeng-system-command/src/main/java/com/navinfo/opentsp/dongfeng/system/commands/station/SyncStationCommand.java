package com.navinfo.opentsp.dongfeng.system.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 同步服务站
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class SyncStationCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "服务站ID 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站ID 不能为空", groups = {GroupCommand.class})
    private String stationIds;

    @NotNull(message = "同步类型 不能为空", groups = {GroupCommand.class})
    private Integer syncType;

    @NotNull(message = "操作类型 不能为空", groups = {GroupCommand.class})
    private Integer operateType;


    public String getStationIds() {
        return stationIds;
    }

    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }

    public Integer getSyncType() {
        return syncType;
    }

    public void setSyncType(Integer syncType) {
        this.syncType = syncType;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}