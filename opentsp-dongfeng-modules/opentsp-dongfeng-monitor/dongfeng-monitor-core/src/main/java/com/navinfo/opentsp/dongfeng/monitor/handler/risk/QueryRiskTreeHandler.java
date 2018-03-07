package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IQueryRiskTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Component
public class QueryRiskTreeHandler extends ValidateTokenAndReSetAbstracHandler<QueryRiskTreeCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryRiskTreeService queryRiskTreeService;

    public QueryRiskTreeHandler() {
        super(QueryRiskTreeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRiskTreeCommand command) {
        HttpCommandResultWithData result = queryRiskTreeService.queryRiskTree(command);
        return result;
    }
}
