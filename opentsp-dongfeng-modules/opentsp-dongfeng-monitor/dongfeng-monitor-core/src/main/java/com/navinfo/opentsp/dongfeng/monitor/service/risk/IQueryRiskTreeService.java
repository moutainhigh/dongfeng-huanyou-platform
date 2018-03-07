package com.navinfo.opentsp.dongfeng.monitor.service.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskTreeCommand;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
public interface IQueryRiskTreeService {
    /**
     * 获取风险防控搜索树
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryRiskTree(QueryRiskTreeCommand command);
}
