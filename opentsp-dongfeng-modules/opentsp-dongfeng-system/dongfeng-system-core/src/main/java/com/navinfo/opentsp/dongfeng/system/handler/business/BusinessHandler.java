package com.navinfo.opentsp.dongfeng.system.handler.business;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.business.BusinessCommand;
import com.navinfo.opentsp.dongfeng.system.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/14.
 * 客户查询
 */
@Component
public class BusinessHandler extends ValidateTokenAndReSetAbstracHandler<BusinessCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(BusinessCommand.class);

    @Autowired
    private IBusinessService businessService;

    public BusinessHandler() {
        super(BusinessCommand.class, HttpCommandResultWithData.class);
    }

    protected BusinessHandler(Class<BusinessCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(BusinessCommand command) {
        HttpCommandResultWithData result = businessService.queryBusiness(command);
        return result;
    }
}
