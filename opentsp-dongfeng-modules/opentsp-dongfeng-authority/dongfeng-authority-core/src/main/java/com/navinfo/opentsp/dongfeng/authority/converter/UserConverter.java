package com.navinfo.opentsp.dongfeng.authority.converter;

import com.navinfo.opentsp.dongfeng.authority.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.common.dto.UserInfoDto;

/**
 * Created by zhangy on 2017/03/08.
 */
public class UserConverter
{
    
    public static UserInfoDto enyityToDto(UserEntity userEntity)
    {
        UserInfoDto dto = new UserInfoDto();
        dto.setUserId(userEntity.getAccountId().longValue());
        dto.setUsername(userEntity.getAccountName());
        dto.setType(userEntity.getAccountType());
        dto.setEmail(userEntity.getAccountEmail());
        dto.setPassword(userEntity.getAccountPwd());
        dto.setPassword(userEntity.getAccountPwd());
        dto.setAccountImage(userEntity.getAccountImage());
        dto.setTerAuthority(userEntity.getTerAuthority());
        if (userEntity.getJobType() != null)
        {
            dto.setJobType(userEntity.getJobType().intValue());
        }
        return dto;
    }
}
