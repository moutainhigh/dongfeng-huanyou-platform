package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;


import java.math.BigInteger;
import java.util.List;

/**
 * Created by yaocy on 2016/03/17.
 */
public class RoleDetailOutdto {

    private BigInteger id;

    private String name;

    private String roleType;

    private List<PermissionTreeOutdto> permissions;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<PermissionTreeOutdto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionTreeOutdto> permissions) {
        this.permissions = permissions;
    }
}
