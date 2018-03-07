package com.navinfo.opentsp.dongfeng.report.commands.online;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-05
 * @modify
 * @copyright Navi Tsp
 */
public class ExportOnlineCommand extends QueryOnlineCommand {
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}