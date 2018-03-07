package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskRuleCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryRiskRuleHandler extends ValidateTokenAndReSetAbstracHandler<QueryRiskRuleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryRiskRuleHandler.class);

    public QueryRiskRuleHandler() {
        super(QueryRiskRuleCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRiskRuleHandler(Class<QueryRiskRuleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRuleService riskRuleService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = riskRuleService.queryRiskRule(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_RISK_RULE_FAILED);
            logger.error("list risk rule failed" , e);
        }
        return result;
    }
}