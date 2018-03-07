package com.navinfo.opentsp.dongfeng.system.handler.user;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.system.commands.user.ModifyPassWordCommand;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Modify User PassWord
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-04
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ModifyPassWordHandler extends ValidateTokenAndReSetAbstracHandler<ModifyPassWordCommand, CommonResult> {

    @Autowired
    private IUserService userService;

    public ModifyPassWordHandler() {
        super(ModifyPassWordCommand.class, CommonResult.class);
    }

    protected ModifyPassWordHandler(Class<ModifyPassWordCommand> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected CommonResult businessHandle(ModifyPassWordCommand command) {
        return userService.modifyPassWord(command);
    }
}
