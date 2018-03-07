package com.navinfo.opentsp.dongfeng.report.converter;

import com.navinfo.opentsp.dongfeng.report.dto.StatisticsOnlineDto;
import com.navinfo.opentsp.dongfeng.report.pojo.StatisticsOnlinePojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public class StatisticsOnlineToDto {

    /****
     * 信息同步上线pojo转dto批量
     * @param pojos
     * @return
     */
    public static List<StatisticsOnlineDto> convertToDtoList(List<StatisticsOnlinePojo> pojos){
        List<StatisticsOnlineDto> result = new ArrayList<StatisticsOnlineDto>();
        StatisticsOnlineDto dto = null;
        for(StatisticsOnlinePojo pojo:pojos){
            dto = new StatisticsOnlineDto();
            if(pojo.getAutoFlag()!=null)
                dto.setAutoFlag(pojo.getAutoFlag().toString());
            if(pojo.getCarCph()!=null)
                dto.setCarCph(pojo.getCarCph());
            if(pojo.getCarDate()!=null)
                dto.setCarDate(pojo.getCarDate().longValue());
            if(pojo.getCarId()!=null)
                dto.setCarId(pojo.getCarId().toString());
            if(pojo.getCarOwner()!=null)
                dto.setCarOwner(pojo.getCarOwner());
            if(pojo.getCarTerminalId()!=null)
                dto.setCarTerId(pojo.getCarTerminalId().toString());
            if(pojo.getCarType()!=null)
                dto.setCarType(pojo.getCarType().toString());
            if(pojo.getChassisNum()!=null)
                dto.setChassisNum(pojo.getChassisNum());
            if(pojo.getCarTeamId()!=null)
                dto.setCompanyName(pojo.getCarTeamId().toString());
            if(pojo.getCarTeamId()!=null)
                dto.setDealerCode(pojo.getCarTeamId().toString());
            if(pojo.getEngineNumber()!=null)
                dto.setEngineNumber(pojo.getEngineNumber());
            if(pojo.getEngineType()!=null)
                dto.setEngineType(pojo.getEngineType());
            if(pojo.getNettingTime()!=null)
                dto.setNettingDate(pojo.getNettingTime().longValue());
            if(pojo.getNettingLat()!=null)
                dto.setNlat(pojo.getNettingLat().doubleValue());
            if(pojo.getNettingLog()!=null)
                dto.setNlog(pojo.getNettingLog().doubleValue());
            if(pojo.getSalesDate()!=null)
                dto.setSalesDate(pojo.getSalesDate().longValue());
            if(pojo.getSalesStatus()!=null)
                dto.setSalesStatusValue(pojo.getSalesStatus().toString());
            if(pojo.getCarTerminal()!=null)
                dto.setTerId(pojo.getCarTerminal().toString());
            if(pojo.getOfflineTime()!=null)
                dto.setXxsj(pojo.getOfflineTime().longValue());
            result.add(dto);
        }
        return result;
    }

    /****
     * 信息同步上线pojo转dto
     * @param pojo
     * @return
     */
    public static StatisticsOnlineDto convertToDto(StatisticsOnlinePojo pojo){
        StatisticsOnlineDto result = null;
        StatisticsOnlineDto dto = null;
        if(pojo !=null) {
            dto = new StatisticsOnlineDto();
            if(pojo.getAutoFlag()!=null)
                dto.setAutoFlag(pojo.getAutoFlag().toString());
            if(pojo.getCarCph()!=null)
                dto.setCarCph(pojo.getCarCph());
            if(pojo.getCarDate()!=null)
                dto.setCarDate(pojo.getCarDate().longValue());
            if(pojo.getCarId()!=null)
                dto.setCarId(pojo.getCarId().toString());
            if(pojo.getCarOwner()!=null)
                dto.setCarOwner(pojo.getCarOwner());
            if(pojo.getCarTerminalId()!=null)
                dto.setCarTerId(pojo.getCarTerminalId().toString());
            if(pojo.getCarType()!=null)
                dto.setCarType(pojo.getCarType().toString());
            if(pojo.getChassisNum()!=null)
                dto.setChassisNum(pojo.getChassisNum());
            if(pojo.getCarTeamId()!=null)
                dto.setCompanyName(pojo.getCarTeamId().toString());
            if(pojo.getCarTeamId()!=null)
                dto.setDealerCode(pojo.getCarTeamId().toString());
            if(pojo.getEngineNumber()!=null)
                dto.setEngineNumber(pojo.getEngineNumber());
            if(pojo.getEngineType()!=null)
                dto.setEngineType(pojo.getEngineType());
            if(pojo.getNettingTime()!=null)
                dto.setNettingDate(pojo.getNettingTime().longValue());
            if(pojo.getNettingLat()!=null)
                dto.setNlat(pojo.getNettingLat().doubleValue());
            if(pojo.getNettingLog()!=null)
                dto.setNlog(pojo.getNettingLog().doubleValue());
            if(pojo.getSalesDate()!=null)
                dto.setSalesDate(pojo.getSalesDate().longValue());
            if(pojo.getSalesStatus()!=null)
                dto.setSalesStatusValue(pojo.getSalesStatus().toString());
            if(pojo.getCarTerminal()!=null)
                dto.setTerId(pojo.getCarTerminal().toString());
            if(pojo.getOfflineTime()!=null)
                dto.setXxsj(pojo.getOfflineTime().longValue());
        }
        result = dto;
        return result;
    }


}
