package com.navinfo.opentsp.dongfeng.report.commands.market;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.report.dto.market.VehicleAuditId;

import java.util.ArrayList;

/**
 * 批量审核
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
public class VehicleAuditCommand extends BaseReportCommand {

    /**
     * ids: [{"id":AAAA},{"id":BBBB},{"id":CCCC}]
     */
    @Converter(target = "auditBean", type = ParameterType.LIST)
    private String ids;

    private ArrayList<VehicleAuditId> auditBean;

    private Integer status;

    private String reason;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public ArrayList<VehicleAuditId> getAuditBean() {
        return auditBean;
    }

    public void setAuditBean(ArrayList<VehicleAuditId> auditBean) {
        this.auditBean = auditBean;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "VehicleAuditCommand{" +
                "ids='" + ids + '\'' +
                ", auditBean=" + auditBean +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}
