package com.navinfo.opentsp.dongfeng.monitor.commands.log;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 导出终端日志
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-16
 * @modify
 * @copyright Navi Tsp
 */
public class ExportTerminalLogCommand extends QueryTerminalLogCommand {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}