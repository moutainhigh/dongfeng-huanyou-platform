package com.navinfo.opentsp.dongfeng.system.handler.nonf9;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.UpdateNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.service.INonF9VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 非F9车辆更新
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class UpdateNonF9VehicleHandler extends ValidateTokenAndReSetAbstracHandler<UpdateNonF9VehicleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateNonF9VehicleHandler.class);

    public UpdateNonF9VehicleHandler() {
        super(UpdateNonF9VehicleCommand.class, HttpCommandResultWithData.class);
    }

    protected UpdateNonF9VehicleHandler(Class<UpdateNonF9VehicleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private INonF9VehicleService nonF9VehicleService;

    @Override
    protected HttpCommandResultWithData businessHandle(UpdateNonF9VehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = nonF9VehicleService.updateNonF9Vehicle(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.UPDATE_NON_F9_VEHICLE_FAILED);
            logger.error(result.getMessage(), e);
        }
        return result;
    }
}