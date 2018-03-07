package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.VoiceMonitoringCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IVoiceMonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-27
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class VoiceMonitoringHandler extends ValidateTokenAndReSetAbstracHandler<VoiceMonitoringCommand, CommonResult> {

    @Autowired
    IVoiceMonitoringService iVoiceMonitoringService;

    protected static final Logger logger = LoggerFactory.getLogger(VoiceMonitoringHandler.class);

    public VoiceMonitoringHandler() {
        super(VoiceMonitoringCommand.class, CommonResult.class);
    }

    protected VoiceMonitoringHandler(Class<VoiceMonitoringCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(VoiceMonitoringCommand command) {
        {
            CommonResult result = new CommonResult();
            try {
                result = iVoiceMonitoringService.voiceMonitoring(command);
            }catch (Exception e){
                result.setResultCode(ReturnCode.SERVER_ERROR.code());
                result.setMessage("发送失败");
            }
            return result;
        }
    }
}