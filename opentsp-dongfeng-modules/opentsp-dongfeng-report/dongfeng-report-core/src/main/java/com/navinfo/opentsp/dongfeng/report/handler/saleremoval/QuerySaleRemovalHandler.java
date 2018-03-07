package com.navinfo.opentsp.dongfeng.report.handler.saleremoval;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.saleremoval.QuerySaleRemovalCommand;
import com.navinfo.opentsp.dongfeng.report.service.saleremoval.IQuerySaleRemovalService;
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
public class QuerySaleRemovalHandler extends ValidateTokenAndReSetAbstracHandler<QuerySaleRemovalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QuerySaleRemovalHandler.class);
    @Autowired
    IQuerySaleRemovalService iQuerySaleRemovalService;

    public QuerySaleRemovalHandler() {
        super(QuerySaleRemovalCommand.class, HttpCommandResultWithData.class);
    }

    protected QuerySaleRemovalHandler(Class<QuerySaleRemovalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QuerySaleRemovalCommand command) {
        HttpCommandResultWithData result = iQuerySaleRemovalService.querySaleRemoval(command);
        return result;
    }
}