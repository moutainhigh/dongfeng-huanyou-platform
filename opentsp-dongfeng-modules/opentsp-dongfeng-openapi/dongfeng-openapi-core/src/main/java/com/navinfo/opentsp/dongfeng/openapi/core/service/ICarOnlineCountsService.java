package com.navinfo.opentsp.dongfeng.openapi.core.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineCountsCommand;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/11/28
 */
public interface ICarOnlineCountsService {
    HttpCommandResultWithData queryCarOnlineCounts(QueryCarOnlineCountsCommand command);
}
