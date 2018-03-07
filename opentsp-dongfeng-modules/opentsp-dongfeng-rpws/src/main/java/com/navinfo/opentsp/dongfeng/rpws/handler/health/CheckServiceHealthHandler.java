package com.navinfo.opentsp.dongfeng.rpws.handler.health;

import com.navinfo.opentsp.dongfeng.common.command.CheckServiceHealthCommand;
import com.navinfo.opentsp.dongfeng.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import org.springframework.stereotype.Component;

/**
 * 检查RPWS服务健康状态
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-30
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class CheckServiceHealthHandler extends NotValidateTokenAbstractHandler<CheckServiceHealthCommand, HttpCommandResultWithData> {

    public CheckServiceHealthHandler() {
        super(CheckServiceHealthCommand.class, HttpCommandResultWithData.class);
    }

    protected CheckServiceHealthHandler(Class<CheckServiceHealthCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(CheckServiceHealthCommand command) {
        return new HttpCommandResultWithData(ReturnCode.OK.code(), ReturnCode.OK.message());
    }
}
