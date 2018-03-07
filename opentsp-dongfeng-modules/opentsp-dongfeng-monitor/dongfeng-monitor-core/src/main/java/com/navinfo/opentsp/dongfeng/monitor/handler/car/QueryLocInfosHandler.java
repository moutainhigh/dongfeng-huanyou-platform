package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryLocInfosCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryLocInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取车辆异步树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Component
public class QueryLocInfosHandler extends ValidateTokenAndReSetAbstracHandler<QueryLocInfosCommand, HttpCommandResultWithData> {

    @Autowired
    private IQueryLocInfosService queryLocInfosService;


    public QueryLocInfosHandler() {
        super(QueryLocInfosCommand.class, HttpCommandResultWithData.class);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryLocInfosCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            if(command.getTeamIds() == null){
                command.setTeamIds("");
            }
            if(command.getCarIds() == null){
                command.setCarIds("");
            }
            if(command.getChassisNum() == null){
                command.setChassisNum("");
            }

            result = queryLocInfosService.queryLocInfos(command);
        }catch (Exception e){
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error("error",e);
        }
        return result;
    }
}
