package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryDealersCommand;

/**
 * 经销商选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public interface IQueryDealersService {
    /**
     * 车辆选择弹框
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryDealers(QueryDealersCommand command) ;
}
