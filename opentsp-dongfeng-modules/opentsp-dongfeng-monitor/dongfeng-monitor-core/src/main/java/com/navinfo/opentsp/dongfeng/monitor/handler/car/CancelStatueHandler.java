package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.CancelStatueCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.ICancelStatueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消防控激活中间状态
 * 正在激活，正在取消激活，正在锁车，正在解锁
 * @wenya
 * @create 2017-04-12 10:58
 **/
@Component
public class CancelStatueHandler extends ValidateTokenAndReSetAbstracHandler<CancelStatueCommand, HttpCommandResultWithData> {
    @Autowired
    private ICancelStatueService cancelStatueService;

    protected CancelStatueHandler(Class<CancelStatueCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }
    public  CancelStatueHandler(){
        super(CancelStatueCommand.class,HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(CancelStatueCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = cancelStatueService.cancelStatue(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(e.getMessage(), e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
