package com.navinfo.opentsp.dongfeng.monitor.service.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.*;

/**
 * 风险防控规则服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public interface IRiskRuleService {
    /**
     * 新增车辆防控规则
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData addRiskRule(AddRiskRuleCommand command);

    /**
     * 删除车辆防控规则
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData deleteRiskRule(DeleteRiskRuleCommand command);

    /**
     * 查询车辆防控规则
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryRiskRuleDetail(QueryRiskRuleDetailCommand command);

    /**
     * 查询车辆防控规则
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData queryRiskRule(QueryRiskRuleCommand command);

    /**
     * 更新车辆防控规则
     *
     * @param command
     * @return
     */
    HttpCommandResultWithData updateRiskRule(UpdateRiskRuleCommand command);
}
