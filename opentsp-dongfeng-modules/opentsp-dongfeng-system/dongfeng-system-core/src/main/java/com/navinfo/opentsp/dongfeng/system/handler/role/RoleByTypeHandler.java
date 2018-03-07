package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.role.RoleByTypeCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/13.
 * 根据类型查询角色
 */
@SuppressWarnings("rawtypes")
@Component
public class RoleByTypeHandler extends ValidateTokenAndReSetAbstracHandler<RoleByTypeCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(RoleByTypeHandler.class);

    @Autowired
    private IRoleService roleService;

    public RoleByTypeHandler() {
        super(RoleByTypeCommand.class, HttpCommandResultWithData.class);
    }

    protected RoleByTypeHandler(Class<RoleByTypeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(RoleByTypeCommand command) {
        return roleService.queryRoleByType(command);
    }
}
