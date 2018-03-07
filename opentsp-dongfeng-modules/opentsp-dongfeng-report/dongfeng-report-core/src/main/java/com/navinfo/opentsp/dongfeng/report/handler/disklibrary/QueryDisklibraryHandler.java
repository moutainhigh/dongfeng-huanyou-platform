package com.navinfo.opentsp.dongfeng.report.handler.disklibrary;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDisklibraryCommand;
import com.navinfo.opentsp.dongfeng.report.service.disklibrary.IQueryDisklibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryDisklibraryHandler extends ValidateTokenAndReSetAbstracHandler<QueryDisklibraryCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryDisklibraryHandler.class);

    @Autowired
    IQueryDisklibraryService iQueryDisklibraryService;

    public QueryDisklibraryHandler() {
        super(QueryDisklibraryCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryDisklibraryHandler(Class<QueryDisklibraryCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryDisklibraryCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo pagingInfo = iQueryDisklibraryService.queryDisklibrary(command, true);
        result.setData(pagingInfo);
        return result;
    }
}