package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryConsumptionCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 轨迹回放:时间段内总里程、时间段内总油耗 handler
 * @author fengwm
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryConsumptionHandler extends ValidateTokenAndReSetAbstracHandler<QueryConsumptionCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryConsumptionService iQueryConsumptionService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryConsumptionHandler.class);

    public QueryConsumptionHandler() {
        super(QueryConsumptionCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryConsumptionHandler(Class<QueryConsumptionCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryConsumptionCommand command) {
        HttpCommandResultWithData result = iQueryConsumptionService.queryConsumption(command);
        return result;
    }
}