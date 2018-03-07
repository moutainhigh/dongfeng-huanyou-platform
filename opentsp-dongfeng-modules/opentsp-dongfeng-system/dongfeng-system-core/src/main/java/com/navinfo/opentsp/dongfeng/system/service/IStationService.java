package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.system.commands.station.*;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 服务站管理接口
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-14
 * @modify
 * @copyright Navi Tsp
 */
public interface IStationService {
    /**
     * 查询服务站
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryStationDetail(QueryStationDetailCommand command);

    /**
     * 搜索服务站
     *
     * @param command
     * @param isPaging 是否分页
     * @return
     */
    PagingInfo queryStation(QueryStationCommand command, boolean isPaging);

    /**
     * 添加服务站
     *
     * @param command
     * @return
     */

    HttpCommandResultWithData addStation(AddStationCommand command) throws Exception;

    /**
     * 删除服务站
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData deleteStation(DeleteStationCommand command) throws Exception;

    /**
     * 更新服务站
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData updateStation(UpdateStationCommand command) throws Exception;


    /**
     * 查询待审核服务站
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryAuditStation(QueryAuditStationCommand command);


    /**
     * 更新审核服务站
     *
     * @param command
     * @return
     */
    void batchUpdateStationAuditStatus(UpdateStationAuditStatusCommand command) throws Exception;

    /**
     * 启用，停用服务站
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData enableOrDisableStation(EnableOrDisableStationCommand command);

    /**
     * 异步导出终端
     *
     * @param fservice
     * @param command
     * @param sourcePath
     */
    void asyncDownload(String fservice, List<? extends Object> list, ExportStationCommand command, String sourcePath);

    /**
     * 同步导出终端
     *
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    JSONObject download(String fservice, List<? extends Object> list, ExportStationCommand command, String sourcePath);

    /**
     * 同步服务站到标签系统
     *
     * @param stationId
     * @param operateType
     * @throws Exception
     */
    void syncServiceStationToTag(String stationId, int operateType) throws Exception;

    /**
     * 同步服务站到tboss系统
     *
     * @param stationId
     * @param operateType
     * @throws Exception
     */
    void syncServiceStationToTBoss(String stationId, int operateType) throws Exception;

}
