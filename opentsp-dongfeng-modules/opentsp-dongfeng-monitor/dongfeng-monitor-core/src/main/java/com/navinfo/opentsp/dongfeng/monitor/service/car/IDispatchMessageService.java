package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.DispatchMessageCommand;

/**
 * 短信调度服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public interface IDispatchMessageService {
    /**
     * 发送短信
     *
     * @param command
     */
    HttpCommandResultWithData sendMessage(DispatchMessageCommand command);
}
