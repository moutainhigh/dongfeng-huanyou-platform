package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAsyncTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryAsyncTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取车辆异步树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Component
public class QueryAsyncTreeHandler  extends ValidateTokenAndReSetAbstracHandler<QueryAsyncTreeCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryAsyncTreeService queryAsyncTreeService;


    public QueryAsyncTreeHandler() {
        super(QueryAsyncTreeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryAsyncTreeCommand command) {
        command.setChecked(command.getChecked()==null? false:command.getChecked());
        HttpCommandResultWithData result = queryAsyncTreeService.queryAsyncTree(command);
        return result;
    }
}
