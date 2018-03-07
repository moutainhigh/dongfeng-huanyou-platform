package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by yaocy on 2017/03/10.
 * 删除用户
 */
public class DelUserCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "账号ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号ID不能为空白", groups = {GroupCommand.class})
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    @Override
    public String toString() {
        return "DelUserCommand{" +
                "accountId='" + accountId + '\'' +
                '}';
    }
}
