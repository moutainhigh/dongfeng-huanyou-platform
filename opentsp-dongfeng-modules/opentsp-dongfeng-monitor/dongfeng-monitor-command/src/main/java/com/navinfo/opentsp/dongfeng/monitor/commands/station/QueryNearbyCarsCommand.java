package com.navinfo.opentsp.dongfeng.monitor.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/29
 */
public class QueryNearbyCarsCommand extends BaseCommand<HttpCommandResultWithData> {

    //底盘号
    private String chassisNum;
    //服务站ID
    @NotNull(message = "服务站ID 不能为NULL", groups = {GroupCommand.class})
    private Long stationId;
    //服务站ID
    @NotNull(message = "位置 不能为NULL", groups = {GroupCommand.class})
    private Integer locType;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Integer getLocType() {
        return locType;
    }

    public void setLocType(Integer locType) {
        this.locType = locType;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
    @Override
    public String toString() {
        return "QueryNearbyCarsCommand{}";
    }
}
