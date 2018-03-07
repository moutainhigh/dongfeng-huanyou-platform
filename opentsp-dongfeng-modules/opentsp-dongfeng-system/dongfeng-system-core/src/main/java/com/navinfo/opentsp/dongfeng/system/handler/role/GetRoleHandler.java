package com.navinfo.opentsp.dongfeng.system.handler.role;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.role.GetRoleCommand;
import com.navinfo.opentsp.dongfeng.system.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yaocy on 2017/03/17.
 * 查询角色详情
 */
@SuppressWarnings("rawtypes")
@Component
public class GetRoleHandler extends ValidateTokenAndReSetAbstracHandler<GetRoleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(GetRoleHandler.class);

    @Autowired
    private IRoleService roleService;

    public GetRoleHandler() {
        super(GetRoleCommand.class, HttpCommandResultWithData.class);
    }

    protected GetRoleHandler(Class<GetRoleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(GetRoleCommand command) {
        return roleService.getRole(command);
    }
}
