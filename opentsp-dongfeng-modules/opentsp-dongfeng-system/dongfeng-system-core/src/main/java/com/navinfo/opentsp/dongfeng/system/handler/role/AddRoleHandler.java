package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.role.AddRoleCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/16.
 * 添加角色
 */
@Component
public class AddRoleHandler extends ValidateTokenAndReSetAbstracHandler<AddRoleCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(AddRoleHandler.class);

    @Autowired
    private IRoleService roleService;

    public AddRoleHandler() {
        super(AddRoleCommand.class, CommonResult.class);
    }

    protected AddRoleHandler(Class<AddRoleCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(AddRoleCommand command) {
        return roleService.addRole(command);
    }
}
