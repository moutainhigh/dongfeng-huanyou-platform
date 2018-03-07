package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationDetailCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryStationDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationDetailCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryStationDetailService iQueryStationDetailService;
    protected static final Logger logger = LoggerFactory.getLogger(QueryStationDetailHandler.class);

    public QueryStationDetailHandler() {
        super(QueryStationDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationDetailHandler(Class<QueryStationDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationDetailCommand command) {
        HttpCommandResultWithData result = iQueryStationDetailService.queryStationDetail(command);
        return result;
    }
}