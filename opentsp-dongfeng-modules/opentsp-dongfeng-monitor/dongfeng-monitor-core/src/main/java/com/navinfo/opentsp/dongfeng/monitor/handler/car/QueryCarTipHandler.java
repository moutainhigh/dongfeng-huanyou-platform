package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarTipCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarTipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wenya on 2017/3/8.
 * 车辆tip详情页面
 */
@Component
public class QueryCarTipHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarTipCommand, HttpCommandResultWithData> {
    protected static final Logger logger = LoggerFactory.getLogger(QueryCarTipHandler.class);
    @Autowired
   private IQueryCarTipService carTipService;

    public QueryCarTipHandler(){
        super(QueryCarTipCommand.class,HttpCommandResultWithData.class);
    }
    protected QueryCarTipHandler(Class<QueryCarTipCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarTipCommand command) {
        HttpCommandResultWithData result = carTipService.queryCarTip(command);
        return result;
    }
}
