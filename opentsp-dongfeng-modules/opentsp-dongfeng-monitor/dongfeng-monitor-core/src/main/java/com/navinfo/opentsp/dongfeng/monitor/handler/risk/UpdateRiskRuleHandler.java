package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.UpdateRiskRuleCommand;
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
public class UpdateRiskRuleHandler extends ValidateTokenAndReSetAbstracHandler<UpdateRiskRuleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateRiskRuleHandler.class);

    public UpdateRiskRuleHandler() {
        super(UpdateRiskRuleCommand.class, HttpCommandResultWithData.class);
    }

    protected UpdateRiskRuleHandler(Class<UpdateRiskRuleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRuleService riskRuleService;

    @Override
    protected HttpCommandResultWithData businessHandle(UpdateRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = riskRuleService.updateRiskRule(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.UPDATE_RISK_RULE_FAILED);
        }
        return result;
    }
}