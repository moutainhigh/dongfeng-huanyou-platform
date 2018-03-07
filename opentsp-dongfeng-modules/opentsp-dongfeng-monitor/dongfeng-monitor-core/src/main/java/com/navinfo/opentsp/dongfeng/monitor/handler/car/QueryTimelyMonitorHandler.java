package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndNoReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryTimelyMonitorCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryTimelyMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryTimelyMonitorHandler extends ValidateTokenAndNoReSetAbstracHandler<QueryTimelyMonitorCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryTimelyMonitorHandler.class);

    @Autowired
    private IQueryTimelyMonitorService iQueryTimelyMonitorService;

    public QueryTimelyMonitorHandler() {
        super(QueryTimelyMonitorCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTimelyMonitorHandler(Class<QueryTimelyMonitorCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTimelyMonitorCommand command) {
        return iQueryTimelyMonitorService.queryTimelyMonitor(command);
    }
}