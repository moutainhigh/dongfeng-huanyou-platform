package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.DeleteRiskRuleCommand;
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
public class DeleteRiskRuleHandler extends ValidateTokenAndReSetAbstracHandler<DeleteRiskRuleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(DeleteRiskRuleHandler.class);

    public DeleteRiskRuleHandler() {
        super(DeleteRiskRuleCommand.class, HttpCommandResultWithData.class);
    }

    protected DeleteRiskRuleHandler(Class<DeleteRiskRuleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRuleService riskRuleService;

    @Override
    protected HttpCommandResultWithData businessHandle(DeleteRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = riskRuleService.deleteRiskRule(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.DELETE_RISK_RULE_FAILED);
        }
        return result;
    }
}