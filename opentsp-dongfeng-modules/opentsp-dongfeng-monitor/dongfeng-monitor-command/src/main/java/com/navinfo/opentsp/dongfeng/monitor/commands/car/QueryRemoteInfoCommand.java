package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 远程诊断
 *
 * @wenya
 * @create 2017-03-13 11:47
 **/
public class QueryRemoteInfoCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "车辆id不能为空", groups = {GroupCommand.class})
    private String carId;     //车辆id
    private String dateTime; // 查询时间
    private String index;  //当前序列号  用于上一条，下一条

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "QueryRemoteInfoCommand{" +
                "carId='" + carId + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", index='" + index + '\'' +
                '}';
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
