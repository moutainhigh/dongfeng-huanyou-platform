package com.navinfo.opentsp.dongfeng.system.handler.termodel;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.AddTerminaModelCommand;
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
public class AddTerminalModelHandler extends ValidateTokenAndReSetAbstracHandler<AddTerminaModelCommand, HttpCommandResultWithData> {

    @Autowired
    private TerminalModeService terminaModelImpl;
    @Resource
    private IOperateLogService operateLogService;

    public AddTerminalModelHandler() {
        super(AddTerminaModelCommand.class, HttpCommandResultWithData.class);
    }

    protected AddTerminalModelHandler(Class<AddTerminaModelCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(AddTerminaModelCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();

        try {
            result = terminaModelImpl.addTerminaModel(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
            logger.error(this.getClass().getName() + "`s businessHandle has error : ", e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.TERMINAL_MODEL.getValue() + command.getTmName(), result);
        return result;
    }
}