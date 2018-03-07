package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.ReportProperty;

import java.util.List;

/**
 * 报表导出通用服务工具类
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */
public interface IReportService {

    /**
     *
     * @param list
     * @param command
     * @param config
     * @param property
     * @return
     */
    HttpCommandResultWithData downLoad(final List<? extends Object> list, final BaseReportCommand command, final ReportConfig config, final ReportProperty property);
}
