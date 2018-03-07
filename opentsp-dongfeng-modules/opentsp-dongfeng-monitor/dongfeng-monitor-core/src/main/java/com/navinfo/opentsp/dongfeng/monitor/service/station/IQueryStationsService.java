package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryStationsCommand;

/**
 * 服务站选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public interface IQueryStationsService {
    /**
     * 车辆选择弹框
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryStations(QueryStationsCommand command) ;
}
