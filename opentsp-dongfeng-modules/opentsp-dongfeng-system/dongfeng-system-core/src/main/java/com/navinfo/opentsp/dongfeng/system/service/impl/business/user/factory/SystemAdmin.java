package com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory;

import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserDetailOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.user.AddUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserDetailCommand;
import com.navinfo.opentsp.dongfeng.system.converter.UserConverter;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.repository.TeamMappingRepository;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;

/**
 * 系统管理员
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class SystemAdmin extends BaseOperator {

    public SystemAdmin(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        super(userRepository, teamRepository, dao);
    }

    @Override
    public HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        UserDetailOutDto dto = UserConverter.detailToDto(entity, null, null, null, null);
        result.setData(dto);
        return result;
    }

    @Override
    protected CommonResult add(final AddUserCommand command, final UserEntity entity, final boolean editFlag) {
        CommonResult result = new CommonResult();
        editUser(entity, editFlag);
        return result;
    }

    @Override
    protected CommonResult update(final EditUserCommand command, final UserEntity entity) {
        CommonResult result = new CommonResult();
        dao.update(entity);
        delReleationMapping(entity.getAccountId());
        return result;
    }
}
