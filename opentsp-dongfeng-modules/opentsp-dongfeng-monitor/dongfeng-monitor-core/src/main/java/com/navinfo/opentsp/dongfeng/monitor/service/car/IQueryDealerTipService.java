package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealerTipCommand;

/**
 * 经销商Tip框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/5/26
 */
public interface IQueryDealerTipService {
    /**
     * 经销商Tip框
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryDealerTip(QueryDealerTipCommand command) ;
}
