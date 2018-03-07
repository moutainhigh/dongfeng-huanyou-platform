package com.navinfo.opentsp.dongfeng.report.service.saleremoval;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.saleremoval.QuerySaleRemovalCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.saleremoval.QuerySaleRemovalPojo;

import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */

public interface IQuerySaleRemovalService {
    /**
     * 出库销售状态报表
     * 分页
     * @param command
     * @return
     */
    public HttpCommandResultWithData querySaleRemoval(QuerySaleRemovalCommand command);
    /**
     * 出库销售状态报表导出
     * 分页
     * @param command
     * @return
     */
    public List<QuerySaleRemovalPojo> querySaleRemovalForExport(QuerySaleRemovalCommand command);
    /**
     * 出库销售状态报表导出
     * 全部
     * @param command
     * @return
     */
    public List<QuerySaleRemovalPojo> queryAllSaleRemovalForExport(QuerySaleRemovalCommand command);

    String checkTimeValid(QuerySaleRemovalCommand command);
}
