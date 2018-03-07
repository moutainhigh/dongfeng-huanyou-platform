package com.navinfo.opentsp.dongfeng.system.commands.termodel;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * 查询所有终端型号
 * @author: zhangyue
 * @version: 1.0
 * @since: 2017-03-09
 **/
public class ListTerminalModelCommand extends BaseCommand<HttpCommandResultWithData> {

    private String tmName;

    private String tmFactoryName;

    private String tmFactoryNum;
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    public String getTmFactoryName() {
        return tmFactoryName;
    }

    public void setTmFactoryName(String tmFactoryName) {
        this.tmFactoryName = tmFactoryName;
    }

    public String getTmFactoryNum() {
        return tmFactoryNum;
    }

    public void setTmFactoryNum(String tmFactoryNum) {
        this.tmFactoryNum = tmFactoryNum;
    }
}
