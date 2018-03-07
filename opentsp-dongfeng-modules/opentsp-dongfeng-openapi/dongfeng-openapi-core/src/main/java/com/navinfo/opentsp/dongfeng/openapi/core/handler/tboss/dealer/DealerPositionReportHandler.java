package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.dealer;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.openapi.commands.dealer.DealerPositionReportCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IDealerPositionReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class DealerPositionReportHandler extends ValidateUsingUserDefinedMethodAbstractHandler<DealerPositionReportCommand, HttpCommandResultWithData> {
    @Autowired
    IDealerPositionReportService iDealerPositionReportService;

    protected static final Logger logger = LoggerFactory.getLogger(DealerPositionReportHandler.class);

    public DealerPositionReportHandler() {
        super(DealerPositionReportCommand.class, HttpCommandResultWithData.class);
    }

    protected DealerPositionReportHandler(Class<DealerPositionReportCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData openApiHandle(DealerPositionReportCommand command) {
        HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
        try {
            resultMap = iDealerPositionReportService.dealerPositionReport(command);
        } catch (Exception e) {
            logger.error("#DealerPositionReportHandler#businessHandle#...", e);
            resultMap.fillResult(ReturnCode.SERVER_ERROR);
            resultMap.setMessage("上报异常");
        }
        return resultMap;
    }
}