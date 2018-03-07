package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.QueryCanBusDataCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-06
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryCanBusDataHandler extends ValidateTokenAndReSetAbstracHandler<QueryCanBusDataCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryCanBusDataHandler.class);

    public QueryCanBusDataHandler() {
        super(QueryCanBusDataCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryCanBusDataHandler(Class<QueryCanBusDataCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCanBusDataCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.queryCanBusData(command.getPid());
        } catch (Exception e) {
            result.fillResult(ReturnCode.QUERY_CAN_BUS_FAILED);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}