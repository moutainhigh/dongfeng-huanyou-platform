package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryStationTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取服务站搜索树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Component
public class QueryStationTreeHandler extends ValidateTokenAndReSetAbstracHandler<QueryStationTreeCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryStationTreeService queryStationTreeService;


    public QueryStationTreeHandler() {
        super(QueryStationTreeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryStationTreeCommand command) {
        HttpCommandResultWithData result = queryStationTreeService.queryStationTree(command);
        return result;
    }
}
