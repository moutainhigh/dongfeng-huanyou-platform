package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndNoReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAlarmRemindsCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryAlarmRemindsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 报警提醒handler
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryAlarmRemindsHandler extends ValidateTokenAndNoReSetAbstracHandler<QueryAlarmRemindsCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryAlarmRemindsService iQueryAlarmRemindService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryAlarmRemindsHandler.class);

    public QueryAlarmRemindsHandler() {
        super(QueryAlarmRemindsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryAlarmRemindsHandler(Class<QueryAlarmRemindsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAlarmRemindsCommand command) {
        HttpCommandResultWithData result = iQueryAlarmRemindService.queryAlarmRemind(command);
        return result;
    }
}