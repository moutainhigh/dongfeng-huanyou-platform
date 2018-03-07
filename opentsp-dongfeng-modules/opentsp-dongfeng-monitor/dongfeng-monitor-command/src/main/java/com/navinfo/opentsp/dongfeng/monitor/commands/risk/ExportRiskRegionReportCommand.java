package com.navinfo.opentsp.dongfeng.monitor.commands.risk;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
public class ExportRiskRegionReportCommand extends RiskRegionReportCommand {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}