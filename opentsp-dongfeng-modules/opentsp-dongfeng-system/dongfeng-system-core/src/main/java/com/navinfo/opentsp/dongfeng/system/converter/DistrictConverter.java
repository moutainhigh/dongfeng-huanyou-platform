package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.DistrictListOutdto;
import com.navinfo.opentsp.dongfeng.system.pojo.DistrictPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunyu on 2017/3/15.
 */
public class DistrictConverter {

    /**
     * pojo转dto
     */
    public static DistrictListOutdto pojoToDto(DistrictPojo pojo) {
        DistrictListOutdto dto = new DistrictListOutdto();
        dto.setId(pojo.getTid());
        dto.settName(pojo.getTname());
        dto.setParentId(pojo.getParentId());
        dto.setTlinkMan(pojo.getTlinkman());
        dto.setTlinkTel(pojo.getTlinktel());
        dto.setpName(pojo.getPname());
        return dto;
    }

    public static List<DistrictListOutdto> listPojoToDto(List<DistrictPojo> list) {
        List<DistrictListOutdto> listDto = new ArrayList<DistrictListOutdto>();
        if (!list.isEmpty()) {
            for (DistrictPojo pojo : list) {
                DistrictListOutdto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }
}