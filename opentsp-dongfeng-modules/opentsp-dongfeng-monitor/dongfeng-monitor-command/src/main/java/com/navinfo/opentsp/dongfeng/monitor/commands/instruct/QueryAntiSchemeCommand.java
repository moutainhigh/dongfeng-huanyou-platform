package com.navinfo.opentsp.dongfeng.monitor.commands.instruct;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 获取防拆方案下拉菜单
 *
 * @wenya
 * @create 2017-03-23 15:13
 **/
public class QueryAntiSchemeCommand extends BaseCommand<HttpCommandResultWithData> {
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
        return "QueryAntiSchemeCommand{" +
                "carId=" + carId +
                '}';
    }
}
