package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationCommentsCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationCommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryStationCommentsHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationCommentsCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryStationCommentsService iQueryStationCommentsService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryStationCommentsHandler.class);

    public QueryStationCommentsHandler() {
        super(QueryStationCommentsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationCommentsHandler(Class<QueryStationCommentsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationCommentsCommand command) {

        HttpCommandResultWithData result = iQueryStationCommentsService.queryStationComments(command);
        return result;
    }
}