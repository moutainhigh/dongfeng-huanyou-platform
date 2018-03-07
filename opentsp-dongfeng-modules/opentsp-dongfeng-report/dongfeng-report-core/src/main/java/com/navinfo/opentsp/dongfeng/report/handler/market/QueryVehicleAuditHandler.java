package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryVehicleAuditCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.IVehicleAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
@Component
public class QueryVehicleAuditHandler extends ValidateTokenAndReSetAbstracHandler<QueryVehicleAuditCommand, HttpCommandResultWithData> {

    @Autowired
    private IVehicleAuditService service;

    public QueryVehicleAuditHandler() {
        super(QueryVehicleAuditCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryVehicleAuditHandler(Class<QueryVehicleAuditCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryVehicleAuditCommand command) {
        return service.query(command, false);
    }
}
