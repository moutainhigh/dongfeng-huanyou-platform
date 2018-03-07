package com.navinfo.opentsp.dongfeng.system.handler.terminal;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.SettingTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class SettingTerminalHandler extends ValidateTokenAndReSetAbstracHandler<SettingTerminalCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(SettingTerminalHandler.class);

    public SettingTerminalHandler() {
        super(SettingTerminalCommand.class, HttpCommandResultWithData.class);
    }

    protected SettingTerminalHandler(Class<SettingTerminalCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private ITerminalService terminalService;
    @Resource
    private IOperateLogService operateLogService;

    /**
     * 1.获取终端绑定的车辆信息和分组信息
     * 2.如果车辆离线则不下发终端设置
     * 3.下发位置数据采集汇报设置
     * 4.下发Can数据采集汇报设置（只有F9下发该设置）
     * 5.下发终端初始化参数设置（只有F9下发该设置）
     * 6.下发违规驾驶行为阀值设置（只有F9下发该设置）
     * 7.下发终端升级设置
     * 8.写终端日志
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(SettingTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = terminalService.setting(command);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.fillResult(ReturnCode.SETTING_TERMINAL_FAILED);
        }
//        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.SETTING, OperateLogConstants.OperateContentPrefixEnum.TERMINAL.getValue() + command.getTerminalId(), result);
        return result;
    }
}