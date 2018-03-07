package com.navinfo.opentsp.dongfeng.report.converter.station;

import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.constant.Constant;
import com.navinfo.opentsp.dongfeng.report.dto.station.QueryStationOverTimeAlarmsDto;
import com.navinfo.opentsp.dongfeng.report.pojo.station.StationOverTimeAlarmsPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StationOverTimeAlarmsToDto {

    public static List<QueryStationOverTimeAlarmsDto> convert(List<StationOverTimeAlarmsPojo> datas) {
        List<QueryStationOverTimeAlarmsDto> results = new ArrayList<QueryStationOverTimeAlarmsDto>();
        if (ListUtil.isEmpty(datas)) {
            return results;
        }

        for (StationOverTimeAlarmsPojo alarm : datas) {
            QueryStationOverTimeAlarmsDto dto = new QueryStationOverTimeAlarmsDto();
            dto.setChassisNum(alarm.getChassisNum());
            dto.setPlateNumber(alarm.getPlateNumber());
            dto.setBeidouMachineID(StringUtil.nvl(alarm.getBeidouMachineID()));
            dto.setFkControllerID(StringUtil.nvl(alarm.getFkControllerID()));
            dto.setDealer(alarm.getDealer());
            dto.setClient(alarm.getClient());
            dto.setContacts(alarm.getContacts());
            dto.setCarModel(alarm.getCarModel());
            dto.setEngineModel(alarm.getEngineModel());
            dto.setEngineType(alarm.getEngineType());
            dto.setStationName(alarm.getStationName());
            dto.setStationAddress(alarm.getStationAddress());
            dto.setInboundTime(DateUtils.formatDate(alarm.getInboundTime()));
            dto.setLastAlarmTime(DateUtils.formatDate(alarm.getLastAlarmTime()));
            dto.setParkingTimes(alarm.getParkingTimes());
            dto.setParkingThreshold(alarm.getParkingThreshold());
            dto.setIsOvertime(Constant.OverTimeEnum.getValue(alarm.getIsOvertime()));
            dto.setParkId(alarm.getParkId());
            results.add(dto);
        }
        Collections.sort(results);
        return results;
    }
}
