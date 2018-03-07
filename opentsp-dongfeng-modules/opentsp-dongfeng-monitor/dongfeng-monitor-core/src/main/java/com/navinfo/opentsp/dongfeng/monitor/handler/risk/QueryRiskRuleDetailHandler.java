package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskRuleDetailCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryRiskRuleDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryRiskRuleDetailCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryRiskRuleDetailHandler.class);

    public QueryRiskRuleDetailHandler() {
        super(QueryRiskRuleDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRiskRuleDetailHandler(Class<QueryRiskRuleDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRuleService riskRuleService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRiskRuleDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = riskRuleService.queryRiskRuleDetail(command);
        } catch (Exception e) {
            logger.error("delete risk rule failed," , e);
            result.fillResult(ReturnCode.QUERY_RISK_RULE_FAILED);
        }
        return result;
    }
}