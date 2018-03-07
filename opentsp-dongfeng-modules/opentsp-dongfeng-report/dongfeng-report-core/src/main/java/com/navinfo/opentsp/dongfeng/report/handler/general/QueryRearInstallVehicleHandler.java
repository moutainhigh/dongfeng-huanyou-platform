package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryRearInstallVehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryRearInstallVehicleHandler extends ValidateTokenAndReSetAbstracHandler<QueryRearInstallVehicleCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryRearInstallVehicleInfoService service;

    public QueryRearInstallVehicleHandler() {
        super(QueryRearInstallVehicleCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRearInstallVehicleHandler(Class<QueryRearInstallVehicleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRearInstallVehicleCommand command) {
        return service.queryVehicleCounts(command);
    }
}
