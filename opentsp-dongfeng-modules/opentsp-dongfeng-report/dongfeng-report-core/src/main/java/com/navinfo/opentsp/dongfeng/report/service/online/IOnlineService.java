package com.navinfo.opentsp.dongfeng.report.service.online;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.online.QueryOnlineCommand;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
public interface IOnlineService {
    /**
     * 查询信息上线数据
     *
     * @param command
     * @param isPaging
     * @return
     */
    PagingInfo queryOnline(QueryOnlineCommand command, boolean isPaging);
}
