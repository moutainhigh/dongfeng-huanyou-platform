package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station;

import com.navinfo.opentsp.dongfeng.common.validation.NotEqualZero;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-26
 * @modify
 * @copyright Navi Tsp
 */
public class StationAuditIndto {
    /**
     * 审核ID
     */
    @NotNull(message = "auditId 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "auditId 不能为空", groups = {GroupCommand.class})
    private String auditId;
    /**
     * 审核状态
     */
    @NotNull(message = "auditStatus 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "auditStatus 不能为空", groups = {GroupCommand.class})
    private String auditStatus;
    /**
     * 审核意见
     */
    private String opinion;
    /**
     * 省编码
     */
    @NotNull(message = "省份编码 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "省份编码 不能为空", groups = {GroupCommand.class})
    @NotEqualZero(message = "省份编码 不能为0", groups = {GroupCommand.class})
    private String province;
    /**
     * 城市编码
     */
    @NotNull(message = "城市编码 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "城市编码 不能为空", groups = {GroupCommand.class})
    @NotEqualZero(message = "城市编码 不能为0", groups = {GroupCommand.class})
    private String city;

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
}
