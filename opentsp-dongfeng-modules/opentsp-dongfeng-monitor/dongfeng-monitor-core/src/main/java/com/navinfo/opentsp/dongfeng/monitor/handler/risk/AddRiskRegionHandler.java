package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRegionService;
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
public class AddRiskRegionHandler extends ValidateTokenAndReSetAbstracHandler<AddRiskRegionCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(AddRiskRegionHandler.class);

    public AddRiskRegionHandler() {
        super(AddRiskRegionCommand.class, HttpCommandResultWithData.class);
    }

    protected AddRiskRegionHandler(Class<AddRiskRegionCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRegionService regionService;

    @Override
    protected HttpCommandResultWithData businessHandle(AddRiskRegionCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = regionService.addRiskRegion(command);
        } catch (Exception e) {
            logger.error("add risk region failed," , e);
            result.fillResult(ReturnCode.ADD_RISK_REGION_FAILED);
        }
        return result;
    }
}