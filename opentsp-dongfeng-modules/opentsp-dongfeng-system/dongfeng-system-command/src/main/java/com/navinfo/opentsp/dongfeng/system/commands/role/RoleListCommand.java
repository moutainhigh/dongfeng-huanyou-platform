package com.navinfo.opentsp.dongfeng.system.commands.role;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Created by yaocy on 2017/03/15.
 * 查询角色列表
 */
@SuppressWarnings("rawtypes")
public class RoleListCommand extends BaseCommand<HttpCommandResultWithData> {

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
