package com.navinfo.opentsp.dongfeng.system.handler.nonf9;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.QueryNonF9VehicleDetailCommand;
import com.navinfo.opentsp.dongfeng.system.pojo.NonF9VehiclePojo;
import com.navinfo.opentsp.dongfeng.system.service.INonF9VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 非F9车辆查询
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryNonF9VehicleDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryNonF9VehicleDetailCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryNonF9VehicleDetailHandler.class);

    public QueryNonF9VehicleDetailHandler() {
        super(QueryNonF9VehicleDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryNonF9VehicleDetailHandler(Class<QueryNonF9VehicleDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private INonF9VehicleService nonF9VehicleService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryNonF9VehicleDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            NonF9VehiclePojo nonF9VehiclePojo = nonF9VehicleService.queryNonF9VehicleDetail(command);
            result.setData(nonF9VehiclePojo);
        } catch (Exception e) {
            logger.error("search not f9 vehicle failed,{},command={},", e, command);
            result.fillResult(ReturnCode.QUERY_NON_F9_VEHICLE_FAILED);
        }
        return result;
    }
}