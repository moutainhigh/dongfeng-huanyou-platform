package com.navinfo.opentsp.dongfeng.report.handler.oil;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryTireCommand;
import com.navinfo.opentsp.dongfeng.report.service.oil.IOilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.QUERY_STATISTIC_OIL_ERROR;
@Component
public class QueryTireInfoHandler extends ValidateTokenAndReSetAbstracHandler<QueryTireCommand, HttpCommandResultWithData> {
    protected static final Logger logger = LoggerFactory.getLogger(QueryOilHandler.class);

    public QueryTireInfoHandler(){
        super (QueryTireCommand.class, HttpCommandResultWithData.class);
    }
    protected QueryTireInfoHandler(Class<QueryTireCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IOilService oilService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTireCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = oilService.queryTireInfo(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(QUERY_STATISTIC_OIL_ERROR);
        }
        return result;
    }
}
