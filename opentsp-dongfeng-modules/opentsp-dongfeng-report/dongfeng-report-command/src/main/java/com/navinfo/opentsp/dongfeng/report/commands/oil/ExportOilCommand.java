package com.navinfo.opentsp.dongfeng.report.commands.oil;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-01
 * @modify
 * @copyright Navi Tsp
 */
public class ExportOilCommand extends QueryOilCommand {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}