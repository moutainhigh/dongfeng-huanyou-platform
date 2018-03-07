package com.navinfo.opentsp.dongfeng.system.commands.role;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by yaocy on 2017/03/13.
 * 根据类型查询角色
 */
@SuppressWarnings("rawtypes")
public class RoleByTypeCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "角色类型不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "角色类型不能为空白", groups = {GroupCommand.class})
    @Pattern(regexp = RegexpUtils.POSITIVE_INTEGER_REGEXP, message = "字典类型只能为整数的数字", groups = {GroupCommand.class})
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}
