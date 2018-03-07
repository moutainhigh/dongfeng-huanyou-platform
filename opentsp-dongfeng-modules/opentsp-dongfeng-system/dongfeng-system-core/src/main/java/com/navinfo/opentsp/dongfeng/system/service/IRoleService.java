package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.role.*;

/**
 * Created by yaocy on 2017/03/13.
 * 角色Service接口
 */
@SuppressWarnings("rawtypes")
public interface IRoleService {
    /**
     * 查询
     * @param command
     * @return
     */
	public HttpCommandResultWithData queryRoleList(RoleListCommand command);

    public HttpCommandResultWithData getRole(GetRoleCommand command);
    /**
     * 添加
     * @param command
     * @return
     */
    public CommonResult addRole(AddRoleCommand command);

    /**
     * 修改
     * @param command
     * @return
     */
    public CommonResult editRole(EditRoleCommand command);

    /**
     * 删除
     * @param command
     * @return
     */
    public CommonResult delRole(DelRoleCommand command);

    public HttpCommandResultWithData queryRoleByType(RoleByTypeCommand command);

    public HttpCommandResultWithData queryRolePermission(RolePermissionCommand command);
}
