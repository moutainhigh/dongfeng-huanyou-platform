package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-06
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTerminalDetailCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "终端  不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "终端 不能为空", groups = {GroupCommand.class})
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