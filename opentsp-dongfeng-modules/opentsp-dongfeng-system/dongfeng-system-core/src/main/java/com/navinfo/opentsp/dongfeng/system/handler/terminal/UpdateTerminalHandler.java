package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.UpdateTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class UpdateTerminalHandler extends ValidateTokenAndReSetAbstracHandler<UpdateTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(UpdateTerminalHandler.class);

    public UpdateTerminalHandler() {
        super(UpdateTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected UpdateTerminalHandler(Class<UpdateTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    /**
     * 1.验证终端ID唯一性
     * 2.验证终端通讯号唯一性
     * 3.验证设备标签ID唯一性
     * 4.填充各个字段包括同步标示
     * 5.同步位置云
     * 6.根据位置云同步结构做相应处理
     * 6.1 江淮&一汽
     * 终端通讯ID已存在----》生成永久同步ID再次同步
     * 设备ID已存在-》返回错误提示
     * 6.2 大运&华菱
     * 返回错误信息
     * 7.更新成功，写操作日志到日志表
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(UpdateTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.updateTerminal(command);
        } catch (Exception e) {
            result.fillResult(ReturnCode.UPDATE_TERMINAL_FAILED);
            logger.error(e.getMessage(), e);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.TERMINAL.getValue() + command.getTerminalId(), result);
        return result;
    }
}