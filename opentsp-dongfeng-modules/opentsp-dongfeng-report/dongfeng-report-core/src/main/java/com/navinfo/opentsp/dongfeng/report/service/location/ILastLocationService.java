package com.navinfo.opentsp.dongfeng.report.service.location;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.location.QueryLastLocationCommand;

/**
 * 末次位置报表统计
 * @author wt
 * @version 1.0
 * @date 2017/12/18
 * @modify
 * @copyright
 */
public interface ILastLocationService {

    /**
     * 末次位置查询
     * @param command  the query command
     * @param isQueryAll If query all is ture, or false
     * @return
     */
    HttpCommandResultWithData query(final QueryLastLocationCommand command, final boolean isQueryAll);
}
