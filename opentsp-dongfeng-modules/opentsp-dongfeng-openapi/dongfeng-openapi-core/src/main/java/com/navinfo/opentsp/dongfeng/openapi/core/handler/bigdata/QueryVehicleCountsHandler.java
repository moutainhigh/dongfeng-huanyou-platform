package com.navinfo.opentsp.dongfeng.openapi.core.handler.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryVehicleCountsCommand;
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
public class QueryVehicleCountsHandler extends ValidateUsingUserDefinedMethodAbstractHandler<QueryVehicleCountsCommand, HttpCommandResultWithData> {

    @Autowired
    private IVehicleCountsService service;

    public QueryVehicleCountsHandler() {
        super(QueryVehicleCountsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryVehicleCountsHandler(Class<QueryVehicleCountsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData openApiHandle(QueryVehicleCountsCommand command) {
        return service.queryCounts(command);
    }
}

