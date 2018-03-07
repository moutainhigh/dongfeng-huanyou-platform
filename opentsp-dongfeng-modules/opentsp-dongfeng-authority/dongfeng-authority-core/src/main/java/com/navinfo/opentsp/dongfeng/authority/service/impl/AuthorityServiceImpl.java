package com.navinfo.opentsp.dongfeng.authority.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.authority.client.PushClient;
import com.navinfo.opentsp.dongfeng.authority.commands.*;
import com.navinfo.opentsp.dongfeng.authority.converter.UserConverter;
import com.navinfo.opentsp.dongfeng.authority.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.authority.pojo.UserAttributePojo;
import com.navinfo.opentsp.dongfeng.authority.repository.UserRepository;
import com.navinfo.opentsp.dongfeng.authority.service.IAuthorityService;
import com.navinfo.opentsp.dongfeng.common.constant.*;
import com.navinfo.opentsp.dongfeng.common.dto.CheckOnline;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.dto.UserInfoDto;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.FileUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.common.util.VerifyCodeUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 鉴权模块service
 *
 * Created by zhangyu on 2017/3/10.
 */
@Service(value = "authorityService")
public class AuthorityServiceImpl extends BaseService implements IAuthorityService
{
    
    protected static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;
    
    // token有效时间
    @Value("${redis.token.liveTime}")
    private long tokenLiveTime;
    
    // 验证码redis有效期
    @Value("${redis.verifyCode.liveTime}")
    private long verifyCodeliveTime;
    
    // n分钟内连续输入错误m次密码锁定账号
    @Value("${login.error.liveTime}")
    private long loginErrorLiveTime;
    
    // 登陆错误n次后锁定账号
    @Value("${login.error.times}")
    private long errorTimes;
    
    // 密码错误n次校验验证码
    @Value("${need.check.verify.code.time}")
    private long needCheckVerifyCodeTime;
    
    // 登陆错误信息redis有效时间
    @Value("${login.error.redis.liveTime}")
    private long errorRedisLiveTime;
    
    // 上传文件接口地址
    @Value("${uploadFile.url}")
    private String uploadFileUrl;
    
    // 删除文件接口地址
    @Value("${delFileUrl.url}")
    private String delFileUrl;
    
    @Autowired
    private PushClient pushClient;
    
    /**
     * 用户登陆
     *
     * @param command
     * @return
     */
    @Override
    @Transactional
    public HttpCommandResultWithData login(LoginCommand command)
        throws JsonProcessingException
    {
        // logger.info("=====  login start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        
        // redis 登陆验证码key:VERIFYCODE_+VerifyCodeId+"_"+"userName"
        String errorKey =
            Constants.ERROR_LOGIN_KEY + command.getVerifyCodeId() + Constants.SYMBO_UNDERLINE + command.getUserName();
        
        // 登陆错误信息
        Map<String, String> errorMap = getErrorMap(errorKey);
        
        // 校验验证码
        if (!checkVerifyCode(command, result, errorMap))
        {
            return result;
        }
        
        // 查询未删除的用户信息
        UserEntity userEntity = userRepository.findByAccountNameAndDelFlag(command.getUserName(), Constants.IS_NOT_DEL);
        
        // 校验失败返回错误信息
        if (!checkUser(userEntity, command, result, errorKey, errorMap))
        {
            return result;
        }
        
        // dto转换
        UserInfoDto dto = UserConverter.enyityToDto(userEntity);
        
        // 判断是否有其他用户登陆，并且确认是否踢掉其他用户,如果返回false返回前端确认登陆提示,如果true清掉redis中之前用户登陆的信息并保存当前也用户登陆的信息
        if (!isSaveTokenToredis(dto, command.getConfirmLoginFlg(), result))
        {
            return result;
        }
        
        // 封装返回结果
        Map map = new LinkedMap();
        map.put("token", dto.getToken());
        result.setData(map);
        
        // 返回成功
        return result;
    }
    
