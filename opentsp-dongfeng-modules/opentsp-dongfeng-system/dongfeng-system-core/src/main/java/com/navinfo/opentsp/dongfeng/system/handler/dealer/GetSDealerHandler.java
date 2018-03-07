package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.GetSDealerCommand;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class GetSDealerHandler extends ValidateTokenAndReSetAbstracHandler<GetSDealerCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(GetSDealerHandler.class);

    @Resource
    private DealerService dealerService;

    public GetSDealerHandler() {
        super(GetSDealerCommand.class, HttpCommandResultWithData.class);
    }

    protected GetSDealerHandler(Class<GetSDealerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(GetSDealerCommand command) {
        return dealerService.getDealer(command);
    }
}