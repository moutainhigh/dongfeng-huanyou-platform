package com.navinfo.opentsp.dongfeng.system.service.impl.business.user.factory;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.user.AddUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.EditUserCommand;
import com.navinfo.opentsp.dongfeng.system.commands.user.UserDetailCommand;
import com.navinfo.opentsp.dongfeng.system.constant.UserStateConstant;
import com.navinfo.opentsp.dongfeng.system.entity.AccountTeamMapping;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.AccountBusinessMapping;
import com.navinfo.opentsp.dongfeng.system.pojo.AccountRoleMapping;
import com.navinfo.opentsp.dongfeng.system.pojo.AccountStationMapping;
import com.navinfo.opentsp.dongfeng.system.repository.TeamMappingRepository;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Base Class for User Operate
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public abstract class BaseOperator {

    protected UserRepository userRepository;
    protected TeamMappingRepository teamRepository;
    protected CommonDao dao;

    private static final int ACCOUNT_TEAM_MAPPING_FLAG = 0;
    private static final int ACCOUNT_VEHICLE_MAPPING_FLAG = 1;

    public BaseOperator(UserRepository userRepository, TeamMappingRepository teamRepository, CommonDao dao) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.dao = dao;
    }


    protected abstract HttpCommandResultWithData query(final UserDetailCommand command, final UserEntity entity);

    protected abstract CommonResult add(final AddUserCommand command, final UserEntity entity, final boolean editFlag);

    protected abstract CommonResult update(final EditUserCommand command, final UserEntity entity);

    public HttpCommandResultWithData queryUser(final UserDetailCommand command, final UserEntity entity) {
        return query(command, entity);
    }

    public CommonResult addUser(final AddUserCommand command) {
        CommonResult result = new CommonResult();
        UserEntity entity = new UserEntity();
        UserEntity userEntity = userRepository.findByAccountName(command.getAccountName());
        boolean editFlag = true;
        if (StringUtil.isNotEmpty(userEntity)) {
            editFlag = userEntity.getDelFlag() == Constants.IS_NOT_DEL ? true : false;
            if (editFlag) {
                result.fillResult(ReturnCode.USER_EXIST_FAILED);
                return result;
            }
            entity.setAccountId(userEntity.getAccountId());
        }

        entity.setAccountPwd(command.getAccountPwd());
        entity.setAccountName(command.getAccountName());
        entity.setAccountNickname(command.getAccountNickname());
        entity.setAccountLinktel(command.getAccountLinktel());
        entity.setAccountDescribe(command.getAccountDescribe());
        if (StringUtil.isNotEmpty(command.getLockAccount())) {
            entity.setLockAccount(command.getLockAccount());
        } else {
            entity.setLockAccount(UserStateConstant.UN_LOCK);
        }

        entity.setTermVilidate(BigInteger.valueOf(DateUtil.getCurrentSecondsByDate(command.getTermVilidate())));
        entity.setAccountType(Integer.parseInt(command.getAccountType()));
        entity.setAccountDate(BigInteger.valueOf(DateUtil.getCurrentSeconds()));
        entity.setDelFlag(Constants.IS_NOT_DEL);
        entity.setCreateAccount(BigInteger.valueOf(command.getUserInfor().getUserId()));
        entity.setEmail(command.getEmail());

        return add(command, entity, editFlag);
    }

    public CommonResult updateUser(final EditUserCommand command) {
        CommonResult result = new CommonResult();
        UserEntity entityReposit = userRepository.findByAccountId(BigInteger.valueOf(Long.parseLong(command.getAccountId())));
        if (entityReposit == null) {
            result.fillResult(ReturnCode.USER_GET_FAILED);
            return result;
        }

        UserEntity entity = new UserEntity();
        entity.setAccountId(BigInteger.valueOf(Long.parseLong(command.getAccountId())));
        entity.setAccountPwd(command.getAccountPwd());

        entity.setAccountName(command.getAccountName());
        entity.setAccountNickname(command.getAccountNickname());
        entity.setAccountLinktel(command.getAccountLinktel());
        entity.setAccountDescribe(command.getAccountDescribe());
        entity.setImage(entityReposit.getImage());

        if (StringUtil.isNotEmpty(command.getLockAccount())) {
            entity.setLockAccount(command.getLockAccount());
        } else {
            entity.setLockAccount(UserStateConstant.UN_LOCK);
        }
        if (StringUtil.isNotEmpty(command.getTermVilidate())) {
            entity.setTermVilidate(BigInteger.valueOf(DateUtil.getCurrentSecondsByDate(command.getTermVilidate())));
        }
        entity.setAccountType(Integer.parseInt(command.getAccountType()));
        entity.setAccountDate(BigInteger.valueOf(DateUtil.getCurrentSeconds()));
        entity.setDelFlag(Constants.IS_NOT_DEL);
        if (StringUtil.isNotEmpty(command.getCreateAccount())) {
            entity.setCreateAccount(BigInteger.valueOf(Long.parseLong(command.getCreateAccount())));
        }
        entity.setEmail(command.getEmail());
        return update(command, entity);
    }

    public CommonResult delUser(final UserEntity entity) {
        CommonResult result = new CommonResult();
        entity.setDelFlag(Constants.HAVE_DEL);
        dao.update(entity);
        delRoleMapping(entity.getAccountId());
        delTeamMapping(entity.getAccountId());
        delStationMapping(entity.getAccountId());
        delBusinessMapping(entity.getAccountId());

        result.fillResult(ReturnCode.OK);
        return result;
    }

    protected void delReleationMapping(BigInteger accountId){
        delRoleMapping(accountId);
        delTeamMapping(accountId);
        delStationMapping(accountId);
        delBusinessMapping(accountId);
    }

    /**
     * @param entity
     * @param editFlag
     * @return
     */
    protected UserEntity editUser(final UserEntity entity, final boolean editFlag) {
        UserEntity user = null;
        if (editFlag) {
            user = (UserEntity) dao.save(entity);
        } else {
            user = (UserEntity) dao.update(entity);
        }
        return user;
    }

    protected void saveTeamMapping(final BigInteger accountId, final BigInteger teamId) {
        AccountTeamMapping teamMapping = new AccountTeamMapping();
        teamMapping.setAccountId(accountId);
        teamMapping.setCarTeamId(teamId);
        teamMapping.setAtcType(ACCOUNT_TEAM_MAPPING_FLAG);
        dao.save(teamMapping);
    }

    protected void saveRoleMapping(final BigInteger accountId, final int role) {
        AccountRoleMapping roleMapping = new AccountRoleMapping();
        roleMapping.setAccountId(accountId);
        roleMapping.setrCode(role);
        dao.executeUpdate("insertRoleMapping", roleMapping);
    }

    protected void saveStationMapping(final BigInteger accountId, final BigInteger stationId) {
        AccountStationMapping stationMapping = new AccountStationMapping();
        stationMapping.setAccountId(accountId);
        stationMapping.setStationId(stationId);
        dao.executeUpdate("insertStationMapping", stationMapping);
    }

    protected void saveBusinessMapping(final BigInteger accountId, final BigInteger businessId) {
        AccountBusinessMapping businessMapping = new AccountBusinessMapping();
        businessMapping.setAccountId(accountId);
        businessMapping.setBusinessId(businessId);
        dao.executeUpdate("insertBusinessMapping", businessMapping);
    }

    protected void delRoleMapping(final BigInteger accountId) {
        AccountRoleMapping roleMapping = new AccountRoleMapping();
        roleMapping.setAccountId(accountId);
        dao.executeUpdate("delRoleMapping", roleMapping);
    }

    protected void delStationMapping(final BigInteger accountId) {
        AccountStationMapping stationMapping = new AccountStationMapping();
        stationMapping.setAccountId(accountId);
        dao.executeUpdate("delStationMapping", stationMapping);

    }

    protected void delTeamMapping(final BigInteger accountId) {
        List<AccountTeamMapping> list = teamRepository.findByAccountId(accountId);
        if (!StringUtil.isEmpty(list)) {
            dao.batchDelete(list);
        }
    }

    protected void delBusinessMapping(final BigInteger accountId) {
        AccountBusinessMapping businessMapping = new AccountBusinessMapping();
        businessMapping.setAccountId(accountId);
        dao.executeUpdate("delBusinessMapping", businessMapping);
    }

}
