package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


/**
 * Created by yaocy on 2017/03/15.
 * 角色权限树dto
 */
public class PermissionTreeOutdto {

    private Integer id;

    private String name;

    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
