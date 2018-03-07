package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryNearbyCarsCommand;

/**
 * 服务站附近车辆
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/29
 */
public interface IQueryNearbyCarsService {
    /**
     * 服务站附近车辆
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryNearbyCars(QueryNearbyCarsCommand command);
}
