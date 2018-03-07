package com.navinfo.opentsp.dongfeng.report.commands.oil;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author sunlin
 * @version 1.0
 * @date 2018-03-01
 * @modify
 */
public class ExportTireCommand extends QueryTireCommand {
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}
