package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @author wt
 * @version 1.0
 * @date 2017/11/29
 * @modify
 * @copyright
 */
public class QueryAuditReasonCommand extends BaseReportCommand {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryAuditReasonCommand{" +
                "id=" + id +
                '}';
    }
}
