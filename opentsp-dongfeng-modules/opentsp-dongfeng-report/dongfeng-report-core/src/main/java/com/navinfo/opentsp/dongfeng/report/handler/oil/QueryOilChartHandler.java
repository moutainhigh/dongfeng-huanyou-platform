package com.navinfo.opentsp.dongfeng.report.handler.oil;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilChartCommand;
import com.navinfo.opentsp.dongfeng.report.service.oil.IOilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.QUERY_STATISTIC_OIL_CHART_ERROR;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryOilChartHandler extends ValidateTokenAndReSetAbstracHandler<QueryOilChartCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryOilChartHandler.class);

    public QueryOilChartHandler() {
        super(QueryOilChartCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryOilChartHandler(Class<QueryOilChartCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IOilService oilService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryOilChartCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = oilService.oilChart(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(QUERY_STATISTIC_OIL_CHART_ERROR);
        }
        return result;
    }
}