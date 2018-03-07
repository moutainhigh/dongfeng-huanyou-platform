package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryRearInstallVehicleDetailsCommand;

/**
 * 后装统计报表
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-05
 * @modify
 * @copyright Navi Tsp
 */
public interface IQueryRearInstallVehicleInfoService {

    /**
     * Query statistics for rear install vehicels
     * @param command
     * @return
     */
    HttpCommandResultWithData queryVehicleCounts(final QueryRearInstallVehicleCommand command);

    /**
     * Query details for rear install vehicels
     * @param command
     * @param isQueryAll
     * @return
     */
    HttpCommandResultWithData queryVehicleDetails(final QueryRearInstallVehicleDetailsCommand command, final boolean isQueryAll);
}
