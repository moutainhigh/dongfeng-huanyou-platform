package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.role.RolePermissionCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/15.
 * 查询角色权限树
 */
@SuppressWarnings("rawtypes")
@Component
public class RolePermissionHandler extends ValidateTokenAndReSetAbstracHandler<RolePermissionCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(RolePermissionHandler.class);

    @Autowired
    private IRoleService roleService;

    public RolePermissionHandler() {
        super(RolePermissionCommand.class, HttpCommandResultWithData.class);
    }

    protected RolePermissionHandler(Class<RolePermissionCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(RolePermissionCommand command) {
        return roleService.queryRolePermission(command);
    }
}
