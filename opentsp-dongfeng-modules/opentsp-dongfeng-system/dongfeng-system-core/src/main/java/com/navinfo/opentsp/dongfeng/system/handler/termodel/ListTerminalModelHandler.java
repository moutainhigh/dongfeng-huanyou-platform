package com.navinfo.opentsp.dongfeng.system.handler.termodel;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.ListTerminalModelCommand;
import com.navinfo.opentsp.dongfeng.system.service.TerminalModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangyue
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ListTerminalModelHandler extends ValidateTokenAndReSetAbstracHandler<ListTerminalModelCommand, HttpCommandResultWithData> {

    public ListTerminalModelHandler() {
        super(ListTerminalModelCommand.class, HttpCommandResultWithData.class);
    }

    protected ListTerminalModelHandler(Class<ListTerminalModelCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private TerminalModeService terminaModelImpl;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(ListTerminalModelCommand command) {

        HttpCommandResultWithData result = terminaModelImpl.listTerminalModelPage(command);
        return result;
    }

}
