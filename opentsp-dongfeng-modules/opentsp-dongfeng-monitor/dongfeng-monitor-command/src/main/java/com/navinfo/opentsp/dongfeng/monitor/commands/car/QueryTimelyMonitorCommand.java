package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTimelyMonitorCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "车辆id不能为空", groups = {GroupCommand.class})
    private Long carId;//车辆id

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
        return "QueryTimelyMonitorCommand{" +
                "carId=" + carId +
                '}';
    }
}