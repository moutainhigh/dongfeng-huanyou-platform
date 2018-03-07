package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 修改密码
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-04
 * @modify
 * @copyright Navi Tsp
 */
public class ModifyPassWordCommand extends BaseCommand<CommonResult> {
    @NotNull(message = "密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "密码不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "密码长度为8到16位，必须包含大、小写字母、数字", groups = {GroupCommand.class})
    private String accountPwd;

    @NotNull(message = "新密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "新密码不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "密码长度为8到16位，必须包含大、小写字母、数字", groups = {GroupCommand.class})
    private String newPwd;

    @NotNull(message = "确认密码不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "确认密码不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.PASSWORD_REGEXP, message = "密码长度为8到16位，必须包含大、小写字母、数字", groups = {GroupCommand.class})
    private String confirmPwd;

    @AssertTrue(message = "密码和确认密码不一致", groups = {GroupCommand.class})
    private boolean isMather() {
        return newPwd.equals(confirmPwd) ? true : false;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    @Override
    public String toString() {
        return "ModifyPassWordCommand{" +
                " accountPwd='" + accountPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                '}';
    }
}