package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-02
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTerminalSettingParamCommand extends BaseCommand<HttpCommandResultWithData> {
    /**
     * 终端自增主键ID
     */
    @NotNull(message = "终端主键ID  不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "终端主键ID 不能为空", groups = {GroupCommand.class})
    private String tid;
    @NotNull(message = "终端类型  不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "终端类型 不能为空", groups = {GroupCommand.class})
    private String type;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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