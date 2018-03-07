package com.navinfo.opentsp.dongfeng.openapi.commands.bigdata;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/11/28
 */
public class QueryCarOnlineCountsCommand extends BaseOpenApiCommand {
    @Override
    public String toString() {
        return "QueryCarOnlineCountsCommand{}";
    }
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
