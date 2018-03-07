package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealersCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Component
public class QueryDealersHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealersCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryDealersService queryDealersService;

    public QueryDealersHandler() {
        super(QueryDealersCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryDealersCommand command) {
        HttpCommandResultWithData result = queryDealersService.queryDealers(command);
        return result;
    }
}
