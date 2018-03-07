package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskRegionCommand;
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
public class QueryRiskRegionHandler extends ValidateTokenAndReSetAbstracHandler<QueryRiskRegionCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryRiskRegionHandler.class);

    public QueryRiskRegionHandler() {
        super(QueryRiskRegionCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRiskRegionHandler(Class<QueryRiskRegionCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IRiskRegionService regionService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRiskRegionCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = regionService.queryRiskRegion(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.QUERY_RISK_REGION_FAILED);
        }
        return result;
    }
}