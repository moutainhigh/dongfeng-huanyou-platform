package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryPolymerizeCommand;

/**
 * 获取聚合点
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/23
 */
public interface IQueryPolymerizeService {

    public HttpCommandResultWithData queryPolymerize(QueryPolymerizeCommand command) ;
}
