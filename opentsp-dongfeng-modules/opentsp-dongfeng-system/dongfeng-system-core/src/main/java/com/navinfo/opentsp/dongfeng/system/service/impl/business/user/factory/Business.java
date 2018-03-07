package com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory;

import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserDetailOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.user.AddUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserDetailCommand;
import com.navinfo.opentsp.dongfeng.system.converter.UserConverter;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.UserAttributePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.UserAttributeRolePojo;
import com.navinfo.opentsp.dongfeng.system.repository.TeamMappingRepository;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经销商
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Business extends BaseOperator {
    public Business(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        super(userRepository, teamRepository, dao);
    }

    @Override
    public HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity) {
        Map<String, BigInteger> param = new HashMap<String, BigInteger>();
        param.put("accountId", entity.getAccountId());
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        List<UserAttributeRolePojo> roles = dao.sqlFind("getAttributeRole", param, UserAttributeRolePojo.class);
        List<UserAttributePojo> teams = dao.sqlFind("getAttributeTeam", param, UserAttributePojo.class);
        UserDetailOutDto dto = UserConverter.detailToDto(entity, null, roles, teams, null);
        result.setData(dto);
        return result;
    }

    @Override
    protected CommonResult add(final AddUserCommand command, final UserEntity entity, final boolean editFlag) {
        CommonResult result = new CommonResult();
        if (StringUtil.isEmpty(command.getJobType())) {
            result.fillResult(ReturnCode.USER_JOBTYPE_ISNOT_EMPTY);
            return result;
        }
        // 角色
        if (StringUtil.isEmpty(command.getRole())) {
            result.fillResult(ReturnCode.USER_ROLE_ISNOT_EMPTY);
            return result;
        }
        // 组
        if (StringUtil.isEmpty(command.getTeam())) {
            result.fillResult(ReturnCode.USER_GROUP_ISNOT_EMPTY);
            return result;
        }

        entity.setJobType(Integer.parseInt(command.getJobType()));
        UserEntity user = editUser(entity, editFlag);
        // 设置角色
        String[] roles = StringUtil.split(command.getRole(), ",");
        for (int i = 0; i < roles.length; i++) {
            saveRoleMapping(user.getAccountId(), Integer.parseInt(roles[i]));
        }
        // 设置组
        String[] teams = StringUtil.split(command.getTeam(), ",");
        for (int i = 0; i < teams.length; i++) {
            saveTeamMapping(user.getAccountId(), BigInteger.valueOf(Long.parseLong(teams[i])));
        }
        return result;
    }

    @Override
    protected CommonResult update(final EditUserCommand command, final UserEntity entity) {
        CommonResult result = new CommonResult();
        if (StringUtil.isEmpty(command.getJobType())) {
            result.fillResult(ReturnCode.USER_JOBTYPE_ISNOT_EMPTY);
            return result;
        }
        // 角色
        if (StringUtil.isEmpty(command.getRole())) {
            result.fillResult(ReturnCode.USER_ROLE_ISNOT_EMPTY);
            return result;
        }
        // 组
        if (StringUtil.isEmpty(command.getTeam())) {
            result.fillResult(ReturnCode.USER_GROUP_ISNOT_EMPTY);
            return result;
        }

        entity.setJobType(Integer.parseInt(command.getJobType()));
        dao.update(entity);
        delReleationMapping(entity.getAccountId());

        // 设置角色
        String[] roles = StringUtil.split(command.getRole(), ",");
        for (int i = 0; i < roles.length; i++) {
            saveRoleMapping(entity.getAccountId(), Integer.parseInt(roles[i]));
        }
        // 设置组
        String[] teams = StringUtil.split(command.getTeam(), ",");
        for (int i = 0; i < teams.length; i++) {
            saveTeamMapping(entity.getAccountId(), BigInteger.valueOf(Long.parseLong(teams[i])));
        }
        return result;
    }
}
