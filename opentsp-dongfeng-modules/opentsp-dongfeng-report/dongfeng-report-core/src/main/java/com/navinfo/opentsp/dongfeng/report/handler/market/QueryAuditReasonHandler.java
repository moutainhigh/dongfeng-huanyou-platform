package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryAuditReasonCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.IVehicleAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/29
 * @modify
 * @copyright
 */
@Component
public class QueryAuditReasonHandler extends ValidateTokenAndReSetAbstracHandler<QueryAuditReasonCommand, HttpCommandResultWithData> {

    @Autowired
    private IVehicleAuditService service;

    public QueryAuditReasonHandler() {
        super(QueryAuditReasonCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryAuditReasonHandler(Class<QueryAuditReasonCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAuditReasonCommand command) {
        return service.queryApplyReason(command);
    }
}
