package com.navinfo.opentsp.dongfeng.system.commands.district;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Created by yaocy on 2017/03/13.
 * 分组查询
 */
public class GetTeamCommand extends BaseCommand<HttpCommandResultWithData> {
    private String name;

    private String type;

    private String pid;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "GetTeamCommand{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
