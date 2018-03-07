package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerVehicleDetailCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealerVehicleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-26
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryDealerVehicleDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealerVehicleDetailCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryDealerVehicleDetailService dealerVehicleService;

    public QueryDealerVehicleDetailHandler() {
        super(QueryDealerVehicleDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryDealerVehicleDetailHandler(Class<QueryDealerVehicleDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryDealerVehicleDetailCommand command) {
        return dealerVehicleService.query(command);
    }
}
