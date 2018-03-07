package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.station.UpdateStationAuditStatusCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 二级服务站站点审核
 **/

@Component
public class UpdateStationAuditStatusHandler extends ValidateTokenAndReSetAbstracHandler<UpdateStationAuditStatusCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateStationAuditStatusHandler.class);
    @Resource
    private IStationService stationService;


    public UpdateStationAuditStatusHandler() {
        super(UpdateStationAuditStatusCommand.class, CommonResult.class);
    }

    protected UpdateStationAuditStatusHandler(Class<UpdateStationAuditStatusCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(UpdateStationAuditStatusCommand command) {
        CommonResult result = new CommonResult();
        try {
            stationService.batchUpdateStationAuditStatus(command);
        } catch (Exception e) {
            logger.error("update audit station failed,", e);
            result.fillResult(ReturnCode.UPDATE_AUDIT_OF_STATION_FAILED);
        }
        return result;
    }
}