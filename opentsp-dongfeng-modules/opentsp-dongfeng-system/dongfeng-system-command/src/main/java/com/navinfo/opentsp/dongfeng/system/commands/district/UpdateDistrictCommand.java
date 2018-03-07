package com.navinfo.opentsp.dongfeng.system.commands.district;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 销售区域修改Command
 *
 * @author Sunyu
 */
public class UpdateDistrictCommand extends BaseCommand<CommonResult> {
    @NotNull(message = "区域ID不能为空", groups = {GroupCommand.class})
    private Long id;
    /**
     * 分区服务编码
     */
    private Integer district;
    /**
     * 分组名称
     */
    @NotNull(message = "区域名称不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "区域名称不能为空白", groups = {GroupCommand.class})
    private String tName;
    /**
     * 上级分组
     */
    private Long parentId;
    /**
     * 所在区域
     */
    private String pName;
    /**
     * 是否是公司
     */
    private Integer tType;
    /**
     * 分组创建时间
     */
    private Long tDate;
    /**
     * 创建用户
     */
    private String taccountName;
    /**
     * 联系人
     */
    private String tlinkMan;
    /**
     * 联系电话
     */
    private String tlinkTel;
    /**
     * 公司名称
     */
    private String companyName;

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTaccountName() {
        return taccountName;
    }

    public void setTaccountName(String taccountName) {
        this.taccountName = taccountName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Long gettDate() {
        return tDate;
    }

    public void settDate(Long tDate) {
        this.tDate = tDate;
    }

    public String getTlinkMan() {
        return tlinkMan;
    }

    public void setTlinkMan(String tlinkMan) {
        this.tlinkMan = tlinkMan;
    }

    public String getTlinkTel() {
        return tlinkTel;
    }

    public void setTlinkTel(String tlinkTel) {
        this.tlinkTel = tlinkTel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}