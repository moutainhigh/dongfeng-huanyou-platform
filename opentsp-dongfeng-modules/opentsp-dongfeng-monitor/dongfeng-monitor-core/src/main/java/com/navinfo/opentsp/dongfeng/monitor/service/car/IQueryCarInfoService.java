package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarInfoCommand;

/**
 * 车辆详情
 *
 * @wenya
 * @create 2017-03-13 17:53
 **/
public interface IQueryCarInfoService {
    /**
     * 车辆详细信息
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarInfo(QueryCarInfoCommand command);
}
