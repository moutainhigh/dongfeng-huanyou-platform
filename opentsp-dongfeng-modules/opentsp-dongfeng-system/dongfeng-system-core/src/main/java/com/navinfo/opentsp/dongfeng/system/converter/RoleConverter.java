package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.PermissionTreeOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.RoleDetailOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.RoleListOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.RoleOutdto;
import com.navinfo.opentsp.dongfeng.system.entity.RoleEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.PermissionTreePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.RoleListPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/08.
 */
public class RoleConverter {

    public static RoleOutdto entityToDto (RoleEntity entity, BaseData base) {
        RoleOutdto dto = new RoleOutdto();
        dto.setCode(entity.getrCode());
        dto.setName(entity.getrName());
        dto.setRoleType(base.getValue());
        return dto;
    }

    public static List<RoleOutdto> listEntityToDto (List<RoleEntity> list, BaseData base) {
        List<RoleOutdto> listDto = new ArrayList<RoleOutdto>();
        if (list != null) {
            for (RoleEntity entity: list) {
                RoleOutdto dto = entityToDto(entity, base);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static RoleListOutDto pojoToDto (RoleListPojo pojo) {
        RoleListOutDto dto = new RoleListOutDto();
        try {
            CopyPropUtil.copyProp(pojo, dto);
            if (pojo.getRoleType().intValue() == UserTypeConstant.SYSTEM_ADMIN.getCode()) {
                dto.setRoleType(UserTypeConstant.SYSTEM_ADMIN.getName());
            } else if (pojo.getRoleType().intValue() == UserTypeConstant.CAR_FACTORY.getCode()) {
                dto.setRoleType(UserTypeConstant.CAR_FACTORY.getName());
            } else if (pojo.getRoleType().intValue() == UserTypeConstant.SERVICE_STATION.getCode()) {
                dto.setRoleType(UserTypeConstant.SERVICE_STATION.getName());
            } else if (pojo.getRoleType().intValue() == UserTypeConstant.TRANSPORT.getCode()) {
                dto.setRoleType(UserTypeConstant.TRANSPORT.getName());
            } else if (pojo.getRoleType().intValue() == UserTypeConstant.BUSINESS.getCode()) {
                dto.setRoleType(UserTypeConstant.BUSINESS.getName());
            } else if (pojo.getRoleType().intValue() == UserTypeConstant.FINANCE_COMPANY.getCode()) {
                dto.setRoleType(UserTypeConstant.FINANCE_COMPANY.getName());
            }
            if (pojo.getCreateTime() != null) {
                dto.setCreateTime(DateUtil.format(DateUtil.time_pattern, pojo.getCreateTime()));
            }
            if (pojo.getUpdateTime() != null) {
                dto.setUpdateTime(DateUtil.format(DateUtil.time_pattern, pojo.getUpdateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    public static List<RoleListOutDto> listPojoToDto (List<RoleListPojo> list) {
        List<RoleListOutDto> listDto = new ArrayList<RoleListOutDto>();
        if (!StringUtil.isEmpty(list)) {
            for (RoleListPojo pojo: list) {
                RoleListOutDto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static RoleDetailOutdto detailToDto (RoleEntity entity, List<PermissionTreePojo> permissions) {
        List<PermissionTreeOutdto> permission = new ArrayList<PermissionTreeOutdto>();
        RoleDetailOutdto dto = new RoleDetailOutdto();
        dto.setId(entity.getrId());
        dto.setName(entity.getrName());
        dto.setRoleType(entity.getRoleType().toString());
        for (PermissionTreePojo pPojo : permissions) {
            PermissionTreeOutdto pDto = new PermissionTreeOutdto();
            try {
                CopyPropUtil.copyProp(pPojo, pDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            permission.add(pDto);
        }
        dto.setPermissions(permission);
        return dto;
    }
}
