package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.QuerySDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QuerySDealerHandler extends ValidateTokenAndReSetAbstracHandler<QuerySDealerCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QuerySDealerHandler.class);

    @Resource
    private DealerService dealerService;

    public QuerySDealerHandler() {
        super(QuerySDealerCommand.class, HttpCommandResultWithData.class);
    }

    protected QuerySDealerHandler(Class<QuerySDealerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QuerySDealerCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result = dealerService.queryDealerPage(command,false);
        return result;
    }
}