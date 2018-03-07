package com.navinfo.opentsp.dongfeng.system.commands.role;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by yaocy on 2017/03/17.
 * 查询角色详情
 */
@SuppressWarnings("rawtypes")
public class GetRoleCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "角色ID不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "角色ID不能为空白", groups = {GroupCommand.class})
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}
