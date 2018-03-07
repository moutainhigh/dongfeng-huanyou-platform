package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.SyncScanCodeCommand;

/**
 * ISyncScanCodeService
 *
 * @author wt
 * @version 1.0
 * @date 2017/8/23
 * @modify
 * @copyright
 */
public interface ISyncScanCodeService {
    HttpCommandResultWithData sync2Tds(final SyncScanCodeCommand command);
}
