package com.navinfo.opentsp.dongfeng.common.handler;

import com.navinfo.opentsp.dongfeng.authority.commands.ValidateTokenCommand;
import com.navinfo.opentsp.dongfeng.authority.commands.ValidateUserCommand;
import com.navinfo.opentsp.dongfeng.common.client.AuthorityClient;
import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dto.UserInfoDto;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.PropertiesUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.validation.ValidateUtil;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentspcore.common.handler.AbstractCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 校验token并且不重置token存活时间的BaseAbstractHandler
 * 
 * Created by zhangyu on 2016/11/07.
 */
public abstract class ValidateTokenAndNoReSetBaseAbstractHandler<C extends BaseCommand<?>, CR extends CommonResult>
    extends AbstractCommandHandler<C, CR>
{
    protected static final Logger logger = LoggerFactory.getLogger(ValidateTokenAndNoReSetBaseAbstractHandler.class);
    
    protected ValidateTokenAndNoReSetBaseAbstractHandler(Class<C> commandType, Class<CR> resultType)
    {
        super(commandType, resultType);
    }
    
    @Autowired
    AuthorityClient authorityClient;
    
    @Override
    public CR handle(C command)
    {
        command.getPage_size();// 设置初始值
        CR result = null;
        
        try
        {
            result = this.getResultType().newInstance();
            
            // 是否校验token，上线时开关必须打开
            if (Boolean.valueOf(PropertiesUtil.getPropertiesByKey(Constants.VALIDATE_TOKEN)))
            {
                // token校验
                if (!this.validateToken(command, result))
                {
                    return result;
                }
                // 用户校验
                if (!this.checkUserExist(command, result))
                {
                    return result;
                }
            }
            else
            {
                // 设置用户信息到基类:用于测试
                command.setUserInfor(getDefultUserInfoDto());
            }
            // 参数校验
            if (!this.validateMsg(command, result))
            {
                return result;
            }
            // 设置分页信息
            command.setPage_number(String.valueOf(Integer.parseInt(command.getPage_number()) - 1));
            logger.info("begin run " + this.getClass().getName() + "`s businessHandle ====>");
            result = businessHandle(command);
            logger.info("end " + this.getClass().getName() + "`s businessHandle");
        }
        catch (Exception e)
        {
            logger.error(this.getClass().getName() + "`s businessHandle has error : ", e);
            result.fillResult(ReturnCode.SERVER_ERROR);
        }
        return result;
    }
    
    /**
     * 校验token
     *
     * @param command
     * @param result
     * @return
     */
    private boolean validateToken(C command, CR result)
    {
        // 获取token
        String token = command.getToken();
        
        // token为空视为无效
        if (StringUtil.isEmpty(token))
        {
            result.fillResult(ReturnCode.TOKEN_FAIL);
            return false;
        }
        try
        {
            // 设置token
            ValidateTokenCommand validateTokenCommand = new ValidateTokenCommand();
            validateTokenCommand.setToken(token);
            validateTokenCommand.setReSetTokenLiveTimeFlg(Constants.RedisLiveTimeReSetEnum.NO_RESET.getCode());
            
            // 获取鉴权结果
            HttpCommandResultWithData tokenResult = authorityClient.validateToken(validateTokenCommand);
            
            // result为空或者或者没缓存中有用户信息或者返回码不是200鉴权失败
            if (tokenResult == null || tokenResult.getData() == null
                || tokenResult.getResultCode() != ReturnCode.OK.code())
            {
                result.fillResult(ReturnCode.TOKEN_FAIL);
                return false;
            }
            
            // 设置用户信息到基类
            command.setUserInfor(getUserInfoDto(tokenResult));
        }
        catch (Exception e)
        {
            logger.error("验证token出现异常", e);
            result.fillResult(ReturnCode.SERVER_ERROR);
            return false;
        }
        return true;
    }
    
    /**
     * 校验massage
     *
     * @param command
     * @return
     */
    private boolean validateMsg(C command, CR result)
    {
        String validateMsg = ValidateUtil.validateCommand(command, GroupCommand.class);
        if (validateMsg != null)
        {
            // 入参异常提示
            result.setResultCode(507);
            result.setMessage(validateMsg);
            return false;
        }
        return true;
    }
    
    /**
     * 设置UserInfoDto
     *
     * @param tokenResult
     * @return
     */
    private UserInfoDto getUserInfoDto(HttpCommandResultWithData tokenResult)
    {
        UserInfoDto dto = new UserInfoDto();
        Map<String, Object> map = (Map<String, Object>)tokenResult.getData();
        if (map != null)
        {
            dto.setUserId(Long.valueOf(String.valueOf(map.get("userId"))));
            dto.setToken((String)map.get("token"));
            dto.setVerycode((String)map.get("verycode"));
            dto.setUsername((String)map.get("username"));
            dto.setType((int)map.get("type"));
            dto.setJobType((int)map.get("jobType"));
            dto.setPassword((String)map.get("password"));
        }
        return dto;
    }
    
    /**
     * 获取默认用户信息（用于测试，token开关关闭时的默认值:system用户）
     *
     * @return
     */
    private UserInfoDto getDefultUserInfoDto()
    {
        UserInfoDto dto = new UserInfoDto();
        dto.setUserId(1L);
        dto.setUsername("system");
        dto.setType(1);
        dto.setPassword("123qwe");
        return dto;
    }
    
    /**
     * 校验用户
     *
     * @param command
     * @param result
     * @return
     */
    private boolean checkUserExist(C command, CR result)
        throws Exception
    {
        try
        {
            UserInfoDto userInfor = command.getUserInfor();
            if (userInfor == null)
            {
                result.fillResult(ReturnCode.USER_NOT_EXIST);
                return false;
            }
            // 用户id
            String userId = StringUtil.valueOf(userInfor.getUserId());
            
            // 设置token
            ValidateUserCommand validateUserCommand = new ValidateUserCommand();
            validateUserCommand.setUserId(userId);
            
            // 获取用户结果
            CommonResult userResult = authorityClient.validateUser(validateUserCommand);
            
            // result为空或者或者返回码不是200用户鉴权失败
            if (userResult == null || userResult.getResultCode() != ReturnCode.OK.code())
            {
                result.fillResult(ReturnCode.USER_NOT_EXIST);
                return false;
            }
        }
        catch (Exception e)
        {
            logger.error("验证user出现异常", e);
            result.fillResult(ReturnCode.SERVER_ERROR);
            return false;
        }
        return true;
    }
    
    protected abstract CR businessHandle(C command);
}
