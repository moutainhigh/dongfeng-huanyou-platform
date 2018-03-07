package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.station.UpdateStationCommand;
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
public class UpdateStationHandler extends ValidateTokenAndReSetAbstracHandler<UpdateStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateStationHandler.class);

    public UpdateStationHandler() {
        super(UpdateStationCommand.class, HttpCommandResultWithData.class);
    }

    protected UpdateStationHandler(Class<UpdateStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;
    @Resource
    private IOperateLogService operateLogService;
    @Autowired
    private ISyncService syncService;

    @Override
    protected HttpCommandResultWithData businessHandle(UpdateStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.updateStation(command);
            //新增成功，同步到云端
            if (result.getResultCode() == ReturnCode.OK.code()) {
                String province = command.getBasicInfoBean().getProvince();
                String city = command.getBasicInfoBean().getCity();
                syncService.asyncStation(command.getStationId(), province, city, Constants.SyncOperateEnum.UPDATE.value());
            }
        } catch (Exception e) {
            if(e instanceof BaseRuntimeException){
                result.setResultCode(ReturnCode.UPDATE_STATION_FAILED.code());
                result.setMessage(e.getMessage());
            }else{
                result.fillResult(ReturnCode.UPDATE_STATION_FAILED);
            }
            logger.error(result.getMessage(), e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + command.getBasicInfoBean().getStationCode(), result);
        return result;
    }
}