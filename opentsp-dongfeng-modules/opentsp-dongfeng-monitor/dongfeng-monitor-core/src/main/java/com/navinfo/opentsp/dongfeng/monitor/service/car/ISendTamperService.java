package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.SendTamperCommand;

/**
 * 发送防控激活
 * Created by wenya on 2017/3/27.
 */
public interface ISendTamperService {
    public HttpCommandResultWithData sendTamper(SendTamperCommand command);
}
