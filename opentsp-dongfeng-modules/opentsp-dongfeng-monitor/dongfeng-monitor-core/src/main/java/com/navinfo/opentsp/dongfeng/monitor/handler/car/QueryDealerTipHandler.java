package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerTipCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryDealerTipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 经销商Tip框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/5/26
 */
@Component
public class QueryDealerTipHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealerTipCommand, HttpCommandResultWithData> {
    protected static final Logger logger = LoggerFactory.getLogger(QueryDealerTipHandler.class);
    @Autowired
    private IQueryDealerTipService dealerTipService;

    public QueryDealerTipHandler(){
        super(QueryDealerTipCommand.class,HttpCommandResultWithData.class);
    }
    protected QueryDealerTipHandler(Class<QueryDealerTipCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    @Override
    protected HttpCommandResultWithData businessHandle(QueryDealerTipCommand command) {
        HttpCommandResultWithData result = dealerTipService.queryDealerTip(command);
        return result;
    }
}
