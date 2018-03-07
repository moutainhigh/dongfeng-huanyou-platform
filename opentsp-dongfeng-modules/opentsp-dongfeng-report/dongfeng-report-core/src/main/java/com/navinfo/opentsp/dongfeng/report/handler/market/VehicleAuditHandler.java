package com.navinfo.opentsp.dongfeng.report.handler.market;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.report.commands.market.VehicleAuditCommand;
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
public class VehicleAuditHandler extends ValidateTokenAndReSetAbstracHandler<VehicleAuditCommand, HttpCommandResultWithData> {

    @Autowired
    private IVehicleAuditService service;

    public VehicleAuditHandler() {
        super(VehicleAuditCommand.class, HttpCommandResultWithData.class);
    }

    protected VehicleAuditHandler(Class<VehicleAuditCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(VehicleAuditCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = service.audit(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.AUDIT_FAILED);
        }
        return result;
    }
}
