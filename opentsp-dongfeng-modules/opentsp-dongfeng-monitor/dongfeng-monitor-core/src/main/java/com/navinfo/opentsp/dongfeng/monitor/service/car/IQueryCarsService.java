package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarsCommand;

/**
 * 车辆选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public interface IQueryCarsService {
    /**
     * 车辆选择弹框
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCars(QueryCarsCommand command) ;
}
