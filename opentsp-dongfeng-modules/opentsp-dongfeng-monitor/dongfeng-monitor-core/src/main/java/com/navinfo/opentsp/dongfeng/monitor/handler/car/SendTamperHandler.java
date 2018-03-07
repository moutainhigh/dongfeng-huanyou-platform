package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.SendTamperCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ISendTamperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @wenya
 * @create 2017-03-27 18:29
 **/
@Component
public class SendTamperHandler extends ValidateTokenAndReSetAbstracHandler<SendTamperCommand, HttpCommandResultWithData> {
   @Autowired
   private ISendTamperService sendTamperService;
    public SendTamperHandler() {
        super(SendTamperCommand.class,HttpCommandResultWithData.class);
    }

    public SendTamperHandler(Class<SendTamperCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(SendTamperCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = sendTamperService.sendTamper(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
