package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.station.EnableOrDisableStationCommand;
import com.navinfo.opentsp.dongfeng.system.entity.HyServiceStationEntity;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class EnableOrDisableStationHandler extends ValidateTokenAndReSetAbstracHandler<EnableOrDisableStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(EnableOrDisableStationHandler.class);

    public EnableOrDisableStationHandler() {
        super(EnableOrDisableStationCommand.class, HttpCommandResultWithData.class);
    }

    protected EnableOrDisableStationHandler(Class<EnableOrDisableStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;
    @Resource
    private IOperateLogService operateLogService;
    @Autowired
    private ISyncService syncService;

    private static final int STATION_ENABLE = 1;// 服务站启用标志
    private static final int STATION_DISABLE = 0;// 服务站停用标志

    @Override
    protected HttpCommandResultWithData businessHandle(EnableOrDisableStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.enableOrDisableStation(command);
            //停用启用成功，同步到云端
            if (result.getResultCode() == ReturnCode.OK.code()) {
                HyServiceStationEntity stationEntity = (HyServiceStationEntity) result.getData();
                String stationId = stationEntity.getId().toString();
                String province = stationEntity.getPovince().toString();
                String city = stationEntity.getCity().toString();
                int operateType = stationEntity.getSyncStatus();
                if (command.getSwitchStatus() == STATION_ENABLE) {
                    operateType = Constants.SyncOperateEnum.SWITCH_ON.value();
                } else if (command.getSwitchStatus() == STATION_DISABLE) {
                    operateType = Constants.SyncOperateEnum.SWITCH_OFF.value();
                }
                syncService.asyncStation(stationId, province, city, operateType);
                result.setData(null);
            }
        } catch (Exception e) {
            logger.error("enable or disable station failed,", e);
            result.fillResult(ReturnCode.ENABLE_OR_DISABLE_STATION_FAILED);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + command.getStationName(), result);
        return result;
    }
}