package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.DispatchMessageCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IDispatchMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class DispatchMessageHandler extends ValidateTokenAndReSetAbstracHandler<DispatchMessageCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(DispatchMessageHandler.class);

    public DispatchMessageHandler() {
        super(DispatchMessageCommand.class, HttpCommandResultWithData.class);
    }

    protected DispatchMessageHandler(Class<DispatchMessageCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IDispatchMessageService dispatchMessageService;

    @Override
    protected HttpCommandResultWithData businessHandle(DispatchMessageCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = dispatchMessageService.sendMessage(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.DISPATCH_MESSAGE_ERROR);
            logger.error("发送调度短信异常，" , e);
        }
        return result;
    }
}