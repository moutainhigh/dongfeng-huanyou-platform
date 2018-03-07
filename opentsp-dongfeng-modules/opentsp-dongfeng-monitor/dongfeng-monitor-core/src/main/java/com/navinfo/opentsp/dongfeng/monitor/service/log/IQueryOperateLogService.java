package com.navinfo.opentsp.dongfeng.monitor.service.log;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryOperateLogCommand;

/**
 * 操作日志服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
public interface IQueryOperateLogService {

    /**
     * 查询操作日志
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData query(QueryOperateLogCommand command);
}
