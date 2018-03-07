package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarTipCommand;

/**
 * @wenya
 * @create 2017-03-10 9:34
 **/

public interface IQueryCarTipService {
    /**
     * 点击车辆树上节点，显示车辆tip详细信息
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarTip(QueryCarTipCommand command);
}
