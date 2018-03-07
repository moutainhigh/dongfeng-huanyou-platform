package com.navinfo.opentsp.dongfeng.openapi.core.converter.time;

import com.navinfo.opentsp.dongfeng.openapi.core.pojo.SecondStationPoJo;
import com.navinfo.opentsp.dongfeng.openapi.dto.time.SecondStationDto;

import java.util.ArrayList;
import java.util.List;

public class SecondStationToDto {
    public static List<SecondStationDto> convertList(List<SecondStationPoJo> poJo2) {
        List<SecondStationDto> dtos = new ArrayList<SecondStationDto>();
        for (SecondStationPoJo secondStationPoJo : poJo2) {
            dtos.add(convert(secondStationPoJo));
        }
        return dtos;
    }

    public static SecondStationDto convert(SecondStationPoJo poJo2) {
        SecondStationDto dto = new SecondStationDto();
		dto.setStayTime(poJo2.getStayTime() != null ? poJo2.getStayTime().longValue() : null);
		dto.setAeraId(poJo2.getAeraId() != null ? poJo2.getAeraId().longValue() : null);
		dto.setAeraType(poJo2.getAeraType());
		dto.setEnterTime(poJo2.getEnterTime() != null ? poJo2.getEnterTime().longValue() : null);
		dto.setOutTime(poJo2.getOutTime() != null ? poJo2.getOutTime().longValue() : null);
        return dto;
    }

}
