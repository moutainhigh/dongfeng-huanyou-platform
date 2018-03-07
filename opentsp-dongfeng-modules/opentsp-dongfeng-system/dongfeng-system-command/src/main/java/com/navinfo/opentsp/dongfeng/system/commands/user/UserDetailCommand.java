package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by yaocy on 2017/03/14.
 * 查看用户详情
 */
public class UserDetailCommand extends BaseCommand<HttpCommandResultWithData> {

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
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "delUserCommand{" +
                "accountId='" + accountId + '\'' +
                '}';
    }
}
