package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationDetailCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryStationDetailService {
    /**
     * 服务站详情
     * @param queryStationDetailCommand
     * @return
     */
    public HttpCommandResultWithData queryStationDetail(QueryStationDetailCommand queryStationDetailCommand);
}
