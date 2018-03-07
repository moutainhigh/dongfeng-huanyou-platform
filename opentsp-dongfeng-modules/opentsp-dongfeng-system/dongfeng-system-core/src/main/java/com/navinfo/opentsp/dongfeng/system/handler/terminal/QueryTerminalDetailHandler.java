package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.QueryTerminalDetailCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-06
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryTerminalDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalDetailCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryTerminalDetailHandler.class);

    public QueryTerminalDetailHandler() {
        super(QueryTerminalDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTerminalDetailHandler(Class<QueryTerminalDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTerminalDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.detailQuery(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_TERMINAL_FAILED);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}