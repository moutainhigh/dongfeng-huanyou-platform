package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.QueryTerminalSettingParamCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询终端设置目录
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-02
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryTerminalSettingParamHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalSettingParamCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryTerminalSettingParamHandler.class);

    public QueryTerminalSettingParamHandler() {
        super(QueryTerminalSettingParamCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTerminalSettingParamHandler(Class<QueryTerminalSettingParamCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTerminalSettingParamCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.queryTerminalSettingParam(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_TERMINAL_SETTING_PARAM_FAILED);
            logger.error(result.getMessage() , e);
        }
        return result;
    }
}