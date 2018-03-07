package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryLocInfosCommand;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/17
 */
public interface IQueryLocInfosService {
    /**
     * 获取车辆位置列表
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryLocInfos(QueryLocInfosCommand command) ;
}
