package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-20
 **/
public class CanBusParam {
    private Integer pid;
    private String canId;
    private Integer begin;
    private Integer offset;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
