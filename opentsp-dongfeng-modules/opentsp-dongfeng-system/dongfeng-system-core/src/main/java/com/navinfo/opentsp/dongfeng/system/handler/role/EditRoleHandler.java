package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.role.EditRoleCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/16.
 * 修改角色
 */
@Component
public class EditRoleHandler extends ValidateTokenAndReSetAbstracHandler<EditRoleCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(EditRoleHandler.class);

    @Autowired
    private IRoleService roleService;

    public EditRoleHandler() {
        super(EditRoleCommand.class, CommonResult.class);
    }

    protected EditRoleHandler(Class<EditRoleCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(EditRoleCommand command) {
        return roleService.editRole(command);
    }
}
