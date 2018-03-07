package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryHistoryVehicleCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IVehicleCountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/29
 * @modify
 * @copyright
 */
@Component
public class QueryHistoryVehicleCountsHandler extends ValidateUsingUserDefinedMethodAbstractHandler<QueryHistoryVehicleCountsCommand, HttpCommandResultWithData> {

    @Autowired
    private IVehicleCountsService service;

    public QueryHistoryVehicleCountsHandler() {
        super(QueryHistoryVehicleCountsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryHistoryVehicleCountsHandler(Class<QueryHistoryVehicleCountsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }


    @Override
    protected HttpCommandResultWithData openApiHandle(QueryHistoryVehicleCountsCommand command) {
        return service.queryHistoryCounts(command);
    }
}
