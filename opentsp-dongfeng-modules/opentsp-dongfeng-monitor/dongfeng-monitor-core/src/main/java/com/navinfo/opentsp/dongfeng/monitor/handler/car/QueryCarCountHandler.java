package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @wenya
 * 根据所画区域查询车次详细信息
 * @create 2017-04-17 15:26
 **/
@Component
public class QueryCarCountHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarCountCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryCarCountService carInfoService;

    public QueryCarCountHandler(){
        super(QueryCarCountCommand.class,HttpCommandResultWithData.class);
    }
    protected QueryCarCountHandler(Class<QueryCarCountCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarCountCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = carInfoService.queryCarCount(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(QueryCarInfoHandler.class.getSimpleName() + " Exception:", e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
