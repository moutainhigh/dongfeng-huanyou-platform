package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.user.DelUserCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/10.
 * 删除用户
 */
@Component
public class DelUserHandler extends ValidateTokenAndReSetAbstracHandler<DelUserCommand, CommonResult> {

    @Autowired
    private IUserService userService;

    public DelUserHandler() {
        super(DelUserCommand.class, CommonResult.class);
    }

    protected DelUserHandler(Class<DelUserCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(DelUserCommand command) {
        return userService.delUser(command);
    }
}
