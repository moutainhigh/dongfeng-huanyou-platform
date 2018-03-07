package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryScanCodeCommand;

/**
 * 车辆扫码报表接口
 * @author wt
 * @date 2017-03-24
 * @modify
 * @version 1.0
 */
public interface IScanCodeService {
    /**
     * Query Vehicle Scan Code Infos
     * @param command
     * @return
     */
    HttpCommandResultWithData queryVehicleScanCodeInfos(final QueryScanCodeCommand command, final boolean isQueryAll);
}
