package com.navinfo.opentsp.dongfeng.report.commands.salestate;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class QuerySaleStateCommand extends BaseReportCommand {

    private String chassisNum;//底盘号
    private String carOwner;//所属客户
    private String carType;//车辆型号
    private String beginSaleDate;//销售时间-开始
    private String endSaleDate;//销售时间-结束
    private String carCph;//车牌号
    private String teamName;//经销商
    private String beginRegisterTime;//注册时间-开始
    private String endRegisterTime;//注册时间-结束
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

    public String getBeginSaleDate() {
        return beginSaleDate;
    }

    public void setBeginSaleDate(String beginSaleDate) {
        this.beginSaleDate = beginSaleDate;
    }

    public String getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(String endSaleDate) {
        this.endSaleDate = endSaleDate;
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

    public String getBeginRegisterTime() {
        return beginRegisterTime;
    }

    public void setBeginRegisterTime(String beginRegisterTime) {
        this.beginRegisterTime = beginRegisterTime;
    }

    public String getEndRegisterTime() {
        return endRegisterTime;
    }

    public void setEndRegisterTime(String endRegisterTime) {
        this.endRegisterTime = endRegisterTime;
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