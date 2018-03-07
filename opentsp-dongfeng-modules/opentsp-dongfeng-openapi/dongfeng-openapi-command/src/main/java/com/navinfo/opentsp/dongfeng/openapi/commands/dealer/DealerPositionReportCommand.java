package com.navinfo.opentsp.dongfeng.openapi.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.base.BaseOpenApiCommand;
import com.navinfo.opentsp.dongfeng.openapi.dto.station.DealerPositionReportInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */

public class DealerPositionReportCommand extends BaseOpenApiCommand {

    @NotNull(message = "参数 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "参数 不能为空", groups = {GroupCommand.class})
    @Converter(target = "dealerPositionReportInfo")
    private String param;
    DealerPositionReportInfo dealerPositionReportInfo;
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public DealerPositionReportInfo getDealerPositionReportInfo() {
        return dealerPositionReportInfo;
    }

    public void setDealerPositionReportInfo(DealerPositionReportInfo dealerPositionReportInfo) {
        this.dealerPositionReportInfo = dealerPositionReportInfo;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}