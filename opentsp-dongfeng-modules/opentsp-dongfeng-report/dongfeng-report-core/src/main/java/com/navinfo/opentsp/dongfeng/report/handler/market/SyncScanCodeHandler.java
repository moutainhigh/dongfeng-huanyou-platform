package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.SyncScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.ISyncScanCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wt
 * @version 1.0
 * @date 2017/9/6
 * @modify
 * @copyright
 */
@Component
public class SyncScanCodeHandler extends ValidateTokenAndReSetAbstracHandler<SyncScanCodeCommand, HttpCommandResultWithData> {

    @Autowired
    private ISyncScanCodeService service;

    public SyncScanCodeHandler() {
        super(SyncScanCodeCommand.class, HttpCommandResultWithData.class);
    }

    protected SyncScanCodeHandler(Class<SyncScanCodeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(SyncScanCodeCommand command) {
        return service.sync2Tds(command);
    }
}