package com.navinfo.opentsp.dongfeng.report.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.station.QueryStationLoadCommand;

/**
 * 查询服务站负荷分析
 * @author wt
 * @date 2017-03-27
 * @modify
 * @version 1.0
 */
public interface IStationLoadService {
    /**
     * Query Server Station Load Analysis
     * @param command
     * @return
     */
    HttpCommandResultWithData queryStationLoadInfos(final QueryStationLoadCommand command, final boolean isQueryAll);
}
