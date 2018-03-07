package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserImageCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-07-24
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class EditUserImageHandler extends ValidateTokenAndReSetAbstracHandler<EditUserImageCommand, CommonResult> {

    @Autowired
    private IUserService userService;

    public EditUserImageHandler() {
        super(EditUserImageCommand.class, CommonResult.class);
    }

    protected EditUserImageHandler(Class<EditUserImageCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(EditUserImageCommand command) {
        return userService.editUserImage(command);
    }
}
