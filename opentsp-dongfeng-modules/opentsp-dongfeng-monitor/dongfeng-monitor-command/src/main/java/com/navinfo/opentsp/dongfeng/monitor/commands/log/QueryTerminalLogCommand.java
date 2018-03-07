package com.navinfo.opentsp.dongfeng.monitor.commands.log;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 查询终端日志
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTerminalLogCommand extends BaseCommand<HttpCommandResultWithData> {
    private String chassisNo;//底盘号
    private String simNo;//SIM卡
    private String terminalId;//终端ID
    private String operateUser;//操作人
    private String commandDir;//指令方向
    private String commandType;//指令类型
    private String commandStatus;//状态
    private String beginTime;//指令时间-开始时间
    private String endTime;//指令时间-结束时间

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getCommandDir() {
        return commandDir;
    }

    public void setCommandDir(String commandDir) {
        this.commandDir = commandDir;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
    }

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

    @Override
    public String toString() {
        return "QueryTerminalLogCommand{" +
                "chassisNo='" + chassisNo + '\'' +
                ", simNo='" + simNo + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", operateUser='" + operateUser + '\'' +
                ", commandDir='" + commandDir + '\'' +
                ", commandType='" + commandType + '\'' +
                ", commandStatus='" + commandStatus + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", " + super.toString() +
                '}';
    }
}