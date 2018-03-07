package com.navinfo.opentsp.dongfeng.report.handler.salestate;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.salestate.QuerySaleStateCommand;
import com.navinfo.opentsp.dongfeng.report.service.salestate.IQuerySaleStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QuerySaleStateHandler extends ValidateTokenAndReSetAbstracHandler<QuerySaleStateCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QuerySaleStateHandler.class);
    @Autowired
    IQuerySaleStateService iQuerySaleStateService;

    public QuerySaleStateHandler() {
        super(QuerySaleStateCommand.class, HttpCommandResultWithData.class);
    }

    protected QuerySaleStateHandler(Class<QuerySaleStateCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QuerySaleStateCommand command) {
        HttpCommandResultWithData result = iQuerySaleStateService.querySaleState(command);
        return result;
    }
}