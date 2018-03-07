package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

/**
 * 终端设置选项目录
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-05-02
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalSettingMenuOutdto {
    private String id;
    private String pid;
    private String name;
    private Boolean open;
    private Boolean isParent;
    private Boolean checked;

    public TerminalSettingMenuOutdto() {
        this.isParent = false;
        this.open = false;
        this.checked = false;
    }

    public TerminalSettingMenuOutdto(String id, String name) {
        this.isParent = true;
        this.id = id;
        this.name = name;
        this.open = true;
        this.checked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
