package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.IScanCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Vehicle Scan Code Query Handler
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
@Component
public class QueryScanCodeHandler extends ValidateTokenAndReSetAbstracHandler<QueryScanCodeCommand, HttpCommandResultWithData> {

    @Autowired
    private IScanCodeService service;

    public QueryScanCodeHandler() {
        super(QueryScanCodeCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryScanCodeHandler(Class<QueryScanCodeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryScanCodeCommand command) {
        return service.queryVehicleScanCodeInfos(command, false);
    }
}
