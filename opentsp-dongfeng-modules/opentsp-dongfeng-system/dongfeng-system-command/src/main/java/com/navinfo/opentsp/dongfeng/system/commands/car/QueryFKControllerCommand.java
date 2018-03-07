package com.navinfo.opentsp.dongfeng.system.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;

/**
 * Created by lihangtao on 2017/3/9.
 * FK控制器查询
 */
public class QueryFKControllerCommand extends BaseCommand<HttpCommandResultWithData> {

    private String dealerId;

    @Converter(target="terminalBea")
    private String terminal;

    private TerminalIndto terminalBea;

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    public String getDealerId() { return dealerId; }

    public void setDealerId(String dealerId) { this.dealerId = dealerId; }

    public String getTerminal() { return terminal; }

    public void setTerminal(String terminal) { this.terminal = terminal; }

    public TerminalIndto getTerminalBea() { return terminalBea; }
}
