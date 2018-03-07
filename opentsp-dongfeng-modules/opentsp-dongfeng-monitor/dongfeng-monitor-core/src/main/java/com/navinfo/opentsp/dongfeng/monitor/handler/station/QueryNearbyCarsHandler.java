package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryNearbyCarsCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryNearbyCarsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务站附近车辆
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
@Component
public class QueryNearbyCarsHandler extends ValidateTokenAndReSetAbstracHandler<QueryNearbyCarsCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryNearbyCarsService queryNearbyCarsService;

    protected static final Logger logger = LoggerFactory.getLogger(QueryStationCommentsHandler.class);

    public QueryNearbyCarsHandler() {
        super(QueryNearbyCarsCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryNearbyCarsHandler(Class<QueryNearbyCarsCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryNearbyCarsCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = queryNearbyCarsService.queryNearbyCars(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error("error",e);
        }
        return result;
    }
}
