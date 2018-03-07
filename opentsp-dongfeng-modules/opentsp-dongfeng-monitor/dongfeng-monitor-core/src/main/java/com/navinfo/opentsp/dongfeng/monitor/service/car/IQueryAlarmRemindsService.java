package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAlarmRemindsCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryAlarmRemindsService {
    /**
     * 报警提醒查询
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryAlarmRemind(QueryAlarmRemindsCommand command);
}
