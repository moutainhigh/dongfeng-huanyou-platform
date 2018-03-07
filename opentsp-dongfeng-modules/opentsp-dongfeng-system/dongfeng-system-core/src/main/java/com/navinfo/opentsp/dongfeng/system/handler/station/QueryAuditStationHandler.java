package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.station.QueryAuditStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 查询待审核的二级服务站站点
 */

@Component
public class QueryAuditStationHandler extends ValidateTokenAndReSetAbstracHandler<QueryAuditStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryAuditStationHandler.class);
    @Resource
    private IStationService stationService;

    public QueryAuditStationHandler() {
        super(QueryAuditStationCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryAuditStationHandler(Class<QueryAuditStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAuditStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.queryAuditStation(command);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.QUERY_AUDIT_OF_STATION_FAILED);
        }
        return result;
    }
}