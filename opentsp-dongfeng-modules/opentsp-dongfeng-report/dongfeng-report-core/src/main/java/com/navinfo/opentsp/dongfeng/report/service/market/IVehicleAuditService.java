package com.navinfo.opentsp.dongfeng.report.service.market;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryAuditReasonCommand;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryVehicleAuditCommand;
import com.navinfo.opentsp.dongfeng.report.commands.market.VehicleAuditCommand;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public interface IVehicleAuditService {

    /**
     * 查询审核车辆
     * @param command
     * @param isQueryAll 导出标识
     * @return
     */
    HttpCommandResultWithData query(final QueryVehicleAuditCommand command, final boolean isQueryAll);

    /**
     * 查询审核车辆审批原因
     * @param command
     * @return
     */
    HttpCommandResultWithData queryApplyReason(final QueryAuditReasonCommand command);

    /**
     * 批量审核
     * @param command
     * @return
     */
    HttpCommandResultWithData audit(final VehicleAuditCommand command) throws Exception;
}
