package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 运营车况查看
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
public class QueryCarOperateCommand  extends BaseReportCommand {
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //整车公里数
    private Integer mileage;
    //发动机类型
    private Integer engineType;


    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getEngineType() {
        return engineType;
    }

    public void setEngineType(Integer engineType) {
        this.engineType = engineType;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
    @Override
    public String toString() {
        return "QueryCarOperateCommand{}";
    }
}
