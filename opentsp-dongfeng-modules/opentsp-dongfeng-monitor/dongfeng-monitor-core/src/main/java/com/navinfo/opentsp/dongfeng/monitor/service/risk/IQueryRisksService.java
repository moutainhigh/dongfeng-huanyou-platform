package com.navinfo.opentsp.dongfeng.monitor.service.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRisksCommand;

/**
 * 防控区域选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public interface IQueryRisksService {
    /**
     * 防控区域选择弹框
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryRisks(QueryRisksCommand command) ;
}
