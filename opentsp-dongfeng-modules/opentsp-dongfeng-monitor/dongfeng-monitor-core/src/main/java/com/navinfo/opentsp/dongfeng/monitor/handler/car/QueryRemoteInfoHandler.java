package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryRemoteInfoCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryRemoteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 远程诊断
 *
 * @wenya
 * @create 2017-03-13 11:43
 **/
@Component
public class QueryRemoteInfoHandler extends ValidateTokenAndReSetAbstracHandler<QueryRemoteInfoCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryRemoteInfoService queryRemoteInfoService;

    public QueryRemoteInfoHandler() {
        super(QueryRemoteInfoCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRemoteInfoHandler(Class<QueryRemoteInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRemoteInfoCommand command) {
        HttpCommandResultWithData result = queryRemoteInfoService.queryRemoteInfo(command);
        return result;
    }
}
