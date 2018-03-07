package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationsCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务站选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Component
public class QueryStationsHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationsCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryStationsService queryStationsService;

    public QueryStationsHandler() {
        super(QueryStationsCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationsCommand command) {
        HttpCommandResultWithData result = queryStationsService.queryStations(command);
        return result;
    }
}
