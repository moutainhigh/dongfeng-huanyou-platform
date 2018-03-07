package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.role.DelRoleCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/16.
 * 删除角色
 */
@Component
public class DelRoleHandler extends ValidateTokenAndReSetAbstracHandler<DelRoleCommand, CommonResult> {

    protected static final Logger logger = LoggerFactory.getLogger(DelRoleHandler.class);

    @Autowired
    private IRoleService roleService;

    public DelRoleHandler() {
        super(DelRoleCommand.class, CommonResult.class);
    }

    protected DelRoleHandler(Class<DelRoleCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(DelRoleCommand command) {
        return roleService.delRole(command);
    }
}
