package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 车辆基本信息
 *
 * @wenya
 * @create 2017-03-13 17:44
 **/
public class QueryCarInfoCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "车辆id不能为空", groups = {GroupCommand.class})
    private Long carId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryCarInfoCommand{" +
                "carId=" + carId +
                '}';
    }
}
