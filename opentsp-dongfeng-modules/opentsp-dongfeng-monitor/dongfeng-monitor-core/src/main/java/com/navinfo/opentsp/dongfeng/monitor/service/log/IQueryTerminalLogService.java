package com.navinfo.opentsp.dongfeng.monitor.service.log;


import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.ExportTerminalLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryTerminalLogCommand;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 终端管理服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-10
 * @modify
 * @copyright Navi Tsp
 */
public interface IQueryTerminalLogService {
    /**
     * 查询终端日志
     *
     * @param command
     * @param paging  是否分页
     * @return
     */
    PagingInfo queryLogOfTerminal(QueryTerminalLogCommand command, boolean paging);

    /**
     * 异步导出终端日志
     *
     * @param fservice
     * @param command
     * @param sourcePath
     */
    void asyncDownload(String fservice, List<? extends Object> list, ExportTerminalLogCommand command, String sourcePath);

    /**
     * 同步导出终端日志
     *
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    JSONObject download(String fservice, List<? extends Object> list, ExportTerminalLogCommand command, String sourcePath);
}
