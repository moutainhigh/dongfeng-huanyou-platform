package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.role.RoleListCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/15.
 * 查询角色列表
 */
@SuppressWarnings("rawtypes")
@Component
public class RoleListHandler extends ValidateTokenAndReSetAbstracHandler<RoleListCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(RoleListHandler.class);

    @Autowired
    private IRoleService roleService;

    public RoleListHandler() {
        super(RoleListCommand.class, HttpCommandResultWithData.class);
    }

    protected RoleListHandler(Class<RoleListCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(RoleListCommand command) {
        return roleService.queryRoleList(command);
    }
}
