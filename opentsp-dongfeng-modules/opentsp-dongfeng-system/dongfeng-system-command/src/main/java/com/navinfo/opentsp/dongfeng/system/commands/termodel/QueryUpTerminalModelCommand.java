package com.navinfo.opentsp.dongfeng.system.commands.termodel;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 删除终端指令
 *
 * @author: zhangyue
 * @version: 1.0
 * @since: 2017-03-09
 **/
public class QueryUpTerminalModelCommand extends BaseCommand<HttpCommandResultWithData> {

    @NotNull(message = "终端id 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "终端id 不能为空", groups = {GroupCommand.class})
        private String terminalId;

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
