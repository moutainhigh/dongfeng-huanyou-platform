package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRuleCommand;
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
public class AddRiskRuleHandler extends ValidateTokenAndReSetAbstracHandler<AddRiskRuleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(AddRiskRuleHandler.class);

    public AddRiskRuleHandler() {
        super(AddRiskRuleCommand.class, HttpCommandResultWithData.class);
    }

    protected AddRiskRuleHandler(Class<AddRiskRuleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRuleService riskRuleService;

    @Override
    protected HttpCommandResultWithData businessHandle(AddRiskRuleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = riskRuleService.addRiskRule(command);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.fillResult(ReturnCode.ADD_RISK_REGION_FAILED);
        }
        return result;
    }
}