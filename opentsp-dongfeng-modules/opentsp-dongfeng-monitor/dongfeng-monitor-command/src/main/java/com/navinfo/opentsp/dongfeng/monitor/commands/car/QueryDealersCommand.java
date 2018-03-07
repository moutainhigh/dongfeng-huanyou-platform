package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 经销商选择弹框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/24
 */
public class QueryDealersCommand extends BaseCommand<HttpCommandResultWithData> {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return  HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryDealersCommand{}";
    }
}
