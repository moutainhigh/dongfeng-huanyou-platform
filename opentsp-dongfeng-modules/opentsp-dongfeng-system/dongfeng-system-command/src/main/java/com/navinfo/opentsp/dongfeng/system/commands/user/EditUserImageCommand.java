package com.navinfo.opentsp.dongfeng.system.commands.user;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-24
 * @modify
 * @copyright Navi Tsp
 */
public class EditUserImageCommand extends BaseCommand<CommonResult> {

    @NotNull(message = "账号ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "账号ID不能为空白", groups = {GroupCommand.class})
    private String accountId;

    @NotNull(message = "用户头像不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "用户头像不能为空白", groups = {GroupCommand.class})
    private String image;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }
}
