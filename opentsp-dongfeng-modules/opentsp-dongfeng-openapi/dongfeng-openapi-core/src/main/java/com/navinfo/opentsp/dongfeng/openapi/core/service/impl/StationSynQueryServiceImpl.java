package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.station.StationSynQueryCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.StationPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IStationSynQueryService;
import com.navinfo.opentsp.dongfeng.openapi.dto.station.StationDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class StationSynQueryServiceImpl  extends BaseService implements IStationSynQueryService {
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public HttpCommandResultWithData stationSynQuery(StationSynQueryCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> conMap = new HashMap<>();
        if (StringUtil.isNotEmpty(command.getBeginTime())) {//时间-开始
            conMap.put("beginTime", DateUtil.strTimeChangeLong(command.getBeginTime()));
        }
        if (StringUtil.isNotEmpty(command.getEndTime())) {//时间-结束
            conMap.put("endTime", DateUtil.strTimeChangeLong(command.getEndTime()));
        }
        List<StationPojo> lists = dao.sqlFind("queryStationByParam", conMap, StationPojo.class);
        List<StationDto> resultList = new ArrayList<StationDto>();
        StationDto stationDto = null;
        for (StationPojo pojo :lists){
            stationDto = new StationDto();
            try {
                CopyPropUtil.copyProp(pojo,stationDto);
            } catch (Exception e) {
                logger.error("StationSynQueryServiceImpl ====> stationSynQuery : ", e);
            }
            //经度纬度处理
            if(null!=pojo.getStationLon()){
                stationDto.setStationLon(NumberFormatUtil.getLatitudeOrLongitude(pojo.getStationLon().intValue()));
            }else{
                stationDto.setStationLon(0);
            }
            if(null!=pojo.getStationLat()){
                stationDto.setStationLat(NumberFormatUtil.getLatitudeOrLongitude(pojo.getStationLat().intValue()));
            }else{
                stationDto.setStationLat(0);
            }
            resultList.add(stationDto);
        }
        result.setData(resultList);
        return result;
    }
}
