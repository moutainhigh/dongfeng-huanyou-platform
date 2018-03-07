package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.station.SyncStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 同步服务站
 **/

@Component
public class SyncStationHandler extends ValidateTokenAndReSetAbstracHandler<SyncStationCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(SyncStationHandler.class);

    private final static Integer SYNC_STATION_TO_TAG = 1;
    private final static Integer SYNC_STATION_TO_TBOSS = 2;
    @Resource
    private IStationService stationService;
    @Value("${sign.server.syn.switch}")
    private String syncTagSwitch;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;

    public SyncStationHandler() {
        super(SyncStationCommand.class, CommonResult.class);
    }

    protected SyncStationHandler(Class<SyncStationCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(SyncStationCommand command) {
        CommonResult result = new CommonResult();
        try {
            if (SYNC_STATION_TO_TAG.equals(command.getSyncType())) {
                if (Boolean.valueOf(syncTagSwitch)) {
                    stationService.syncServiceStationToTag(command.getStationIds(), command.getOperateType());
                } else {
                    result.fillResult(ReturnCode.SYN_NO_SIGN_SWITH);
                }
            } else if (SYNC_STATION_TO_TBOSS.equals(command.getSyncType())) {
                if (Boolean.valueOf(syncStationTeamSwitch)) {
                    stationService.syncServiceStationToTBoss(command.getStationIds(), command.getOperateType());
                } else {
                    result.fillResult(ReturnCode.SYN_NO_BOSS_SWITH);
                }
            }
        } catch (Exception e) {
            logger.error("sync station failed,", e);
            result.setResultCode(ReturnCode.STATION_SYNC_FAILED.code());
            result.setMessage(e.getMessage());
        }
        return result;
    }
}