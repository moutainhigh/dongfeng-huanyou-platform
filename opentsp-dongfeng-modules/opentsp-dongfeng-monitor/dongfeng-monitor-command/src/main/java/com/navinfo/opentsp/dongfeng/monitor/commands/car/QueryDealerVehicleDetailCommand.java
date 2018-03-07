package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 销售车辆详情
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
public class QueryDealerVehicleDetailCommand extends BaseCommand<HttpCommandResultWithData> {

    private String dealerId;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryDealerVehicleDetailCommand{" +
                "dealerId='" + dealerId + '\'' +
                '}';
    }
}