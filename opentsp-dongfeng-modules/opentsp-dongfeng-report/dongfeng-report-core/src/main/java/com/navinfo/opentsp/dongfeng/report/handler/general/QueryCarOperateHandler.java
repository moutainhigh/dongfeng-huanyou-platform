package com.navinfo.opentsp.dongfeng.report.handler.general;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarOperateCommand;
import com.navinfo.opentsp.dongfeng.report.service.general.ICarOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 运营车况查看
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/27
 */
@Component
public class QueryCarOperateHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarOperateCommand, HttpCommandResultWithData> {
    @Autowired
    private ICarOperateService carOperateService;

    public QueryCarOperateHandler() {
        super(QueryCarOperateCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryCarOperateHandler(Class<QueryCarOperateCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarOperateCommand command) {
        HttpCommandResultWithData result = carOperateService.queryCarOperate(command);
        return result;
    }
}
