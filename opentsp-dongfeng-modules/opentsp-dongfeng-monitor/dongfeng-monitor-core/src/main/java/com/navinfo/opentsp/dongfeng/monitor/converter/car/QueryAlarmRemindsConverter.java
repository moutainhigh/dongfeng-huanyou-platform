package com.navinfo.opentsp.dongfeng.monitor.converter.car;

import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryAlarmRemindsDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryAlarmRemindsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 轨迹查询  POJO>>DTO
 * @author fengwm
 * @version 1.0
 * @date 2017-03-09
 * @modify
 * @copyright Navi Tsp
 */

public class QueryAlarmRemindsConverter {
    /**
     * JPJO>>DTO
     * @param entityList
     * @return
     */
    public static List<QueryAlarmRemindsDto> convertToDtoList(List<QueryAlarmRemindsPojo> entityList) {
        List<QueryAlarmRemindsDto> result = new ArrayList<QueryAlarmRemindsDto>();
        for (QueryAlarmRemindsPojo entity : entityList) {
            QueryAlarmRemindsDto alarmRemindDto = new QueryAlarmRemindsDto();
            alarmRemindDto.setCarId(entity.getCarId().longValue());
            alarmRemindDto.setChassisNum(entity.getChassisNum());
            alarmRemindDto.setLat(entity.getLat());
            alarmRemindDto.setLng(entity.getLng());
            alarmRemindDto.setAlarmType(entity.getAlarmType());
            alarmRemindDto.setAlarmTypeStr(entity.getAlarmTypeStr());
            alarmRemindDto.setLockCarReason(entity.getLockCarReason());
            alarmRemindDto.setLockCar(entity.getLockCar());
            alarmRemindDto.setAlarmDate(entity.getAlarmDateStr());
            alarmRemindDto.setAlarmTimes(entity.getAlarmtimes());
            result.add(alarmRemindDto);
        }
        return result;
    }
}