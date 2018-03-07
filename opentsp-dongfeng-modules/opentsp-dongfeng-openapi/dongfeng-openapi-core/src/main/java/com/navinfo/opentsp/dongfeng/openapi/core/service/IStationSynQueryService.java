package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.station.StationSynQueryCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

public interface IStationSynQueryService {
    /**
     *  服务站同步查询
     * @param command
     * @return
     */
    public HttpCommandResultWithData stationSynQuery(StationSynQueryCommand command);
}
