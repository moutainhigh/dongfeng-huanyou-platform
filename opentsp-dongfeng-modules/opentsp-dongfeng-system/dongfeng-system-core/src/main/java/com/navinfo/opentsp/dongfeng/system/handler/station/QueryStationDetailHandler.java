package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.station.QueryStationDetailCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryStationDetailHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationDetailCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryStationDetailHandler.class);

    public QueryStationDetailHandler() {
        super(QueryStationDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryStationDetailHandler(Class<QueryStationDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.queryStationDetail(command);
        } catch (Exception e) {
            logger.error("query station failed,{},stationId={},", e, command.getStationId());
            result.fillResult(ReturnCode.QUERY_STATION_DETAIL_FAILED);
        }
        return result;
    }
}