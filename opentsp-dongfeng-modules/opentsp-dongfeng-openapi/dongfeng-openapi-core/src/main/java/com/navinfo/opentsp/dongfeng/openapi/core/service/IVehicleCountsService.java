package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryHistoryVehicleCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryVehicleCountsCommand;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
public interface IVehicleCountsService {

    HttpCommandResultWithData queryCounts(QueryVehicleCountsCommand command);

    HttpCommandResultWithData queryHistoryCounts(QueryHistoryVehicleCountsCommand command);
}
