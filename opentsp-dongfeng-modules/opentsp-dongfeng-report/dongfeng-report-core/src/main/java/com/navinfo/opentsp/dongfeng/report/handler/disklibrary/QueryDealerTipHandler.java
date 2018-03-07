package com.navinfo.opentsp.dongfeng.report.handler.disklibrary;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDealerTipCommand;
import com.navinfo.opentsp.dongfeng.report.service.disklibrary.IQueryDisklibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 经销商弹窗
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class QueryDealerTipHandler extends ValidateTokenAndReSetAbstracHandler<QueryDealerTipCommand, HttpCommandResultWithData> {

    @Autowired
    IQueryDisklibraryService iQueryDisklibraryService;

    public QueryDealerTipHandler(){
        super(QueryDealerTipCommand.class,HttpCommandResultWithData.class);
    }
    public QueryDealerTipHandler(Class<QueryDealerTipCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryDealerTipCommand command) {
        HttpCommandResultWithData result = iQueryDisklibraryService.queryDealerTip(command);
        return result;
    }
}
