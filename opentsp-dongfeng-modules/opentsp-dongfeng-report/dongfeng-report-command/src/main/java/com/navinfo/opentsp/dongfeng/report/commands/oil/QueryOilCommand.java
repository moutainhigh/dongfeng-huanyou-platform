package com.navinfo.opentsp.dongfeng.report.commands.oil;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 油耗变化统计
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-30
 * @modify
 * @copyright Navi Tsp
 */
public class QueryOilCommand extends BaseReportCommand {
    private String chassisNum;//底盘号
    private String carNo;//车牌号
    private String teamNme;//经销商
    private String carOwnerName;//客户
    private String terminalId;//北斗一体机ID
    private String fkID;//FK控制器ID
    private String carType;//车辆型号
    private String engineType;//发动机类型
    @NotNull(message = "查询时间不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "查询时间不能为空", groups = {GroupCommand.class})
    private String dateStr;//时间

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getTeamNme() {
        return teamNme;
    }

    public void setTeamNme(String teamNme) {
        this.teamNme = teamNme;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getFkID() {
        return fkID;
    }

    public void setFkID(String fkID) {
        this.fkID = fkID;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override

    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}