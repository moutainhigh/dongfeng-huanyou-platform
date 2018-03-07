package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserDetailCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/14.
 * 查询用户详情
 */
@Component
public class UserDetailHandler extends ValidateTokenAndReSetAbstracHandler<UserDetailCommand, HttpCommandResultWithData> {

    @Autowired
    private IUserService userService;

    public UserDetailHandler() {
        super(UserDetailCommand.class, HttpCommandResultWithData.class);
    }

    protected UserDetailHandler(Class<UserDetailCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(UserDetailCommand command) {
        return userService.getUserDetail(command);
    }
}
