package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.ImportScanCodeCommand;

/**
 * 空入车辆批量导入
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-07
 * @modify
 * @copyright Navi Tsp
 */
public interface ImportScanCodeService {
    HttpCommandResultWithData importScanCode(final ImportScanCodeCommand command) ;
}
