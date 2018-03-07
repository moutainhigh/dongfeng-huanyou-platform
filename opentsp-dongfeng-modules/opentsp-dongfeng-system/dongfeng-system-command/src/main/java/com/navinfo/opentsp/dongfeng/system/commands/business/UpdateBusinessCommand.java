package com.navinfo.opentsp.dongfeng.system.commands.business;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class UpdateBusinessCommand extends BaseCommand<CommonResult> {

    /**
     * 客户ID
     */
    private String cid;
    /**
     * 企业许可证号
     */
    private String businessCode;
    /**
     * 所属客户名称
     */
    @NotNull(message = "客户名称 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "客户名称 不能为空", groups = {GroupCommand.class})
    private String businessName;
    /**
     * 所属省市
     */
    @NotNull(message = "所属省市 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "所属省市 不能为空", groups = {GroupCommand.class})
    private String addressCode;
    /**
     * 联系人
     */
    private String linkPerson;
    /**
     * 联系电话
     */
    private String linkTelpone;
    /**
     * 传真
     */
    private String fax;
    /**
     * 详细地址
     */
    private String addressCommon;
    /**
     * 备注
     */
    private String common;
    /**
     * 客户类型 1-企业 2-个人
     */
    @NotNull(message = "客户类型 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "客户类型 不能为空", groups = {GroupCommand.class})
    private String ctype;
    /**
     * 从业资格证号-个人
     */
    private String certificateCode;
    /**
     * 机动车驾驶证号-个人
     */
    private String vehicleLicCode;
    /**
     * 道路运输证号-个人
     */
    private String roadTransLicCode;
    /**
     * 道路运输经营许可证号-企业
     */
    private String organizationCode;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getLinkPerson() {
        return linkPerson;
    }

    public void setLinkPerson(String linkPerson) {
        this.linkPerson = linkPerson;
    }

    public String getLinkTelpone() {
        return linkTelpone;
    }

    public void setLinkTelpone(String linkTelpone) {
        this.linkTelpone = linkTelpone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddressCommon() {
        return addressCommon;
    }

    public void setAddressCommon(String addressCommon) {
        this.addressCommon = addressCommon;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getVehicleLicCode() {
        return vehicleLicCode;
    }

    public void setVehicleLicCode(String vehicleLicCode) {
        this.vehicleLicCode = vehicleLicCode;
    }

    public String getRoadTransLicCode() {
        return roadTransLicCode;
    }

    public void setRoadTransLicCode(String roadTransLicCode) {
        this.roadTransLicCode = roadTransLicCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}