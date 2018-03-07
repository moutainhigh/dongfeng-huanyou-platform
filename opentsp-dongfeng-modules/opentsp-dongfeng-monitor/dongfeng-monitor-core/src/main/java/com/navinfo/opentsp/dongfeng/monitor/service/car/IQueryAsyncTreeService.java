package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryAsyncTreeCommand;

/**
 * 获取车辆异步树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/11
 */
public interface IQueryAsyncTreeService {
    /**
     * 获取车辆异步树
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryAsyncTree(QueryAsyncTreeCommand command);
}
