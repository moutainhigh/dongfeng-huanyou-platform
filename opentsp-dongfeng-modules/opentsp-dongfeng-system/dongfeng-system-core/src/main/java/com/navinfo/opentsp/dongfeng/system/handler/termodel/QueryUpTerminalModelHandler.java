package com.navinfo.opentsp.dongfeng.system.handler.termodel;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.QueryUpTerminalModelCommand;
import com.navinfo.opentsp.dongfeng.system.service.TerminalModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangyue
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryUpTerminalModelHandler extends ValidateTokenAndReSetAbstracHandler<QueryUpTerminalModelCommand, HttpCommandResultWithData> {

    public QueryUpTerminalModelHandler() {
        super(QueryUpTerminalModelCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryUpTerminalModelHandler(Class<QueryUpTerminalModelCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private TerminalModeService terminaModelImpl;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryUpTerminalModelCommand command) {

        HttpCommandResultWithData result = terminaModelImpl.queryUpdateData(command);
        return result;
    }
}
