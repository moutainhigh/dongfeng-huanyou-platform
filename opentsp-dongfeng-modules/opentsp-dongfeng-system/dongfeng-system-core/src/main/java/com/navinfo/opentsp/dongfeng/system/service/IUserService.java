package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.user.*;

/**
 * Created by yaocy on 2017/03/08.
 * 用户相关功能Service
 */

public interface IUserService {
    /**
     * 查询用户
     * @param command
     * @return
     */
    HttpCommandResultWithData queryUserList (final UserListCommand command, final boolean isQueryAll);

    /**
     * 查询用户详情
     * @param command
     * @return
     */
    HttpCommandResultWithData getUserDetail (final UserDetailCommand command);
    /**
     * 添加用户
     * @param command
     * @return
     */
    CommonResult addUser (final AddUserCommand command);

    /**
     * 修改用户
     * @param command
     * @return
     */
    CommonResult editUser (final EditUserCommand command);

    /**
     * 删除用户
     * @param command
     * @return
     */
    CommonResult delUser (final DelUserCommand command);

    /**
     * 修改密码
     * @param command
     * @return
     */
    CommonResult modifyPassWord (final ModifyPassWordCommand command);

    /**
     * 修改用户头像
     * @return
     */
    CommonResult editUserImage(final EditUserImageCommand command);
}
