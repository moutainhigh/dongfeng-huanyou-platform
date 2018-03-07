package com.navinfo.opentsp.dongfeng.monitor.service.instruct;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryLockMenuCommand;

/**
 * @wenya
 * @create 2017-03-23 16:40
 **/
public interface IQueryLockMenuService {
    /**
     * 获取锁车方案中下拉菜单
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryLockMenu(QueryLockMenuCommand command);
}