    /**
     * 用户登陆
     *
     * @param command
     * @return
     */
    @Override
    public CommonResult openApilogin(OpenApiLoginCommand command)
        throws JsonProcessingException
    {
        // logger.info("=====  login start  =====");
        CommonResult result = new CommonResult();
        
        // redis token key:OPENAPI_TOKEN_KEY_+UserName
        String key = Constants.OPENAPI_TOKEN_KEY + command.getUserName();
        
        // 已经登陆的用户信息
        UserEntity userEntity = redisService.getJson(key, UserEntity.class);
        
        if (userEntity == null)
        {
            // 没登陆过查询用户信息
            userEntity = userRepository.findByAccountNameAndDelFlag(command.getUserName(), Constants.IS_NOT_DEL);
        }
        
        // 校验通过刷新redis有效期
        if (checkOpenApiUser(userEntity, command.getPassword(), key, result))
        {
            redisService.saveObjectToJson(key, userEntity, tokenLiveTime);
        }
        return result;
    }
    
    /**
     * * 用户登出
     *
     * @param command
     * @return
     */
    @Override
    public CommonResult logout(LogOutCommand command)
    {
        CommonResult result = new CommonResult();
        
        // 默认设置为成功
        result.fillResult(ReturnCode.OK);
        
        // redis token key:STOKENS_+UserId+"_"+token
        // String key =
        // Constants.TOKEN_KEY + command.getUserInfor().getUserId() + Constants.SYMBO_UNDERLINE + command.getToken();
        String key = Constants.SYMBO_NUMBERSIGN + command.getToken() + Constants.SYMBO_NUMBERSIGN;
        
        // 清空所有token
        // redisService.del(key);
        redisService.delKeys(key);
        
        return result;
    }
    
    /**
     * * 用户修改密码
     *
     * @param command
     * @return
     */
    @Override
    @Transactional
    public CommonResult updatePassword(UpdatePasswordCommand command)
    {
        CommonResult result = new CommonResult();
        
        // 默认设置为成功
        result.fillResult(ReturnCode.OK);
        
        // 按照用户Id和删除区分查询
        UserEntity userEntity =
            userRepository.findByAccountIdAndDelFlag(BigInteger.valueOf(command.getUserInfor().getUserId()),
                Constants.IS_NOT_DEL);
        if (userEntity.getAccountId() != null && userEntity.getAccountId().longValue() == 1 && userEntity.getAccountType()!=null && userEntity.getAccountType().intValue() == 1) {
            result.fillResult(ReturnCode.ADMINISTRATOR_PASSWORD_ISNOT_MODIFY);
            return result;
        }
        // 如果数据库中的密码和原始的密码不一致返回错
        if (StringUtil.isNotEq(userEntity.getAccountPwd(), command.getOldPassword()))
        {
            result.fillResult(ReturnCode.OLD_PASSWORD_WRONG);
            return result;
        }
        
        // 更新用户密码
        userEntity.setAccountPwd(command.getNewPassword());
        userRepository.save(userEntity);
        return result;
    }
    
    /**
     * token校验
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData validateToken(ValidateTokenCommand command)
        throws JsonProcessingException
    {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        
        // 默认设置为成功
        result.fillResult(ReturnCode.OK);
        
        // 设置token校验返回结果
        setValidateTokenResult(command.getToken(), command.getReSetTokenLiveTimeFlg(), result);
        
        return result;
    }
    
    /**
     * 用户存在校验
     *
     * @param command
     * @return
     */
    @Override
    public CommonResult validateUser(ValidateUserCommand command)
    {
        CommonResult result = new CommonResult();
        
        // 默认设置为成功
        result.fillResult(ReturnCode.OK);
        
        // 按照用户Id和删除区分查询
        UserEntity userEntity =
            userRepository.findByAccountIdAndDelFlag(BigInteger.valueOf(StringUtil.toLong(command.getUserId(), 0L)),
                Constants.IS_NOT_DEL);
        
        if (userEntity == null)
        {
            // 用户不存在
            result.fillResult(ReturnCode.USER_NOT_EXIST);
        }
        
        return result;
    }
    
