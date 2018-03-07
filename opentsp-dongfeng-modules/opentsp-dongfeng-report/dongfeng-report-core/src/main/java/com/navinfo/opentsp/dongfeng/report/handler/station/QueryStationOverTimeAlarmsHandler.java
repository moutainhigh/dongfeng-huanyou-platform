package com.navinfo.opentsp.dongfeng.report.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationOverTimeAlarmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询服务站超时报警信息
 * @author wt
 * @date 2017-03-28
 * @modify
 * @version 1.0
 */
@Component
public class QueryStationOverTimeAlarmsHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationOverTimeAlarmsCommand, HttpCommandResultWithData> {

    @Autowired
    private IStationOverTimeAlarmsService service;

    public QueryStationOverTimeAlarmsHandler() {
        super(QueryStationOverTimeAlarmsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationOverTimeAlarmsHandler(Class<QueryStationOverTimeAlarmsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationOverTimeAlarmsCommand command) {
        return service.queryStationOverTimeAlarms(command, false);
    }
}
