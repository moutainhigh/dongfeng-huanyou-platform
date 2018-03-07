package com.navinfo.opentsp.dongfeng.report.commands.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public class ExportRearInstallVehicleDetailsCommand extends QueryRearInstallVehicleDetailsCommand {

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}