    /**
     * 通过token获取用户信息
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData getUserInfoByToken(GetUserInfoByTokenCommand command,
        HttpCommandResultWithData result)
        throws JsonProcessingException
    
    {
        // 设置token校验返回结果
        this.setValidateTokenResult(command.getToken(), Constants.RedisLiveTimeReSetEnum.NO_RESET.getCode(), result);
        return result;
    }
    
    /**
     * 获取验证码
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData getGetVerifyCode(GetVerifyCodeCommand command, HttpCommandResultWithData result)
        throws IOException
    
    {
        // redis 验证码key:VERIFYCODE_+VerifyCodeId
        String key = Constants.VERIFYCODE_KEY + command.getVerifyCodeId();
        
        // 同一session存储的验证码
        Map<String, String> oldMap = redisService.getJson(key, LinkedMap.class);
        
        // 如果存储过验证码文件则删除文件服务器上传的验证码文件,清空redis
        if (!StringUtil.isEmpty(oldMap))
        {
            try
            {
                this.delGetVerifyCodeFile(oldMap, key);
            }
            catch (Exception e)
            {
                logger.error("删除临时验证码文件失败:" + e.getMessage(), e);
            }
        }
        
        // 上传验证码文件并返回验证码和文件路径
        Map<String, String> map = VerifyCodeUtils.uploadVerifyCodeFlie(command.getVerifyCodeId(), uploadFileUrl);
        
        // 上传失败或者验证码为空返回错误
        if (StringUtil.isEmpty(map) || StringUtil.isEmpty(map.get("verifyCode"))
            || StringUtil.isEmpty(map.get("fullPath")))
        {
            result.fillResult(ReturnCode.UPLOAD_GETVERIFYCODE_ERROR);
            return result;
        }
        
        // 存储验证码
        redisService.saveObjectToJson(key, map, verifyCodeliveTime);
        
        // 结果集赋值
        Map<String, String> returnMap = new<String, String> LinkedMap();
        returnMap.put("fullPath", map.get("fullPath"));
        result.setData(returnMap);
        return result;
    }
    
    /**
     * 校验用户合法性
     *
     * @param userEntity
     * @param command
     * @param result
     * @param errorKey
     * @param errorMap
     * @return
     * @throws JsonProcessingException
     */
    private boolean checkUser(UserEntity userEntity, LoginCommand command, HttpCommandResultWithData result,
        String errorKey, Map<String, String> errorMap)
        throws JsonProcessingException
    {
        
        // 删除所有用户登陆错误信息
        clearErrorMap(command);
        
        // 如果用户不存返回:用户名错误
        if (userEntity == null)
        {
            result.fillResult(ReturnCode.ERROR_USERNAME);
            return false;
        }
        
        // 登陆用户为车厂用户并且职位不是整体管理和交付室返回没有权限
        if (userEntity.getAccountType() == UserTypeConstant.CAR_FACTORY.getCode()
            && userEntity.getJobType() != JobTypeConstant.JOB_TYPE.getCode()
            && userEntity.getJobType() != JobTypeConstant.JOB_TYPE_DELIVERY_ROOM.getCode())
        {
            result.fillResult(ReturnCode.ERROR_NO_RIGHT_ACCESS);
            return false;
        }
        
        // 登陆用户为经销商用户并且职位不是经理没有权限
        if (userEntity.getAccountType() == UserTypeConstant.BUSINESS.getCode()
            && userEntity.getJobType() != BusinessJobType.MANAGER.getIndex())
        {
            result.fillResult(ReturnCode.ERROR_NO_RIGHT_ACCESS);
            return false;
        }
        
        // 登陆用户为服务站用户
        if (userEntity.getAccountType() == UserTypeConstant.SERVICE_STATION.getCode())
        {
            // 职位不是经理没有权限
            if (userEntity.getJobType() != StationJobType.MANAGER.getIndex())
            {
                result.fillResult(ReturnCode.ERROR_NO_RIGHT_ACCESS);
                return false;
            }
            else
            {
                // 是经理职位但是服务站被停用了没有权限登录
                Map<String, BigInteger> param = new HashMap<String, BigInteger>();
                param.put("accountId", userEntity.getAccountId());
                UserAttributePojo stations =
                    (UserAttributePojo)dao.sqlFindObject("getAttributeStation", param, UserAttributePojo.class);
                
                // 如果服务站被停用了
                if (stations != null && stations.getStationEnable() == StationStatus.STATION_DISABLE.getIndex())
                {
                    result.fillResult(ReturnCode.SERVICE_STATION_CLOSED_LOGIN_FAIL);
                    return false;
                }
            }
        }
        
        // 锁定状态不为空并且用户被锁定返回:用户被锁定
        if (!StringUtil.isEmpty(userEntity.getLockAccount())
            && StringUtil.isEq(userEntity.getLockAccount(), Constants.LockStatusEnum.LOCK.getValue()))
        {
            result.fillResult(ReturnCode.ACCOUNT_LOCKED);
            return false;
        }
        
        // 有效时间不为空并且不大于系统时间返回：用户过期
        if (userEntity.getTermVilidate() != null
            && (userEntity.getTermVilidate().longValue()) <= (System.currentTimeMillis() / 1000))
        {
            result.fillResult(ReturnCode.ACCOUNT_EXPIRED);
            return false;
        }
        
        // 如果密码不正确返回:密码错误+错误次数
        if (StringUtil.isNotEq(userEntity.getAccountPwd(), command.getPassword()))
        {
            setErrorTimes(result, userEntity, errorKey, errorMap);
            
            // 给前端返回是否显示验证码
            setIsShowVerifyCode(result, errorMap);
            return false;
        }
        // 用户名密码正确清空登陆错误信息
        else
        {
            redisService.del(errorKey);
        }
        
        return true;
    }
    
