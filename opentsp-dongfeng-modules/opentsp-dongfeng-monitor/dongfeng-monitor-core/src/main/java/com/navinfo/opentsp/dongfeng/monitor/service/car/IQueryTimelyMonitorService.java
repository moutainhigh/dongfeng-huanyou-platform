package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryTimelyMonitorCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryTimelyMonitorService {
    /**
     * 实时监控（实时在途）
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryTimelyMonitor(QueryTimelyMonitorCommand command);
}