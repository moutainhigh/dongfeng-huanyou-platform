package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.QueryTerminalCompleteInfoCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-16
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryTerminalCompleteInfoHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalCompleteInfoCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryTerminalCompleteInfoHandler.class);

    public QueryTerminalCompleteInfoHandler() {
        super(QueryTerminalCompleteInfoCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTerminalCompleteInfoHandler(Class<QueryTerminalCompleteInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTerminalCompleteInfoCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.queryCompleteInfo(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_TERMINAL_FAILED);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}