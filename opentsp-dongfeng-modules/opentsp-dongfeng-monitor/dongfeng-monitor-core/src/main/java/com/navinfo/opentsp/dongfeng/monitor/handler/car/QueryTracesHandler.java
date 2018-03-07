package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryTracesCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryTracesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 轨迹回放:轨迹点handler
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryTracesHandler extends ValidateTokenAndReSetAbstracHandler<QueryTracesCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryTracesService iQueryTraceService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryTracesHandler.class);

    public QueryTracesHandler() {
        super(QueryTracesCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTracesHandler(Class<QueryTracesCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTracesCommand command) {
        HttpCommandResultWithData result = iQueryTraceService.queryTrace(command);
        return result;
    }
}