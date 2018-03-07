package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 删除终端指令
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-03-08
 **/
public class DeleteTerminalCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "终端不存在", groups = {GroupCommand.class})
    @NotBlank(message = "终端不存在", groups = {GroupCommand.class})
    private String id;
    private String terminalId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "DeleteTerminalCommand{" +
                "id='" + id + '\'' +
                ", " + super.toString() +
                '}';
    }
}
