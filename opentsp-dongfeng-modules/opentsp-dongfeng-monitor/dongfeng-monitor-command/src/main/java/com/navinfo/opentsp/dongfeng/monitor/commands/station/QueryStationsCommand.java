package com.navinfo.opentsp.dongfeng.monitor.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 服务站选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryStationsCommand extends BaseCommand<HttpCommandResultWithData> {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return  HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationsCommand{}";
    }
}
