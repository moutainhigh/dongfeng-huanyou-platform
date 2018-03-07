package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 轨迹回放:时间段内总里程、时间段内总油耗command
 * @author fengwm
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
public class QueryConsumptionCommand extends BaseCommand<HttpCommandResultWithData> {
    //轨迹查询开始时间点
    @NotNull(message = "开始时间 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "开始时间 不能为空", groups = {GroupCommand.class})
    private String beginDate;
    //轨迹查询结束时间点
    @NotNull(message = "结束时间 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "结束时间 不能为空", groups = {GroupCommand.class})
    private String endDate;
    //车辆ID
    @NotNull(message = "车辆ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "车辆ID 不能为空", groups = {GroupCommand.class})
    private String carId;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
    @Override
    public String toString() {
        return "QueryConsumptionCommand{" +
                "beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }
}