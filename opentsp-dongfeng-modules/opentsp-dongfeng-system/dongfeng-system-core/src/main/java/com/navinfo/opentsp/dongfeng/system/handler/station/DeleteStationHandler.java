package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.station.DeleteStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
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
public class DeleteStationHandler extends ValidateTokenAndReSetAbstracHandler<DeleteStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(DeleteStationHandler.class);

    public DeleteStationHandler() {
        super(DeleteStationCommand.class, HttpCommandResultWithData.class);
    }

    protected DeleteStationHandler(Class<DeleteStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(DeleteStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.deleteStation(command);
        } catch (RuntimeException e) {
            result.setResultCode(ReturnCode.DELETE_STATION_FAILED.code());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("delete station failed,", e);
            result.fillResult(ReturnCode.DELETE_STATION_FAILED);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + command.getStationName(), result);
        return result;
    }
}