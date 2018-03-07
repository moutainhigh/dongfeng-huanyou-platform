package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.constant.BusinessJobType;
import com.navinfo.opentsp.dongfeng.common.constant.JobTypeConstant;
import com.navinfo.opentsp.dongfeng.common.constant.StationJobType;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserAttributeOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserDetailOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.UserListOutdto;
import com.navinfo.opentsp.dongfeng.system.constant.UserStateConstant;
import com.navinfo.opentsp.dongfeng.system.entity.UserEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.UserAttributePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.UserAttributeRolePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.UserListPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/08.
 */
public class UserConverter {

    private static final Logger logger = LoggerFactory.getLogger(UserConverter.class);

    public static UserListOutdto pojoToDto(UserListPojo pojo) {
        UserListOutdto dto = new UserListOutdto();
        dto.setAccountId(pojo.getAccountId());
        dto.setAccountName(pojo.getAccountName());
        if (pojo.getAccountDate() != null) {
            dto.setAccountDate(DateUtil.timeStr(pojo.getAccountDate().longValue()));
        }
        dto.setAccountLinktel(pojo.getAccountLinktel());
        cvtUserTypeAndJobType(pojo, dto);

        dto.setCreateAccount(pojo.getCreateAccount());
        dto.setLockAccount(UserStateConstant.Locked.getValue(StringUtil.toInt(pojo.getLockAccount(), UserStateConstant.Locked.UNKNOWN.getIndex())));
        if (pojo.getTermVilidate() != null) {
            long termVilidate = pojo.getTermVilidate().longValue();
            dto.setTermVilidate(DateUtil.timeStr2(termVilidate));
            if (DateUtil.getCurrentDaySeconds() <= termVilidate) {
                dto.setExpiredStatus(UserStateConstant.Expired.getValue(UserStateConstant.NOT_EXPIRED));
            } else {
                dto.setExpiredStatus(UserStateConstant.Expired.getValue(UserStateConstant.EXPIRED));
            }
        }
        dto.setPassword(pojo.getPassword());
        return dto;
    }

    private static void cvtUserTypeAndJobType(UserListPojo pojo, UserListOutdto dto) {
        if (StringUtil.isNotEmpty(pojo.getAccountType())) {
            UserTypeConstant userType = UserTypeConstant.valueOf(pojo.getAccountType());
            dto.setAccountType(userType.getName());
            if (StringUtil.isNotEmpty(pojo.getJobType())) {
                switch (userType) {
                    case CAR_FACTORY:
                        dto.setJobType(JobTypeConstant.getValue(pojo.getJobType()));
                        break;
                    case SERVICE_STATION:
                        dto.setJobType(StationJobType.getValue(pojo.getJobType()));
                        break;
                    case BUSINESS:
                        dto.setJobType(BusinessJobType.getValue(pojo.getJobType()));
                        break;
                    default:
                }
            }
        }
    }

    public static List<UserListOutdto> listPojoToDto(List<UserListPojo> list) {
        List<UserListOutdto> listDto = new ArrayList<UserListOutdto>();
        if (!ListUtil.isEmpty(list)) {
            for (UserListPojo pojo : list) {
                UserListOutdto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static UserDetailOutDto detailToDto(UserEntity entity, UserAttributePojo customerAttribute, List<UserAttributeRolePojo> roleAttribute,
                                               List<UserAttributePojo> teamAttribute, List<UserAttributePojo> stationAttribute) {
        UserDetailOutDto dto = new UserDetailOutDto();
        try {
            CopyPropUtil.copyProp(entity, dto);
            dto.setAccountType(StringUtil.valueOf(entity.getAccountType()));
            dto.setJobType(StringUtil.valueOf(entity.getJobType()));

            if (StringUtil.isNotEmpty(entity.getTermVilidate())) {
                dto.setTermVilidate(DateUtil.timeStr2(entity.getTermVilidate().longValue()));
            }

            if (StringUtil.isNotEmpty(customerAttribute)) {
                UserAttributeOutDto customerDto = new UserAttributeOutDto();
                customerDto.setCode(customerAttribute.getId());
                customerDto.setName(customerAttribute.getName());
                dto.setCustomer(customerDto);
            }

            if (StringUtil.isNotEmpty(roleAttribute)) {
                List<UserAttributeOutDto> roles = new ArrayList<UserAttributeOutDto>();
                for (UserAttributeRolePojo role : roleAttribute) {
                    UserAttributeOutDto roleDto = new UserAttributeOutDto();
                    roleDto.setCode(BigInteger.valueOf(Long.parseLong(Integer.toString(role.getId()))));
                    roleDto.setName(role.getName());
                    roles.add(roleDto);
                }
                dto.setRole(roles);
            }

            if (StringUtil.isNotEmpty(teamAttribute)) {
                dto.setTeam(pramaAttribute(teamAttribute));
            }
            if (StringUtil.isNotEmpty(stationAttribute)) {
                dto.setServiceStation(pramaAttribute(stationAttribute));
            }

        } catch (Exception e) {
            logger.error("detailToDto#copyProp for UserInfo is error: ", e);
        }
        return dto;
    }

    private static List<UserAttributeOutDto> pramaAttribute(List<UserAttributePojo> attributes) throws Exception {
        List<UserAttributeOutDto> dto = new ArrayList<UserAttributeOutDto>();
        for (UserAttributePojo attribute : attributes) {
            UserAttributeOutDto attributeDto = new UserAttributeOutDto();
            attributeDto.setCode(attribute.getId());
            attributeDto.setName(attribute.getName());
            dto.add(attributeDto);
        }
        return dto;
    }
}
