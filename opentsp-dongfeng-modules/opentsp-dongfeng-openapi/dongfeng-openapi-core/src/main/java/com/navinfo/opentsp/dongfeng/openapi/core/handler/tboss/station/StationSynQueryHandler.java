package com.navinfo.opentsp.dongfeng.openapi.core.handler.tboss.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.station.StationSynQueryCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.handler.abstracts.ValidateUsingUserDefinedMethodAbstractHandler;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IStationSynQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class StationSynQueryHandler extends ValidateUsingUserDefinedMethodAbstractHandler<StationSynQueryCommand, HttpCommandResultWithData> {

    @Autowired
    IStationSynQueryService iStationSynQueryService;

    protected static final Logger logger = LoggerFactory.getLogger(StationSynQueryHandler.class);

    public StationSynQueryHandler() {
        super(StationSynQueryCommand.class, HttpCommandResultWithData.class);
    }

    protected StationSynQueryHandler(Class<StationSynQueryCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData openApiHandle(StationSynQueryCommand command) {
        HttpCommandResultWithData result = iStationSynQueryService.stationSynQuery(command);
        return result;
    }
}