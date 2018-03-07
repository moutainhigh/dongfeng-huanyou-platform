package com.navinfo.opentsp.dongfeng.push.health;

import com.navinfo.opentsp.dongfeng.common.command.CheckServiceHealthCommand;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class QueryHealthHandler extends NotValidateTokenAbstractHandler<CheckServiceHealthCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(CheckServiceHealthCommand.class);

    public QueryHealthHandler() {
        super(CheckServiceHealthCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryHealthHandler(Class<CheckServiceHealthCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(CheckServiceHealthCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result.setData("成功");
        return result;
    }
}