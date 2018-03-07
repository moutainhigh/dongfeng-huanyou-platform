package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.VoiceMonitoringCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */

public interface IVoiceMonitoringService {
    /**
     * 语音监控
     *
     * @param command
     * @return
     */
    public CommonResult voiceMonitoring(VoiceMonitoringCommand command) throws Exception;
}
