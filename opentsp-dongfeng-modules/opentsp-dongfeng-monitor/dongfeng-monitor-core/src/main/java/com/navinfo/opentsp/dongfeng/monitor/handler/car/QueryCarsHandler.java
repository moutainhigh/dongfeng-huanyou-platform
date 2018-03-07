package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarsCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Component
public class QueryCarsHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarsCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryCarsService queryCarsService;

    public QueryCarsHandler() {
        super(QueryCarsCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarsCommand command) {
        HttpCommandResultWithData result = queryCarsService.queryCars(command);
        return result;
    }
}