    /**
     * 校验验证码合法性
     *
     * @param command
     * @param result
     * @param errorMap
     * @return
     */
    private boolean checkVerifyCode(LoginCommand command, HttpCommandResultWithData result, Map<String, String> errorMap)
    {
        // 错误数
        Long errorConunt = this.getErrorConunt(errorMap);
        ;
        
        // 验证码唯一标识
        String verifyCodeId = command.getVerifyCodeId();
        
        // 验证码
        String verifyCode = command.getVerifyCode();
        
        // redis 验证码key:VERIFYCODE_+VerifyCodeId
        String key = Constants.VERIFYCODE_KEY + verifyCodeId;
        
        // redis中验证码
        Map<String, String> map = redisService.getJson(key, LinkedMap.class);
        
        // 有验证码就校验
        if (StringUtil.isNotEmpty(verifyCode))
        {
            return checkVerifyCode(map, result, verifyCode);
        }
        // 没有验证码时如果不是第一次密码输入错误，进行校验
        else
        {
            // 如果密码已经错误一次校验验证码
            if (errorConunt >= needCheckVerifyCodeTime)
            {
                return checkVerifyCode(map, result, verifyCode);
            }
        }
        return true;
    }
    
    /**
     * @param map
     * @param result
     * @param verifyCode
     * @return
     */
    private boolean checkVerifyCode(Map<String, String> map, HttpCommandResultWithData result, String verifyCode)
    {
        // 验证码不能为空
        if (StringUtil.isEmpty(verifyCode))
        {
            result.fillResult(ReturnCode.VERIFYCODE_NOT_NULL);
            return false;
        }
        
        // redis中没有验证码或者验证码为空或者输入的验证码和生成的验证码不一致返回错误信息
        if (map == null || StringUtil.isEmpty(map.get("verifyCode"))
            || StringUtil.isNotEq(map.get("verifyCode"), verifyCode.toUpperCase().trim()))
        {
            result.fillResult(ReturnCode.GETVERIFYCODE_VALIDATE_ERROR);
            return false;
        }
        return true;
    }
    
