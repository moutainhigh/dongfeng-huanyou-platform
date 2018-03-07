package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/10.
 * 用户修改
 */
@Component
public class EditUserHandler extends ValidateTokenAndReSetAbstracHandler<EditUserCommand, CommonResult> {

    @Autowired
    private IUserService userService;

    public EditUserHandler() {
        super(EditUserCommand.class, CommonResult.class);
    }

    protected EditUserHandler(Class<EditUserCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(EditUserCommand command) {
        return userService.editUser(command);
    }
}
