package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerVehicleDetailCommand;

/**
 * 查询销售车辆详情
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
public interface IQueryDealerVehicleDetailService {

    HttpCommandResultWithData query(final QueryDealerVehicleDetailCommand command);
}
