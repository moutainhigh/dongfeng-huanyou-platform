package com.navinfo.opentsp.dongfeng.report.service.oil;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilChartCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryOilCommand;
import com.navinfo.opentsp.dongfeng.report.commands.oil.QueryTireCommand;

/**
 * 油耗变化统计分析服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-30
 * @modify
 * @copyright Navi Tsp
 */
public interface IOilService {

    /**
     * 查询油耗
     *
     * @param command
     * @param isPaging 是否分页  true 分页  false 不分页查询全部
     * @return
     */
    PagingInfo queryOil(QueryOilCommand command, boolean isPaging);

    /**
     * 油量变化分析统计图表
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData oilChart(QueryOilChartCommand command);

    /**
     * 查询轮胎信息
     *
     * @param command
     * @param isPaging 是否分页  true 分页  false 不分页查询全部
     * @return
     */
    PagingInfo queryTireInfo(QueryTireCommand command, boolean isPaging);

}
