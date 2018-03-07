package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.ImportScanCodeCommand;
import com.navinfo.opentsp.dongfeng.report.service.market.ImportScanCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-07
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ImportScanCodeHandler extends ValidateTokenAndReSetAbstracHandler<ImportScanCodeCommand, HttpCommandResultWithData> {

    @Autowired
    private ImportScanCodeService service;

    public ImportScanCodeHandler() {
        super(ImportScanCodeCommand.class, HttpCommandResultWithData.class);
    }

    protected ImportScanCodeHandler(Class<ImportScanCodeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ImportScanCodeCommand command)
    {
        return service.importScanCode(command);
    }
}
