package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.DeleteTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 删除终端
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class DeleteTerminalHandler extends ValidateTokenAndReSetAbstracHandler<DeleteTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(DeleteTerminalHandler.class);

    public DeleteTerminalHandler() {
        super(DeleteTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected DeleteTerminalHandler(Class<DeleteTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    /**
     * 删除逻辑
     * 1.判断用户是否有删除终端的权限即终端所在的分组用户是否有权限
     * 2.判断所删除的终端是否绑定了车辆
     * 3.绑定了终端提示失败
     * 4.从调用位置云接口删除终端
     * 5.从位置云删除失败，给出提示
     * 6.从库中删除终端
     * 7.删除成功，写操作日志到日志表
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(DeleteTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.delete(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.DELETE_TERMINAL_FAILED);
            logger.error("===== delete Terminal Exception =====" , e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.TERMINAL.getValue() + command.getTerminalId(), result);
        return result;
    }
}