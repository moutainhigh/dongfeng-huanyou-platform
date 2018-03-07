package com.navinfo.opentsp.dongfeng.monitor.commands.instruct;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 锁车方案下拉菜单
 *
 * @wenya
 * @create 2017-03-23 16:20
 **/
public class QueryLockMenuCommand extends BaseCommand<HttpCommandResultWithData> {
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
        return "QueryLockMenuCommand{" +
                "carId=" + carId +
                '}';
    }
}
