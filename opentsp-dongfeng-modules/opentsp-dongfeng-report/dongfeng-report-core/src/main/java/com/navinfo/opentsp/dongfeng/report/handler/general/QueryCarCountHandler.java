package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarCountCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.IQueryCarCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 全国车次统计
 *
 * @wenya
 * @create 2017-03-28 13:51
 **/
@Component
public class QueryCarCountHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarCountCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryCarCountService carCountService;
    public QueryCarCountHandler(){
        super(QueryCarCountCommand.class,HttpCommandResultWithData.class);
    }
    public QueryCarCountHandler(Class<QueryCarCountCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarCountCommand command) {
        HttpCommandResultWithData result = carCountService.queryCarCount(command);
        return result;
    }
}
