package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.user.*;
import com.navinfo.opentsp.dongfeng.system.converter.UserConverter;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.UserListPojo;
import com.navinfo.opentsp.dongfeng.system.repository.UserRepository;
import com.navinfo.opentsp.dongfeng.system.service.IUserService;
import com.navinfo.opentsp.dongfeng.system.service.impl.business.user.IUserFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaocy on 2017/03/08.
 * 用户相关功能Service
 */
@Service
public class UserServiceImpl extends BaseService implements IUserService {

    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final long INVALID_ACCOUNT_ID = -1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserFactory userFactory;

    @Resource
    private IOperateLogService operateLogService;

    /**
     * 查询用户
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData queryUserList(final UserListCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo page = new PagingInfo();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountName", command.getAccountName());
        parameters.put("accountType", command.getAccountType());
        parameters.put("lockAccount", command.getLockAccount());
        parameters.put("pastDue", command.getPastDue());
        parameters.put("createAccount", command.getCreateAccount());
        parameters.put("accountId", command.getUserInfor().getUserId());
        parameters.put("userType", command.getUserInfor().getType());
        configReleateAccounts(command, parameters);
        configSubAccounts(command, parameters);

        PagingInfo<UserListPojo> datas = new PagingInfo<>();
        if (isQueryAll) {
            List<UserListPojo> users = dao.sqlFind("queryUserList", parameters, UserListPojo.class);
            datas.setList(users);
        } else {
            datas = dao.sqlPagelFind("queryUserList", parameters,
                    NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), UserListPojo.class);
        }

        page.setList(UserConverter.listPojoToDto(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        return result;
    }

    private void configReleateAccounts(UserListCommand command, Map<String, Object> parameters) {
        Map<String, Object> userParameter = new HashMap<>();
        userParameter.put("stationName", command.getStationName());
        userParameter.put("businessName", command.getBusinessName());
        userParameter.put("dealerName", command.getDealerName());
        userParameter.put("roleName",command.getRoleName());

        if (StringUtil.isNotEmpty(command.getBusinessName())) {
            List<BigInteger> busiAccounts = dao.sqlFindField("queryUserByBusiness", userParameter);
            busiAccounts.add(BigInteger.valueOf(INVALID_ACCOUNT_ID));
            parameters.put("busiAccounts", busiAccounts);
        }

        if (StringUtil.isNotEmpty(command.getStationName())) {
            List<BigInteger> stationAccounts = (List<BigInteger>)dao.sqlFindField("queryUserByStation", userParameter);
            stationAccounts.add(BigInteger.valueOf(INVALID_ACCOUNT_ID));
            parameters.put("stationAccounts", stationAccounts);
        }

        if (StringUtil.isNotEmpty(command.getDealerName())) {
            List<BigInteger> dealerAccounts = dao.sqlFindField("queryUserByDealer", userParameter);
            dealerAccounts.add(BigInteger.valueOf(INVALID_ACCOUNT_ID));
            parameters.put("dealerAccounts", dealerAccounts);
        }

        if (StringUtil.isNotEmpty(command.getRoleName())) {
            List<BigInteger> roleAccounts = dao.sqlFindField("queryUserByRole", userParameter);
            roleAccounts.add(BigInteger.valueOf(INVALID_ACCOUNT_ID));
            parameters.put("roleAccounts", roleAccounts);
        }
    }

    private void configSubAccounts(UserListCommand command, Map<String, Object> parameters) {
        String result = StringUtil.valueOf(INVALID_ACCOUNT_ID);
        Map<String, Object> userParameter = new HashMap<>();
        userParameter.put("accountId", command.getUserInfor().getUserId());
        List<String> userIds = (List<String>) dao.sqlFindField("querySubUserIDs", userParameter);
        if (CollectionUtils.isNotEmpty(userIds)) {
            result = userIds.get(0);
        }
        parameters.put("subUserIDs", result);
        logger.info("configSubAccounts result is {}", result);
    }

    @Override
    public HttpCommandResultWithData getUserDetail(final UserDetailCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        UserEntity entity = userRepository.findByAccountId(BigInteger.valueOf(Long.parseLong(command.getAccountId())));
        if (entity == null) {
            result.fillResult(ReturnCode.USER_GET_FAILED);
            return result;
        }

        UserTypeConstant userType = UserTypeConstant.valueOf(entity.getAccountType());
        result = userFactory.build(userType).queryUser(command, entity);
        return result;
    }

    @Override
    @Transactional
    public CommonResult addUser(final AddUserCommand command) {
        UserTypeConstant userType = UserTypeConstant.valueOf(Integer.parseInt(command.getAccountType()));
        CommonResult result = userFactory.build(userType).addUser(command);

        final String operateContent = OperateLogConstants.OperateContentPrefixEnum.USER.getValue() + command.getAccountName();
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, operateContent, result);
        return result;
    }

    @Override
    @Transactional
    public CommonResult editUser(final EditUserCommand command) {
        UserTypeConstant userType = UserTypeConstant.valueOf(Integer.parseInt(command.getAccountType()));
        CommonResult result = userFactory.build(userType).updateUser(command);

        final String operateContent = OperateLogConstants.OperateContentPrefixEnum.USER.getValue() + command.getAccountName();
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, operateContent, result);
        return result;
    }

    @Override
    @Transactional
    public CommonResult delUser(final DelUserCommand command) {
        CommonResult result = new CommonResult();
        UserEntity entity = userRepository.findByAccountId(BigInteger.valueOf(Long.parseLong(command.getAccountId())));
        if (entity == null) {
            result.fillResult(ReturnCode.USER_DEL_FAILED);
            return result;
        }

        UserTypeConstant userType = UserTypeConstant.valueOf(entity.getAccountType());
        result = userFactory.build(userType).delUser(entity);

        final String operateContent = OperateLogConstants.OperateContentPrefixEnum.USER.getValue() + entity.getAccountName();
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.DELETE, operateContent, result);
        return result;
    }

    @Override
    @Transactional
    public CommonResult modifyPassWord(final ModifyPassWordCommand command) {
        CommonResult result = new CommonResult();
        UserEntity entity = userRepository.findByAccountId(BigInteger.valueOf(command.getUserInfor().getUserId()));
        if (entity == null) {
            result.fillResult(ReturnCode.USER_GET_FAILED);
            return result;
        }
        if (StringUtil.isNotEq(entity.getAccountPwd(), command.getAccountPwd())) {
            result.fillResult(ReturnCode.USER_PWD_MODIFY_FAILED);
            return result;
        }
        entity.setAccountPwd(command.getNewPwd());
        dao.update(entity);

        final String operateContent = command.getUserInfor().getUsername() + OperateLogConstants.OperateContentPrefixEnum.PASSWORD.getValue();
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, operateContent, result);
        return result;
    }

    @Override
    @Transactional
    public CommonResult editUserImage(final EditUserImageCommand command) {
        CommonResult result = new CommonResult();
        UserEntity entity = userRepository.findByAccountId(BigInteger.valueOf(Long.parseLong(command.getAccountId())));
        if (entity == null) {
            result.fillResult(ReturnCode.USER_GET_FAILED);
            return result;
        }
        entity.setImage(command.getImage());
        dao.update(entity);
        return result;
    }
}
