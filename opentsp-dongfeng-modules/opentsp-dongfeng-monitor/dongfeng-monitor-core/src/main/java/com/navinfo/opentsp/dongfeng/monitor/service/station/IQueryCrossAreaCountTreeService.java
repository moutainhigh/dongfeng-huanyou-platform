package com.navinfo.opentsp.dongfeng.monitor.service.station;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.ExportCrossAreaCountTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryCrossAreaCountTreeCommand;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryCrossAreaCountTreeService {
    /**
     * 服务站车次
     * @param crossAreaCountTreeCommand
     * @return
     */
    public HttpCommandResultWithData queryCrossAreaCountTree(QueryCrossAreaCountTreeCommand crossAreaCountTreeCommand);

    /**
     * 服务站车次导出
     * @param fservice
     * @param command
     * @param sourcePath
     */
    public void asyncDonwload(String fservice , ExportCrossAreaCountTreeCommand command , String sourcePath, List<? extends Object> list);

    /**
     * 服务站车次导出
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    public JSONObject download(String fservice , List<? extends Object> list , ExportCrossAreaCountTreeCommand command , String sourcePath);
}
