package com.navinfo.opentsp.dongfeng.report.service.disklibrary;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDealerTipCommand;
import com.navinfo.opentsp.dongfeng.report.commands.disklibrary.QueryDisklibraryCommand;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-12-19
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryDisklibraryService {

    /**
     * 查询盘库数据
     *
     * @param command
     * @param isPaging 是否分页
     * @return
     */
    PagingInfo queryDisklibrary(QueryDisklibraryCommand command, boolean isPaging);
    /**
     * 经销商tip
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryDealerTip(QueryDealerTipCommand command);
}
