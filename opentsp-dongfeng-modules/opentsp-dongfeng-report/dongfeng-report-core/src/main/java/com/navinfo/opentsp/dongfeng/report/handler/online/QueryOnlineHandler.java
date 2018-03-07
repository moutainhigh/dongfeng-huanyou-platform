package com.navinfo.opentsp.dongfeng.report.handler.online;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.online.QueryOnlineCommand;
import com.navinfo.opentsp.dongfeng.report.service.online.IOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.QUERY_STATISTIC_ONLINE_ERROR;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryOnlineHandler extends ValidateTokenAndReSetAbstracHandler<QueryOnlineCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryOnlineHandler.class);

    public QueryOnlineHandler() {
        super(QueryOnlineCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryOnlineHandler(Class<QueryOnlineCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IOnlineService onlineService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryOnlineCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = onlineService.queryOnline(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.fillResult(QUERY_STATISTIC_ONLINE_ERROR);
        }
        return result;
    }
}