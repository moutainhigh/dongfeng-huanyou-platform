package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.RiskRegionReportCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IRiskRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class RiskRegionReportHandler extends ValidateTokenAndReSetAbstracHandler<RiskRegionReportCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(RiskRegionReportHandler.class);

    public RiskRegionReportHandler() {
        super(RiskRegionReportCommand.class, HttpCommandResultWithData.class);
    }

    protected RiskRegionReportHandler(Class<RiskRegionReportCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRegionService regionService;

    @Override
    protected HttpCommandResultWithData businessHandle(RiskRegionReportCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = regionService.queryReportOfRiskRegion(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.QUERY_REPORT_OF_RISK_REGION_FAILED);
        }
        return result;
    }
}