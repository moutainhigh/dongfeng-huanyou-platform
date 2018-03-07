package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;


/**
 * Created by zhangtiatnong on 2018/2/28/028.
 */
public class BadDrivingAnalysisCommand extends BaseReportCommand {

    /**
     * 底盘号
     */
    private String chassisNum;

    /**
     * 终端Id
     */
    private String terId;

    /**
     * 防拆盒Id
     */
    private String carTerId;

    /**
     * 车牌号
     */
    private String plateNum;

    /**
     * 经销商
     */
    private String companyName;

    /**
     * 客户
     */
    private String carOwner;

    /**
     * 车型
     */
    private String carType;

    /**
     * 发动机类型
     */
    private String engineType;

    /**
     * 异常时间
     */
    @NotNull(message = "异常时间 不能为空", groups = {GroupCommand.class})
    private String dateStr;

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getTerId() {
        return terId;
    }

    public void setTerId(String terId) {
        this.terId = terId;
    }

    public String getCarTerId() {
        return carTerId;
    }

    public void setCarTerId(String carTerId) {
        this.carTerId = carTerId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
    public String toString() {
        return "BadDrivingAnalysisCommand{" +
                "chassisNum='" + chassisNum + '\'' +
                ", terId='" + terId + '\'' +
                ", carTerId='" + carTerId + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", companyName='" + companyName + '\'' +
                ", carOwner='" + carOwner + '\'' +
                ", carType='" + carType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }
}
