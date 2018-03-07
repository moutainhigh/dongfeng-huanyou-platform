package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarInfoCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 车辆详情
 *
 * @wenya
 * @create 2017-03-13 17:47
 **/
@Component
public class QueryCarInfoHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarInfoCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryCarInfoService carInfoService;
    public QueryCarInfoHandler(){
        super(QueryCarInfoCommand.class,HttpCommandResultWithData.class);
    }

    protected QueryCarInfoHandler(Class<QueryCarInfoCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarInfoCommand command) {
        HttpCommandResultWithData result = carInfoService.queryCarInfo(command);
        return result;
    }
}
