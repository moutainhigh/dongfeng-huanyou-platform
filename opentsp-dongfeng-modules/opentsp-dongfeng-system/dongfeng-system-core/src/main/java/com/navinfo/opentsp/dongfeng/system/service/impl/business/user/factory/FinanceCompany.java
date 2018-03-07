package com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory;

import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.user.AddUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserDetailCommand;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.repository.TeamMappingRepository;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;

/**
 * 金融公司
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class FinanceCompany extends BaseOperator {
    public FinanceCompany(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        super(userRepository, teamRepository, dao);
    }

    @Override
    protected HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity) {
        return null;
    }

    @Override
    protected CommonResult add(final AddUserCommand command, final UserEntity entity, final boolean editFlag) {
        return null;
    }

    @Override
    protected CommonResult update(final EditUserCommand command, final UserEntity entity) {
        return null;
    }
}

