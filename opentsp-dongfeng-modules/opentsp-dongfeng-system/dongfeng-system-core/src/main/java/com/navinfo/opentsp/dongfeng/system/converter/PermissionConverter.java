package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.PermissionTreeOutdto;
import com.navinfo.opentsp.dongfeng.system.pojo.PermissionTreePojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/15.
 */
public class PermissionConverter {

    public static PermissionTreeOutdto pojoToDto (PermissionTreePojo pojo) {
        PermissionTreeOutdto dto = new PermissionTreeOutdto();
        try {
            CopyPropUtil.copyProp(pojo, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    public static List<PermissionTreeOutdto> listPojoToDto (List<PermissionTreePojo> list) {
        List<PermissionTreeOutdto> listDto = new ArrayList<PermissionTreeOutdto>();
        if (!StringUtil.isEmpty(list)) {
            for (PermissionTreePojo pojo: list) {
                PermissionTreeOutdto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }
}
