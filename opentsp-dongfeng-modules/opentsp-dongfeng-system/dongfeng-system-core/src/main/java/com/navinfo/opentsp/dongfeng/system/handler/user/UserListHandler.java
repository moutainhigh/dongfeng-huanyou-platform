package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserListCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/08.
 * 用户查询
 */
@Component
public class UserListHandler extends ValidateTokenAndReSetAbstracHandler<UserListCommand, HttpCommandResultWithData> {

    @Autowired
    private IUserService userService;

    public UserListHandler() {
        super(UserListCommand.class, HttpCommandResultWithData.class);
    }

    protected UserListHandler(Class<UserListCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(UserListCommand command) {
        return userService.queryUserList(command, false);
    }
}
