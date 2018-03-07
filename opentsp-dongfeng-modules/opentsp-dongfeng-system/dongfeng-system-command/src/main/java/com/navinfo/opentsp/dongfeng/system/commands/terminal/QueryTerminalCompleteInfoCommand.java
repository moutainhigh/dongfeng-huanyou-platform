package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 查询终端完整信息，包括终端绑定车辆，经销商，客户信息
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-16
 * @modify
 * @copyright Navi Tsp
 */
public class QueryTerminalCompleteInfoCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "id  不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "id 不能为空", groups = {GroupCommand.class})
    private String id;
    private String terminalId; //终端ID

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
}