package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.user.AddUserCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/09.
 * 用户添加
 */
@Component
public class AddUserHandler extends ValidateTokenAndReSetAbstracHandler<AddUserCommand, CommonResult> {

    @Autowired
    private IUserService userService;

    public AddUserHandler() {
        super(AddUserCommand.class, CommonResult.class);
    }

    protected AddUserHandler(Class<AddUserCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(AddUserCommand command) {
        return userService.addUser(command);
    }
}
