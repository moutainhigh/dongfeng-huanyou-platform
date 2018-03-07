package com.navinfo.opentsp.dongfeng.monitor.service.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.ExportRiskRegionReportCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.QueryRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.RiskRegionReportCommand;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.RiskRegionDto;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 风险防控区域服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public interface IRiskRegionService {
    /**
     * 新增防控区域
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData addRiskRegion(AddRiskRegionCommand command);


    /**
     * 查询防控区域
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryRiskRegion(QueryRiskRegionCommand command);

    /**
     * 查询防控区域
     *
     * @param regionId 区域ID
     * @return
     */
    RiskRegionDto queryRiskRegionById(String regionId);

    /**
     * 查询风险防控报表
     *
     * @param command
     * @param isPaging 是否分页
     * @return
     */
    PagingInfo queryReportOfRiskRegion(RiskRegionReportCommand command, boolean isPaging);

    /**
     * 异步导出防区报表
     *
     * @param fservice
     * @param command
     * @param sourcePath
     */
    void asyncDownload(String fservice, List<? extends Object> list, ExportRiskRegionReportCommand command, String sourcePath);

    /**
     * 同步导出防区报表
     *
     * @param fservice
     * @param list
     * @param command
     * @param sourcePath
     * @return
     */
    JSONObject download(String fservice, List<? extends Object> list, ExportRiskRegionReportCommand command, String sourcePath);

}
