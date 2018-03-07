package com.navinfo.opentsp.dongfeng.report.service.general;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.general.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.report.commands.general.QueryCarCountCommand;

/**
 * 全国车次统计
 * Created by wenya on 2017/3/28.
 */
public interface IQueryCarCountService {
    /**
     * 全国车次统计
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarCount(QueryCarCountCommand command);
    /**
     * 导出车次统计
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData exportCarCount(ExportCarCountCommand command);

}
