package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/17
 */
public class QueryLocInfosCommand extends BaseCommand<HttpCommandResultWithData> {

    private String carIds;
    private String teamIds;
    private String chassisNum;

    public String getCarIds() {
        return carIds;
    }

    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    public String getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(String teamIds) {
        this.teamIds = teamIds;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryLocInfosCommand{}";
    }
}
