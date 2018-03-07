package com.navinfo.opentsp.dongfeng.system.handler.termodel;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.QueryTerminalModelCommand;
import com.navinfo.opentsp.dongfeng.system.service.TerminalModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangyue
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryTerminalModelHandler extends ValidateTokenAndReSetAbstracHandler<QueryTerminalModelCommand, HttpCommandResultWithData> {

    public QueryTerminalModelHandler() {
        super(QueryTerminalModelCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryTerminalModelHandler(Class<QueryTerminalModelCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private TerminalModeService terminaModelImpl;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryTerminalModelCommand command) {

        HttpCommandResultWithData result = terminaModelImpl.queryTerminaModel(command);
        return result;
    }

}
