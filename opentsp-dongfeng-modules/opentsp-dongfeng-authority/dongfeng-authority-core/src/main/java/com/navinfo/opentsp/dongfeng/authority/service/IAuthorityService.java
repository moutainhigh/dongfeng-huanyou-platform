package com.navinfo.opentsp.dongfeng.authority.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.authority.commands.*;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

import java.io.IOException;

/**
 * 鉴权模块服务类
 *
 * Created by zhangyu on 2017/3/10.
 */
public interface IAuthorityService
{
    
    /**
     * 用户登陆
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData login(LoginCommand command)
        throws JsonProcessingException;
    
    /**
     * 第三方用户登陆
     *
     * @param command
     * @return
     */
    public CommonResult openApilogin(OpenApiLoginCommand command) throws JsonProcessingException;
    
    /**
     * token校验
     *
     * @param command
     * @return
     */
    public HttpCommandResultWithData validateToken(ValidateTokenCommand command)
        throws JsonProcessingException;
    
    /**
     * user校验
     *
     * @param command
     * @return
     */
    public CommonResult validateUser(ValidateUserCommand command)
        throws JsonProcessingException;
    
    /**
     * 用户登出
     *
     * @param command
     * @return
     */
    public CommonResult logout(LogOutCommand command)
        throws JsonProcessingException;
    
    /**
     * 通过token获取用户信息
     *
     * @param command
     * @param result
     * @return
     * @throws JsonProcessingException
     */
    public HttpCommandResultWithData getUserInfoByToken(GetUserInfoByTokenCommand command,
        HttpCommandResultWithData result)
        throws JsonProcessingException;
    
    /**
     * 用户修改密码
     *
     * @param command
     * @return
     */
    public CommonResult updatePassword(UpdatePasswordCommand command)
        throws JsonProcessingException;
    
    /**
     * 通过获取随机验证码
     *
     * @param command
     * @param result
     * @return
     * @throws JsonProcessingException
     */
    public HttpCommandResultWithData getGetVerifyCode(GetVerifyCodeCommand command, HttpCommandResultWithData result)
        throws IOException;
}
