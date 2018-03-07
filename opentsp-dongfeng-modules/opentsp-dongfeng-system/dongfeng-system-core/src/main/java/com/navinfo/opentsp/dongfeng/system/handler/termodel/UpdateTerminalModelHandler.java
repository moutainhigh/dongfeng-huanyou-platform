package com.navinfo.opentsp.dongfeng.system.handler.termodel;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.UpdateTerminalModelCommand;
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
public class UpdateTerminalModelHandler extends ValidateTokenAndReSetAbstracHandler<UpdateTerminalModelCommand, HttpCommandResultWithData> {

    public UpdateTerminalModelHandler() {
        super(UpdateTerminalModelCommand.class, HttpCommandResultWithData.class);
    }

    protected UpdateTerminalModelHandler(Class<UpdateTerminalModelCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private TerminalModeService terminaModelImpl;
    @Resource
    private IOperateLogService operateLogService;

    @Override
    protected HttpCommandResultWithData businessHandle(UpdateTerminalModelCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();

        try {
            if (!StringUtil.isEmpty(command.getTmId())) {
                result = terminaModelImpl.updateTerminaModel(command);
            } else {
                result.fillResult(ReturnCode.ERROR_TERMINA_MODEL_IDNULL);
            }

        } catch (Exception e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.TERMINAL_MODEL.getValue() + command.getTmName(), result);
        return result;
    }
}
