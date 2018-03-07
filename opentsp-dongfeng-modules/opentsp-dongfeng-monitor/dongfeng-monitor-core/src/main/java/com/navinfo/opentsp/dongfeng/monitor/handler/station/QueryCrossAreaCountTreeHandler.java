package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryCrossAreaCountTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryCrossAreaCountTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-16
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryCrossAreaCountTreeHandler extends ValidateTokenAndReSetAbstracHandler<QueryCrossAreaCountTreeCommand, HttpCommandResultWithData> {

    @Autowired
    IQueryCrossAreaCountTreeService iQueryCrossAreaCountTreeService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryCrossAreaCountTreeHandler.class);

    public QueryCrossAreaCountTreeHandler() {
        super(QueryCrossAreaCountTreeCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryCrossAreaCountTreeHandler(Class<QueryCrossAreaCountTreeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCrossAreaCountTreeCommand command) {
        HttpCommandResultWithData result = iQueryCrossAreaCountTreeService.queryCrossAreaCountTree(command);
        return result;
    }
}