    /**
     * 判断是否有其他用户登陆，并且是否确认踢掉其他用户
     *
     * @param dto
     * @param confirmLoginFlg
     * @param result
     * @throws JsonProcessingException
     */
    private boolean isSaveTokenToredis(UserInfoDto dto, String confirmLoginFlg, HttpCommandResultWithData result)
        throws JsonProcessingException
    {
        // 生成token
        String token = StringUtil.getUUID();
        dto.setToken(token);
        
        // redis token key:STOKENS_+UserId+"_"+token
        String keys = Constants.TOKEN_KEY + dto.getUserId() + Constants.SYMBO_UNDERLINE;
        
        // 校验是否确认登陆，并踢掉其他用户,如果返回false返回前端确认登陆提示，否则踢掉之前用户并登陆,并清掉redis
        if (!checkConfirmLogin(keys + Constants.SYMBO_NUMBERSIGN, confirmLoginFlg, result))
        {
            return false;
        }
        
        // 存入redis
        redisService.saveObjectToJson(keys + token, dto, tokenLiveTime);
        
        return true;
    }
    
    /**
     * 设置token校验返回结果
     *
     * @param token
     * @param reSetTokenLiveTimeFlg
     * @param result
     * @throws JsonProcessingException
     */
    private void setValidateTokenResult(String token, String reSetTokenLiveTimeFlg, HttpCommandResultWithData result)
        throws JsonProcessingException
    {
        // token为空视为无效token,返回错误信息
        if (StringUtil.isEmpty(token))
        {
            result.fillResult(ReturnCode.TOKEN_FAIL);
            return;
        }
        
        // 用户信息
        UserInfoDto userInfoDto = null;
        
        // token的模糊key
        String keys = Constants.TOKEN_KEY + Constants.SYMBO_NUMBERSIGN + Constants.SYMBO_UNDERLINE + token;
        
        // 获取所有记录
        Set<String> tb_set = redisService.getKeys(keys);
        
        // 返回用户信息
        for (String key : tb_set)
        {
            // 重置过期时间
            if (StringUtil.isEq(reSetTokenLiveTimeFlg, Constants.RedisLiveTimeReSetEnum.RESET.getCode()))
            {
                userInfoDto = redisService.getJsonAndRefreshExpTime(key, UserInfoDto.class, tokenLiveTime);
            }
            else
            {
                userInfoDto = redisService.getJson(key, UserInfoDto.class);
            }
            
            if (userInfoDto != null)
            {
                result.setData(userInfoDto);
                break;
            }
        }
        
        // 无效token返回错误
        if (userInfoDto == null)
        {
            result.fillResult(ReturnCode.TOKEN_FAIL);
        }
    }
    
    /**
     * 删除文件服务器上传的验证码文件,清空redis
     *
     * @param oldMap
     * @param key
     * @return
     */
    private void delGetVerifyCodeFile(Map<String, String> oldMap, String key)
    {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        
        String group = oldMap.get("group_name");
        String path = oldMap.get("path");
        String tempFilePath = oldMap.get("tempFilePath");
        
        // 删除之前的临时文件
        FileUtil.deleteJpg(tempFilePath);
        
        // 如果有上传过的文件
        if (StringUtil.isNotEmpty(group) && StringUtil.isNotEmpty(path))
        {
            param.add("group", group);
            param.add("path", path);
            RestTemplate restTemplate = new RestTemplate();
            
            // 物理删除文件
            restTemplate.postForObject(delFileUrl, param, String.class);
        }
        
        // 清空redis
        redisService.del(key);
    }
    
    /**
     * 获取登陆错误信息
     *
     * @param errorKey
     * @return
     */
    private Map getErrorMap(String errorKey)
    {
        
        // 获取登陆的错误信息和错误次数
        Map<String, String> errorMap = redisService.getJson(errorKey, LinkedMap.class);
        
        // 如果为空初始化map
        if (StringUtil.isEmpty(errorMap))
        {
            errorMap = this.initErrorMap();
        }
        
        return errorMap;
    }
    
    /**
     * 初始化错误信息
     *
     * @return
     */
    private Map initErrorMap()
    {
        Map<String, String> errorMap = new LinkedHashMap<String, String>();
        errorMap.put("errorCount", "0");
        errorMap.put("firstErrorTime", DateUtil.getCurrentMilliSeconds() + "");// 首次错误时间
        return errorMap;
    }
    
