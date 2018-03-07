package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.TerminalProtocolCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 终端协议handler
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class TerminalProtocolHandler extends ValidateTokenAndReSetAbstracHandler<TerminalProtocolCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(TerminalProtocolHandler.class);

    public TerminalProtocolHandler() {
        super(TerminalProtocolCommand.class, HttpCommandResultWithData.class);
    }

    protected TerminalProtocolHandler(Class<TerminalProtocolCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;

    @Override
    protected HttpCommandResultWithData businessHandle(TerminalProtocolCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.queryProtocolOfTerminal(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_TERMINAL_PROTOCOL_FAILED);
            logger.error("===== query Terminal Protocol Exception =====" , e);
        }
        return result;
    }
}