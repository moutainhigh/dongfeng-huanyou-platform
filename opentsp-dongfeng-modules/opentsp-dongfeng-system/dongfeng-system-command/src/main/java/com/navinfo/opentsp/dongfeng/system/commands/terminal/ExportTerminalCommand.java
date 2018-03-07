package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 终端导出
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-07-24
 * @modify
 * @copyright Navi Tsp
 */
public class ExportTerminalCommand extends SearchTerminalCommand {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return super.getResultType();
    }
}
