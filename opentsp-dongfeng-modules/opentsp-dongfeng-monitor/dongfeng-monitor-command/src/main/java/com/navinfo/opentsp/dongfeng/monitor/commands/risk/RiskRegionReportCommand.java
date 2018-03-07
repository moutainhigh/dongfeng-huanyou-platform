package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
public class RiskRegionReportCommand extends BaseCommand<HttpCommandResultWithData> {
    private String carIds;//车辆id,多个用逗号隔开
    private String teamIds;//组id,多个用逗号隔开
    private String chassisNum;    //底盘号
    private String stdSalesStatus;    //销售状态 0 已售 ，1 未售
    private String aakSalesStatus;    //销售状态 0 已售 ，1 未售
    private String preventionStatus;//防控状态
    private String teamName;//经销商名称

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

    public String getStdSalesStatus() {
        return stdSalesStatus;
    }

    public void setStdSalesStatus(String stdSalesStatus) {
        this.stdSalesStatus = stdSalesStatus;
    }

    public String getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(String aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
    }

    public String getPreventionStatus() {
        return preventionStatus;
    }

    public void setPreventionStatus(String preventionStatus) {
        this.preventionStatus = preventionStatus;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}