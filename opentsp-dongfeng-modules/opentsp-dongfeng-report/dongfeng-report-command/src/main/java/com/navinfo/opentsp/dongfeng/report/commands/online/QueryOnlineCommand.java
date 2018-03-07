package com.navinfo.opentsp.dongfeng.report.commands.online;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
public class QueryOnlineCommand extends BaseReportCommand {
    private String chassisNum;//底盘号
    private String carNo;//车牌号
    private String teamNme;//经销商
    private String carOwnerName;//客户
    private String terminalId;//北斗一体机ID
    private String fkID;//FK控制器ID
    private String carType;//车辆型号
    private String engineType;//发动机类型
    private String syncDateStr;//同步时间范围
    private String firstNettingDateStr;//首次上线时间
    private String produceDateStr;//生产下线时间
    private Integer locationStatus;//定位状态
    private String saleStatus;//销售状态

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

    public String getSyncDateStr() {
        return syncDateStr;
    }

    public void setSyncDateStr(String syncDateStr) {
        this.syncDateStr = syncDateStr;
    }

    public String getFirstNettingDateStr() {
        return firstNettingDateStr;
    }

    public void setFirstNettingDateStr(String firstNettingDateStr) {
        this.firstNettingDateStr = firstNettingDateStr;
    }

    public String getProduceDateStr() {
        return produceDateStr;
    }

    public void setProduceDateStr(String produceDateStr) {
        this.produceDateStr = produceDateStr;
    }

    public Integer getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(Integer locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}