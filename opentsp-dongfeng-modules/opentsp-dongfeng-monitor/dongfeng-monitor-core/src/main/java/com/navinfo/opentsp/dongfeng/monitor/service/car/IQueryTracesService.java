package com.navinfo.opentsp.dongfeng.monitor.service.car;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportTimelyDataCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportTracesCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryTracesCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTracesPojo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-11
 * @modify
 * @copyright Navi Tsp
 */

public interface IQueryTracesService {
    /**
     * 轨迹回放查询轨迹点
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryTrace(QueryTracesCommand command);
    /**
     * 轨迹回放查询轨迹点FOR导出
     * @param command
     * @return
     */
    public List<QueryTracesPojo> queryTraceList(QueryTracesCommand command);

    /**
     * 轨迹导出
     * @param fservice
     * @param command
     * @param sourcePath
     */
    public void asyncDonwload(String fservice , ExportTracesCommand command , String sourcePath,List<? extends Object> list);

    /**
     * 轨迹导出
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    public JSONObject download(String fservice , List<? extends Object> list , ExportTracesCommand command , String sourcePath);
    /**
     * 查询实时数据FOR导出
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryTimelyDataList(ExportTimelyDataCommand command);
}
