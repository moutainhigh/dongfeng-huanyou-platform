package com.navinfo.opentsp.dongfeng.report.converter.general;

import com.navinfo.opentsp.dongfeng.report.dto.general.QueryBreakDownRemindDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryBreakDownPojo;

import java.util.ArrayList;
import java.util.List;

public class BreakDownRemindDto {

    public static List<QueryBreakDownRemindDto> convert(List<QueryBreakDownPojo> list) {
        List<QueryBreakDownRemindDto> dtos = new ArrayList<QueryBreakDownRemindDto>();
        for (QueryBreakDownPojo pojo : list) {
            QueryBreakDownRemindDto dto = new QueryBreakDownRemindDto();
            dto.setCarId(pojo.getId().longValue());
            dto.setChassisNum(pojo.getChassisNum());
            dto.setCarCph(pojo.getPlateNum());
            dto.setTerId(pojo.getBdTerCode());
            dto.setCarTerId(pojo.getFkTerCode());
            dto.setCompanyName(pojo.gettName());
            dto.setCarOwner(pojo.getBusinessName());
            dto.setCarType(pojo.getCarModel());
            if (pojo.geteType() != null && !pojo.geteType().isEmpty()) {
                dto.setEngineType(Integer.parseInt(pojo.geteType()));
            }
            dto.setEngineTypeStr(pojo.getEngineNumber());
            dto.setEngineNumber(pojo.getEngineType());
            dto.setSpnFmi(pojo.getSpnFmi());
            dto.setOccurDate(pojo.getOccurDate());
            dto.setbLoction(pojo.getbLoction());
            dto.setbLog(pojo.getbLog());
            dto.setbLat(pojo.getbLat());
            dto.setBreakdownDis(pojo.getBreakdownDis());

            //增加维修方案和维修成本
            dto.setMaintenanceSchedule(pojo.getMaintenanceSchedule());
            dto.setMaintenanceCost(pojo.getMaintenanceCost());

            dtos.add(dto);
        }
        return dtos;
    }
}
