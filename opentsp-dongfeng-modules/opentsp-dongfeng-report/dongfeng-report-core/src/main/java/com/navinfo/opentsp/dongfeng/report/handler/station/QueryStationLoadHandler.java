package com.navinfo.opentsp.dongfeng.report.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationLoadCommand;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 查询服务站负荷分析
 * @author wt
 * @date 2017-03-27
 * @modify
 * @version 1.0
 */

@Component
public class QueryStationLoadHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationLoadCommand, HttpCommandResultWithData> {

    @Autowired
    private IStationLoadService service;

    public QueryStationLoadHandler() {
        super(QueryStationLoadCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationLoadHandler(Class<QueryStationLoadCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationLoadCommand command) {
        return service.queryStationLoadInfos(command, false);
    }
}
