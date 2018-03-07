package com.navinfo.opentsp.dongfeng.monitor.service.car;/**
 * Created by Administrator on 2017/3/21.
 */

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryRemoteInfoCommand;

/**
 * 远程诊断
 *
 * @wenya
 * @create 2017-03-21 17:59
 **/
public interface IQueryRemoteInfoService {
    public HttpCommandResultWithData queryRemoteInfo(QueryRemoteInfoCommand command);
}
