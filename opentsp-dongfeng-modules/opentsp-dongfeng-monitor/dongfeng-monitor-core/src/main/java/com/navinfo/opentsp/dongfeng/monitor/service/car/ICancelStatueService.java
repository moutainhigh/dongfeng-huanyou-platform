package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.CancelStatueCommand;

public interface ICancelStatueService {
    /**
     * 取消防控中间状态
     *
     * @param command
     */
    HttpCommandResultWithData cancelStatue(CancelStatueCommand command);
}
