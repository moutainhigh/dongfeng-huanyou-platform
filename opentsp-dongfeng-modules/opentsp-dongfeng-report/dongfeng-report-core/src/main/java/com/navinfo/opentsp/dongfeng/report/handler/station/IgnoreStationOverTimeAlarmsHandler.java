package com.navinfo.opentsp.dongfeng.report.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.station.IgnoreStationOverTimeAlarmsCommand;
import com.navinfo.opentsp.dongfeng.report.service.station.IStationOverTimeAlarmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务站超时报警忽略操作
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-19
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class IgnoreStationOverTimeAlarmsHandler extends ValidateTokenAndReSetAbstracHandler<IgnoreStationOverTimeAlarmsCommand, HttpCommandResultWithData> {

    @Autowired
    private IStationOverTimeAlarmsService service;

    public IgnoreStationOverTimeAlarmsHandler() {
        super(IgnoreStationOverTimeAlarmsCommand.class, HttpCommandResultWithData.class);
    }

    protected IgnoreStationOverTimeAlarmsHandler(Class<IgnoreStationOverTimeAlarmsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(IgnoreStationOverTimeAlarmsCommand command) {
        return service.ignoreStationOverTimeAlarms(command);
    }
}
