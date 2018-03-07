package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 更新终端指令
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-03-08
 **/
public class UpdateTerminalCommand extends AddTerminalCommand {
    @NotNull(message = "id 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "id 不能为空", groups = {GroupCommand.class})
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

    @Override
    public String toString() {
        return "UpdateTerminalCommand{" +
                "id='" + id + '\'' +
                super.toString() +
                '}';
    }
}
