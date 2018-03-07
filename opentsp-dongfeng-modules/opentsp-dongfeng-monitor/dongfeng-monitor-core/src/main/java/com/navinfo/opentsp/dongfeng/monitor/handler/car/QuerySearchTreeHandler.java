package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QuerySearchTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQuerySearchTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取车辆搜索树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Component
public class QuerySearchTreeHandler extends ValidateTokenAndReSetAbstracHandler<QuerySearchTreeCommand, HttpCommandResultWithData> {

    @Autowired
    private IQuerySearchTreeService querySearchTreeService;


    public QuerySearchTreeHandler() {
        super(QuerySearchTreeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QuerySearchTreeCommand command) {
        if (command.getCarStauts() == null){
            command.setCarStauts(63);
        }
        HttpCommandResultWithData result = querySearchTreeService.querySearchTree(command);
        return result;
    }
}
