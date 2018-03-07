package com.navinfo.opentsp.dongfeng.monitor.handler.log;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryTerminalLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.log.IQueryTerminalLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.QUERY_TERMINAL_LOG_FAILED;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryTerminalLogHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalLogCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryTerminalLogHandler.class);

    public QueryTerminalLogHandler() {
        super(QueryTerminalLogCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTerminalLogHandler(Class<QueryTerminalLogCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IQueryTerminalLogService queryTerminalLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTerminalLogCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = queryTerminalLogService.queryLogOfTerminal(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            logger.error(result.getMessage() , e);
            result.fillResult(QUERY_TERMINAL_LOG_FAILED);
        }
        return result;
    }
}