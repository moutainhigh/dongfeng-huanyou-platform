package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.dealer.DealerPositionReportCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

public interface IDealerPositionReportService {

    /**
     * 经销商位置上报
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData dealerPositionReport(DealerPositionReportCommand command) throws Exception;
}