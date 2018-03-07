package com.navinfo.opentsp.dongfeng.monitor.handler.log;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryOperateLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.log.IQueryOperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.QUERY_OPERATE_LOG_FAILED;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryOperateLogHandler extends ValidateTokenAndReSetAbstracHandler<QueryOperateLogCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryOperateLogHandler.class);

    public QueryOperateLogHandler() {
        super(QueryOperateLogCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryOperateLogHandler(Class<QueryOperateLogCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IQueryOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryOperateLogCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = operateLogService.query(command);
        } catch (Exception e) {
            logger.error(result.getMessage() , e);
            result.fillResult(QUERY_OPERATE_LOG_FAILED);
        }
        return result;
    }
}