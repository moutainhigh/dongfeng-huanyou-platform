package com.navinfo.opentsp.dongfeng.common.pojo;

import java.io.Serializable;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-01
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalSubscribeInfoPojo implements Serializable {
    private static final long serialVersionUID = -2238148644695246960L;
    private Long communicationId;
    private Long timestamp;

    public Long getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(Long communicationId) {
        this.communicationId = communicationId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
