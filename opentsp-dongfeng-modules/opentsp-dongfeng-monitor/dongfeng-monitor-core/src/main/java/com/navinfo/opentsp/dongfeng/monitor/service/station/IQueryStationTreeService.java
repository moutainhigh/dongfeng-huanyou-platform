package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationTreeCommand;

/**
 * 获取服务站搜索树
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
public interface IQueryStationTreeService {
    /**
     * 获取服务站搜索树
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryStationTree(QueryStationTreeCommand command);
}
