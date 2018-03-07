package com.navinfo.opentsp.dongfeng.report.service.salestate;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.salestate.QuerySaleStateCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.salestate.QuerySaleStatePojo;

import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */

public interface IQuerySaleStateService {
    /**
     * 销售状态报表
     * 分页
     * @param command
     * @return
     */
    public HttpCommandResultWithData querySaleState(QuerySaleStateCommand command);
    /**
     * 销售状态报表导出
     * 分页
     * @param command
     * @return
     */
    public List<QuerySaleStatePojo> querySaleStateForExport(QuerySaleStateCommand command);
    /**
     * 销售状态报表导出
     * 全部
     * @param command
     * @return
     */
    public List<QuerySaleStatePojo> queryAllSaleStateForExport(QuerySaleStateCommand command);

    String checkTimeValid(QuerySaleStateCommand command);
}
