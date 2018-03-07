package com.navinfo.opentsp.dongfeng.report.commands.salestate;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class ExportSaleStateCommand extends QuerySaleStateCommand {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}