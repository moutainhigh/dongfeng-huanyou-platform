package com.navinfo.opentsp.dongfeng.system.commands.role;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by yaocy on 2017/03/15.
 * 添加角色
 */
public class AddRoleCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "角色名称不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "角色名称不能为空白", groups = {GroupCommand.class})
    private String name;

    @NotNull(message = "角色类型不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "角色类型不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.POSITIVE_INTEGER_REGEXP, message = "角色类型只能为整数的数字", groups = {GroupCommand.class})
    private String roleType;
    
    @NotNull(message = "权限不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "权限不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.NUMBER_COMMA_REGEXP, message = "权限只能为整数和逗号组成的字符串", groups = {GroupCommand.class})
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

}
