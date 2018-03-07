package com.navinfo.opentsp.dongfeng.report.commands.saleremoval;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class QuerySaleRemovalCommand extends BaseReportCommand {

    private String chassisNum;//底盘号
    private String carOwner;//所属客户
    private String carType;//车辆型号
    private String carCph;//车牌号
    private String teamName;//经销商
    private String beginRemovalTime;//出库时间-开始
    private String endRemovalTime;//出库时间-结束
    private String terminalFKCode;//防拆盒
    private String engType;//发动机类型
    private String beginOfflineTime;//下线时间-开始
    private String endOfflineTime;//下线时间-结束
    private String terminalBDCode;//北斗

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarCph() {
        return carCph;
    }

    public void setCarCph(String carCph) {
        this.carCph = carCph;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBeginRemovalTime() {
        return beginRemovalTime;
    }

    public void setBeginRemovalTime(String beginRemovalTime) {
        this.beginRemovalTime = beginRemovalTime;
    }

    public String getEndRemovalTime() {
        return endRemovalTime;
    }

    public void setEndRemovalTime(String endRemovalTime) {
        this.endRemovalTime = endRemovalTime;
    }

    public String getTerminalFKCode() {
        return terminalFKCode;
    }

    public void setTerminalFKCode(String terminalFKCode) {
        this.terminalFKCode = terminalFKCode;
    }

    public String getEngType() {
        return engType;
    }

    public void setEngType(String engType) {
        this.engType = engType;
    }

    public String getBeginOfflineTime() {
        return beginOfflineTime;
    }

    public void setBeginOfflineTime(String beginOfflineTime) {
        this.beginOfflineTime = beginOfflineTime;
    }

    public String getEndOfflineTime() {
        return endOfflineTime;
    }

    public void setEndOfflineTime(String endOfflineTime) {
        this.endOfflineTime = endOfflineTime;
    }

    public String getTerminalBDCode() {
        return terminalBDCode;
    }

    public void setTerminalBDCode(String terminalBDCode) {
        this.terminalBDCode = terminalBDCode;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}