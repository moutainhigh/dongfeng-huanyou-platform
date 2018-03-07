package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationCommentsCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryStationCommentsService {
    /**
     * 服务站详情
     * @param stationCommentsCommand
     * @return
     */
    public HttpCommandResultWithData queryStationComments(QueryStationCommentsCommand stationCommentsCommand);

}
