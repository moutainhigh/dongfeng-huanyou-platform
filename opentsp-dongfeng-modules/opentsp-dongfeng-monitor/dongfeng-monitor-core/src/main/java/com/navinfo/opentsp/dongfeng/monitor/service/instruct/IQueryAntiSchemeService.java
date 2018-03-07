package com.navinfo.opentsp.dongfeng.monitor.service.instruct;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.instruct.QueryAntiSchemeCommand;
/**
 * @wenya
 * @create 2017-03-23 9:40
 **/
public interface IQueryAntiSchemeService {
    /**
     * 获取防拆方案中下拉菜单
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryAntiScheme(QueryAntiSchemeCommand command);
}
