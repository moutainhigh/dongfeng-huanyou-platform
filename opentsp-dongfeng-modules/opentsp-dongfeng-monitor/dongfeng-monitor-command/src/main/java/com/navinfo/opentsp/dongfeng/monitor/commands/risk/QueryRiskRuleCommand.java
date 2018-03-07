package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
public class QueryRiskRuleCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "风控区ID 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "风控区ID 不能为NULL", groups = {GroupCommand.class})
    private String regionId;//区域ID

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}