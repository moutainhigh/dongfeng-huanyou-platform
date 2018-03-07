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
public class UpdateDealerAuditStatusCommand extends BaseCommand<CommonResult>{

    /**
     * 经销商ID
     */
    @NotNull(message = "tid 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "tid 不能为空", groups = {GroupCommand.class})
    private String tid;
    /**
     * 经销商类型（一级OR二级）
     */
    @NotNull(message = "type 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "type 不能为空", groups = {GroupCommand.class})
    private String type;
    /**
     * 审核ID
     */
    @NotNull(message = "auditId 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "auditId 不能为空", groups = {GroupCommand.class})
    private String auditId;
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
     * 经销商名称
     */
    private String dealerCode;

    /**
     * 省编码
     */
    private String province;
    /**
     * 城市编码
     */
    private String city;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

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

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}