package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleDetailsCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryRearInstallVehicleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 后装车辆详情信息
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryRearInstallVehicleDetailsHandler extends ValidateTokenAndReSetAbstracHandler<QueryRearInstallVehicleDetailsCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryRearInstallVehicleInfoService service;

    public QueryRearInstallVehicleDetailsHandler() {
        super(QueryRearInstallVehicleDetailsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryRearInstallVehicleDetailsHandler(Class<QueryRearInstallVehicleDetailsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRearInstallVehicleDetailsCommand command) {
        return service.queryVehicleDetails(command, false);
    }
}