    /**
     * 校验密码错误次数
     *
     * @param result
     * @param userEntity
     * @param errorKey
     * @throws JsonProcessingException
     */
    private void setErrorTimes(HttpCommandResultWithData result, UserEntity userEntity, String errorKey,
        Map<String, String> errorMap)
        throws JsonProcessingException
    {
        // 30分钟内错误次数
        Long errorCount = this.getErrorConunt(errorMap);
        ;
        
        // 首次错误时间
        Long firstErrorTime = StringUtil.toLong(errorMap.get("firstErrorTime"));
        
        // 如果超过30分钟清空错误信息
        if (DateUtil.dateDiff(firstErrorTime) >= loginErrorLiveTime)
        {
            // 初始化错误信息
            errorMap = this.initErrorMap();
            addErrorConunt(errorMap);
            
            // 存入reids
            redisService.saveObjectToJson(errorKey, errorMap, errorRedisLiveTime);
            this.setErrorLoginResult(result, errorMap);
            return;
        }
        
        // 错误小于6次 错误次数+1(errorCount从1开始计数，所以+1 )
        if (errorCount + 1 < errorTimes)
        {
            // 错误次数+1
            addErrorConunt(errorMap);
            this.setErrorLoginResult(result, errorMap);
            
            // 存入reids
            redisService.saveObjectToJson(errorKey, errorMap, errorRedisLiveTime);
        }
        // 错误大于等于6次 锁定用户，清空redis
        else
        {
            // 锁定用户
            userEntity.setLockAccount(Constants.LockStatusEnum.LOCK.getValue());
            userRepository.save(userEntity);
            
            // 清空redis
            redisService.del(errorKey);
            result.fillResult(ReturnCode.LOCK_USER);
        }
    }
    
    /**
     * @param result
     * @param errorMap
     */
    private void setErrorLoginResult(HttpCommandResultWithData result, Map<String, String> errorMap)
    {
        long errorCount = this.getErrorConunt(errorMap);
        
        result.setMessage("登陆失败，密码错误 " + errorCount + " 次，在登陆错误 " + (errorTimes - errorCount) + "次后会锁定该用户!");
        result.setResultCode(610191);
    }
    
    /**
     * 删除所有用户登陆错误信息
     *
     * @param command
     */
    private void clearErrorMap(LoginCommand command)
    {
        String keys =
            Constants.ERROR_LOGIN_KEY + command.getVerifyCodeId() + Constants.SYMBO_UNDERLINE
                + Constants.SYMBO_NUMBERSIGN;
        // 删除所有用户登陆错误信息
        redisService.delKeys(keys);
    }
    
    /**
     * 给前端返回是否显示验证码
     *
     * @param result
     * @param errorMap
     */
    private void setIsShowVerifyCode(HttpCommandResultWithData result, Map<String, String> errorMap)
    {
        // 30分钟内错误次数
        Long errorCount = this.getErrorConunt(errorMap);
        if (errorCount >= needCheckVerifyCodeTime)
        {
            // 返回前端是否显示验证码
            Map map = new LinkedMap();
            map.put("isShowVerifyCode", true);
            result.setData(map);
        }
    }
    
    /**
     * @param errorMap
     * @return
     */
    private Long getErrorConunt(Map<String, String> errorMap)
    {
        return StringUtil.toLong(errorMap.get("errorCount"));
    }
    
    /**
     * 错误数加1
     *
     * @param errorMap
     * @return
     */
    private void addErrorConunt(Map<String, String> errorMap)
    {
        Long errorCount = StringUtil.toLong(errorMap.get("errorCount"));
        errorMap.put("errorCount", (errorCount + 1) + "");
    }
    
