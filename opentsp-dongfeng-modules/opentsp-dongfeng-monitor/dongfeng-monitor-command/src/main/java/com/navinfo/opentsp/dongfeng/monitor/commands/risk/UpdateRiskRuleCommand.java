package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 更新车辆风险防控规则
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class UpdateRiskRuleCommand extends AddRiskRuleCommand {
    @NotNull(message = "车辆防控规则ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "车辆防控规则ID 不能为NULL", groups = {GroupCommand.class})
    private String ruleId;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}