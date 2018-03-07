package com.navinfo.opentsp.dongfeng.system.handler.business;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.business.QueryBusinessesCommand;
import com.navinfo.opentsp.dongfeng.system.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class QueryBusinessesHandler extends ValidateTokenAndReSetAbstracHandler<QueryBusinessesCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(QueryBusinessesHandler.class);

    @Autowired
    private IBusinessService businessService;
    @Resource
    private IOperateLogService operateLogService;

    public QueryBusinessesHandler() {
        super(QueryBusinessesCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryBusinessesHandler(Class<QueryBusinessesCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    @Override
    protected HttpCommandResultWithData businessHandle(QueryBusinessesCommand command) {
        HttpCommandResultWithData result = businessService.queryBusinesses(command,false);
        return result;
    }
}