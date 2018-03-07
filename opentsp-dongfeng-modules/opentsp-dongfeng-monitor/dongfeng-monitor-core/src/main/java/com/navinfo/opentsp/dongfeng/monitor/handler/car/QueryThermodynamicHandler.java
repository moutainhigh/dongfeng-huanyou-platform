package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryThermodynamicCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryThermodynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地图热力图显示
 *
 * @wenya
 * @create 2017-04-12 11:41
 **/
@Component
public class QueryThermodynamicHandler extends ValidateTokenAndReSetAbstracHandler<QueryThermodynamicCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryThermodynamicService queryThermodynamicService;

    public QueryThermodynamicHandler(){
        super(QueryThermodynamicCommand.class,HttpCommandResultWithData.class);
    }
    protected QueryThermodynamicHandler(Class<QueryThermodynamicCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryThermodynamicCommand command) {
        logger.info(" ===== businessHandle start==========");
        logger.info(command.toString());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = queryThermodynamicService.queryThermodynamic(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(CancelStatueHandler.class.getSimpleName() + " Exception:",e);
        }
        logger.info(" ===== businessHandle end==========");
        return result;
    }
}
