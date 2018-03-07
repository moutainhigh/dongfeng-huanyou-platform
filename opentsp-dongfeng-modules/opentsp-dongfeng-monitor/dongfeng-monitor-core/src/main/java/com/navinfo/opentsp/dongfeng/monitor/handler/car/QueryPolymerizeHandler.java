package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryPolymerizeCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryPolymerizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取聚合点
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/23
 */
@Component
public class QueryPolymerizeHandler extends ValidateTokenAndReSetAbstracHandler<QueryPolymerizeCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryPolymerizeService queryPolymerizeService;

    public QueryPolymerizeHandler() {
        super(QueryPolymerizeCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryPolymerizeCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = queryPolymerizeService.queryPolymerize(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error("error",e);
        }
        return result;
    }
}
