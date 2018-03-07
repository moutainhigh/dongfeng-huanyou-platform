package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
public class UpdateBatchDealerAuditStatusCommand extends BaseCommand<CommonResult>{

    /**
     * 审核状态
     */
    @NotNull(message = "auditStatus 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "auditStatus 不能为空", groups = {GroupCommand.class})
    private String auditStatus;
    /**
     * 审核意见
     */
    private String opinion;
    /**
     * 待审核经销商字符串
     */
    @NotNull(message = "待审核信息 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "待审核信息 不能为空", groups = {GroupCommand.class})
    private String dealerAudit;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getDealerAudit() {
        return dealerAudit;
    }

    public void setDealerAudit(String dealerAudit) {
        this.dealerAudit = dealerAudit;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}