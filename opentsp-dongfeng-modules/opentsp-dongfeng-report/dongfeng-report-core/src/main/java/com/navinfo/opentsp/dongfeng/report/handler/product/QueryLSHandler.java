package com.navinfo.opentsp.dongfeng.report.handler.product;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.report.commands.product.QueryLSCommand;
import com.navinfo.opentsp.dongfeng.report.service.product.IQueryLSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产线漏扫报表
 *
 * @wenya
 * @create 2017-03-28 9:23
 **/
@Component
public class QueryLSHandler extends ValidateTokenAndReSetAbstracHandler<QueryLSCommand , HttpCommandResultWithData> {
    @Autowired
    private IQueryLSService queryLSService;
    public QueryLSHandler(){
        super(QueryLSCommand.class,HttpCommandResultWithData.class);
    }

    protected QueryLSHandler(Class<QueryLSCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryLSCommand command) {
        HttpCommandResultWithData result = queryLSService.queryLs(command,false);
        result.fillResult(ReturnCode.SERVER_ERROR);
        return result;
    }
}
