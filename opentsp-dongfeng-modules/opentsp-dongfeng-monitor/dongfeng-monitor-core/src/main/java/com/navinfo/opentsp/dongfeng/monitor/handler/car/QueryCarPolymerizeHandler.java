package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarPolymerizeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarPolymerizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取搜索车辆的定位信息
 * @author xltianc-zh
 * 
 */
@Component
public class QueryCarPolymerizeHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarPolymerizeCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryCarPolymerizeService service;


    public QueryCarPolymerizeHandler() {
        super(QueryCarPolymerizeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarPolymerizeCommand command) {
        if (command.getCarStauts() == null){
            command.setCarStauts(63);
        }
        HttpCommandResultWithData result = service.queryCarPolymerize(command);
        return result;
    }
}
