package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.station.QueryStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-12
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryStationHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryStationHandler.class);

    public QueryStationHandler() {
        super(QueryStationCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationHandler(Class<QueryStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = stationService.queryStation(command, true);
            result.setData(pagingInfo);
        } catch (Exception e) {
            logger.error("search station failed,{},command={},", e, command);
            result.fillResult(ReturnCode.QUERY_STATION_FAILED);
        }
        return result;
    }
}