    /**
     * 如果有其他用户登陆时校验是否确认登陆
     *
     * @param keys
     * @param confirmLoginFlg
     * @param result
     * @return
     */
    private boolean checkConfirmLogin(String keys, String confirmLoginFlg, HttpCommandResultWithData result)
    {
        boolean isConfirmLogin = true;
        
        // 遍历redis记录
        Set<String> tb_set = redisService.getKeys(keys);
        
        // 判空
        if (!StringUtil.isEmpty(tb_set))
        {
            for (String key : tb_set)
            {
                // 已经登陆的用户信息
                UserInfoDto dto = redisService.getJson(key, UserInfoDto.class);
                
                // 其他用户token
                String token = dto.getToken();
                
                // 如果已经有用户
                if (dto != null)
                {
                    // 设置登出参数
                    LogOutCommand command = new LogOutCommand();
                    command.setToken(token);
                    
                    // 如果此用不户在线,不提示，不推送消息,清空缓存（用户点击关闭浏览器时出现此问题）
                    if (!this.checkOnline(token))
                    {
                        // 登出用户
                        this.logout(command);
                        isConfirmLogin = true;
                    }
                    else
                    {
                        // 如果确认登陆踢掉之前的用户，并发送消息推送并登陆
                        if (StringUtil.isEq(Constants.ConfirmLoginFlgEnum.CONFIRM_LOGIN.getCode(), confirmLoginFlg))
                        {
                            // 登出其他用户
                            this.logout(command);
                            
                            // 消息推送给其他登陆的用户，踢掉其他登陆的用户
                            this.pushMessage(token);
                            isConfirmLogin = true;
                            continue;
                        }
                        // 否则返回失败并设置提示用户已在其他地方登陆的提示信息，提示用户是否确认登陆
                        else
                        {
                            // 返回前端是否确认登陆提示
                            result.fillResult(ReturnCode.CONFIRM_LOGIN);
                            isConfirmLogin = false;
                            break;
                        }
                    }
                }
            }
        }
        return isConfirmLogin;
    }
    
    /**
     * 推送到前端
     *
     * @param token
     */
    private void pushMessage(String token)
    {
        Message msg = new Message();
        JSONObject json = new JSONObject();
        json.put("type", MessagePushConstants.PushType.OTHER_USER_LOGIN_IN.getCode());
        json.put("message", MessagePushConstants.UserLoginPushMsg.OTHER_USER_LOGIN_MSQ_PUSH.getMsg());
        json.put("data", "");
        msg.setData(json.toString());
        msg.setToken(token);
        try
        {
            // 消息推送
            pushClient.messagePush(msg);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    private boolean checkOnline(String token)
    {
        // 判断是否在线
        CommonResult commonResult;
        
        try
        {
            CheckOnline checkOnline = new CheckOnline();
            checkOnline.setToken(token);
            commonResult = pushClient.checkOnline(checkOnline);
            
            // 返回成功表示在线
            if (commonResult != null && commonResult.getResultCode() == ReturnCode.OK.code())
            {
                return true;
            }
            else
            {
                return false;
            }
            
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 校验第三方用户合法性
     *
     * @param userEntity
     * @param password
     * @param key
     * @param result
     * @return
     */
    private boolean checkOpenApiUser(UserEntity userEntity, String password, String key, CommonResult result)
        throws JsonProcessingException
    {
        
        // 如果用户不存返回:用户名错误
        if (userEntity == null)
        {
            result.fillResult(ReturnCode.ERROR_USERNAME);
            return false;
        }

        // 锁定状态不为空并且用户被锁定返回:用户被锁定
        if (!StringUtil.isEmpty(userEntity.getLockAccount())
            && StringUtil.isEq(userEntity.getLockAccount(), Constants.LockStatusEnum.LOCK.getValue()))
        {
            result.fillResult(ReturnCode.ACCOUNT_LOCKED);
            return false;
        }
        
        // 有效时间不为空并且不大于系统时间返回：用户过期
        if (userEntity.getTermVilidate() != null
            && (userEntity.getTermVilidate().longValue()) <= (System.currentTimeMillis() / 1000))
        {
            result.fillResult(ReturnCode.ACCOUNT_EXPIRED);
            return false;
        }
        
        // 如果密码不正确返回
        if (StringUtil.isNotEq(userEntity.getAccountPwd(), password))
        {
            result.fillResult(ReturnCode.ERROR_PASSWORD);
            return false;
        }
        return true;
    }
}
