package com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory;

import com.navinfo.opentsp.dongfeng.common.constant.JobTypeConstant;
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
 * 车厂
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class VehicleFactory extends BaseOperator {
    public VehicleFactory(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        super(userRepository, teamRepository, dao);
    }

    @Override
    public HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        UserDetailOutDto dto = null;
        Map<String, BigInteger> param = new HashMap<String, BigInteger>();
        param.put("accountId", entity.getAccountId());
        if (entity.getJobType() == JobTypeConstant.JOB_TYPE.getCode()
                || entity.getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
            List<UserAttributeRolePojo> roles = dao.sqlFind("getAttributeRole", param, UserAttributeRolePojo.class);
            List<UserAttributePojo> teams = dao.sqlFind("getAttributeTeam", param, UserAttributePojo.class);
            List<UserAttributePojo> stations = dao.sqlFind("getAttributeStation", param, UserAttributePojo.class);
            dto = UserConverter.detailToDto(entity, null, roles, teams, stations);
        } else {
            dto = UserConverter.detailToDto(entity, null, null, null, null);
        }

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

        entity.setJobType(Integer.parseInt(command.getJobType()));
        if (entity.getJobType() == JobTypeConstant.JOB_TYPE.getCode()
                || entity.getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
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
        }

        UserEntity user = editUser(entity, editFlag);
        if (entity.getJobType() == JobTypeConstant.JOB_TYPE.getCode()
                || entity.getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
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

            // 设置服务站
            if (StringUtil.isNotEmpty(command.getServiceStation())) {
                String[] stations = StringUtil.split(command.getServiceStation(), ",");
                for (int i = 0; i < stations.length; i++) {
                    saveStationMapping(user.getAccountId(), BigInteger.valueOf(Long.parseLong(stations[i])));
                }
            }

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

        entity.setJobType(Integer.parseInt(command.getJobType()));
        if (entity.getJobType() == JobTypeConstant.JOB_TYPE.getCode()
                || entity.getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
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
        }

        dao.update(entity);
        delReleationMapping(entity.getAccountId());

        if (entity.getJobType() == JobTypeConstant.JOB_TYPE.getCode()
                || entity.getJobType() == JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode()) {
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

            // 设置服务站
            if (StringUtil.isNotEmpty(command.getServiceStation())) {
                String[] stations = StringUtil.split(command.getServiceStation(), ",");
                for (int i = 0; i < stations.length; i++) {
                    saveStationMapping(entity.getAccountId(), BigInteger.valueOf(Long.parseLong(stations[i])));
                }
            }

        }

        return result;
    }
}
