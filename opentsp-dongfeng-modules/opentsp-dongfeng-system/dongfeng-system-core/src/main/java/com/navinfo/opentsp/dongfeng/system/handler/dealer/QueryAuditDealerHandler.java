package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.QueryAuditDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryAuditDealerHandler extends ValidateTokenAndReSetAbstracHandler<QueryAuditDealerCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryAuditDealerHandler.class);
    @Resource
    private DealerService dealerService;

    public QueryAuditDealerHandler() {
        super(QueryAuditDealerCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryAuditDealerHandler(Class<QueryAuditDealerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAuditDealerCommand command) {
        return dealerService.queryAuditDealer(command);
    }
}