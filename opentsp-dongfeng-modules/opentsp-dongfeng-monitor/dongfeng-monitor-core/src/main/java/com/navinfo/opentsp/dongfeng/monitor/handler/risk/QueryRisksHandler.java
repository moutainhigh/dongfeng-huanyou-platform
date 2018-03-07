package com.navinfo.opentsp.dongfeng.monitor.handler.risk;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRisksCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.risk.IQueryRisksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 防控区域选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Component
public class QueryRisksHandler extends ValidateTokenAndReSetAbstracHandler<QueryRisksCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryRisksService queryRisksService;

    public QueryRisksHandler() {
        super(QueryRisksCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryRisksCommand command) {
        HttpCommandResultWithData result = queryRisksService.queryRisks(command);
        return result;
    }
}
