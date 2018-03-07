package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarLocCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarLocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地图打点
 *
 * @wenya
 * @create 2017-03-09 16:07
 **/
@Component
public class QueryCarLocHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarLocCommand, HttpCommandResultWithData> {
    protected static final Logger logger = LoggerFactory.getLogger(QueryCarLocHandler.class);
    @Autowired
    private IQueryCarLocService carLocService;

    public QueryCarLocHandler(){
        super(QueryCarLocCommand.class,HttpCommandResultWithData.class);
    }

    protected QueryCarLocHandler(Class<QueryCarLocCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarLocCommand command) {
        HttpCommandResultWithData result = carLocService.queryCarLoc(command);
        return result;
    }
}
