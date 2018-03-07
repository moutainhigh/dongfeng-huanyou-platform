package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.AddTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新增终端
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class AddTerminalHandler extends ValidateTokenAndReSetAbstracHandler<AddTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(AddTerminalHandler.class);

    public AddTerminalHandler() {
        super(AddTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected AddTerminalHandler(Class<AddTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;


    /**
     * 1.验证终端ID唯一性
     * 2.验证终端通讯号唯一性
     * 3.验证设备标签ID唯一性
     * 4.填充各个字段包括同步标示
     * 5.是否已删除，如果已删除，恢复删除的终端
     * 6.同步位置云
     * 6.根据位置云同步结构做相应处理
     * 6.1 江淮&一汽
     * 终端通讯ID已存在----》生成永久同步ID再次同步
     * 设备ID已存在-》返回错误提示
     * 6.2 大运&华菱
     * 返回错误信息
     * 7.新增成功，写操作日志到日志表
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(AddTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.addTerminal(command, 0);
        } catch (Exception e) {
            logger.error(ReturnCode.ADD_TERMINAL_FAILED.message(), e);
            result.fillResult(ReturnCode.ADD_TERMINAL_FAILED);
        }
        return result;

    }


}