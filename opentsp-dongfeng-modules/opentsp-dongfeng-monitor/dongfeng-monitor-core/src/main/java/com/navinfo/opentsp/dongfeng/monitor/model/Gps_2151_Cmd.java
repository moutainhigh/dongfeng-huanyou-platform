package com.navinfo.opentsp.dongfeng.monitor.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 调度短信
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2151_Cmd extends BaseTcpCommand {
    private static final long serialVersionUID = 6200071952214559746L;
    private Integer type;//显示类型
    private String content;//消息内容
    private String communicationIds;//通讯号

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public String getCommunicationIds() {
        return communicationIds;
    }

    public void setCommunicationIds(String communicationIds) {
        this.communicationIds = communicationIds;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
