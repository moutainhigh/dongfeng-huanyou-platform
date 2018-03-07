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
 * 服务站
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class ServiceStation extends BaseOperator {

    public ServiceStation(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        super(userRepository, teamRepository, dao);
    }

    @Override
    protected HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, BigInteger> param = new HashMap<String, BigInteger>();
        param.put("accountId", entity.getAccountId());
        List<UserAttributeRolePojo> roles = dao.sqlFind("getAttributeRole", param, UserAttributeRolePojo.class);
        List<UserAttributePojo> stations = dao.sqlFind("getAttributeStation", param, UserAttributePojo.class);
        UserDetailOutDto dto = UserConverter.detailToDto(entity, null, roles, null, stations);
        result.setData(dto);
        result.fillResult(ReturnCode.OK);
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
        // 服务站
        if (StringUtil.isEmpty(command.getServiceStation())) {
            result.fillResult(ReturnCode.USER_STATION_ISNOT_EMPTY);
            return result;
        }

        entity.setJobType(Integer.parseInt(command.getJobType()));
        UserEntity user = editUser(entity, editFlag);
        // 设置角色
        String[] roles = StringUtil.split(command.getRole(), ",");
        for (int i = 0; i < roles.length; i++) {
            saveRoleMapping(user.getAccountId(), Integer.parseInt(roles[i]));
        }

        // 设置服务站
        String[] stations = StringUtil.split(command.getServiceStation(), ",");
        for (int i = 0; i < stations.length; i++) {
            saveStationMapping(user.getAccountId(), BigInteger.valueOf(Long.parseLong(stations[i])));
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
        // 服务站
        if (StringUtil.isEmpty(command.getServiceStation())) {
            result.fillResult(ReturnCode.USER_STATION_ISNOT_EMPTY);
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

        // 设置服务站
        String[] stations = StringUtil.split(command.getServiceStation(), ",");
        for (int i = 0; i < stations.length; i++) {
            saveStationMapping(entity.getAccountId(), BigInteger.valueOf(Long.parseLong(stations[i])));
        }
        return result;
    }
}
