package com.navinfo.opentsp.dongfeng.report.commands.disklibrary;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */
public class ExportDisklibraryCommand extends QueryDisklibraryCommand {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}