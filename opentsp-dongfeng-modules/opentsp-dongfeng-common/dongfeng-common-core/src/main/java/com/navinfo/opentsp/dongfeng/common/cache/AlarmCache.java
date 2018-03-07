package com.navinfo.opentsp.dongfeng.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.dto.AlarmInfoData;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.tcp.AlarmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 报警提醒缓存
 *
 * @wenya
 * @create 2017-03-15 18:40
 **/
@Component
public class AlarmCache
{
    private static final Logger logger = LoggerFactory.getLogger(AlarmCache.class);
    
    @Resource
    private IRedisService redisService;
    @Resource
    private GpsInfoCache gpsInfoCache;
    
    public List<AlarmInfoData> getAlarmInfos(List<String> sims, Integer size)
    {
        // 获取所有缓存数据对象Map(key,value)
        Map<String,AlarmInfoData> map = getAllAlarmInfoMap();
        //末次位置状态等信息
        Map<String,GpsInfoData> gpsInfoMap  = gpsInfoCache.getAllGpsInfoMap();
        List<AlarmInfoData> alarms = new ArrayList<AlarmInfoData>();
        for (int i = 0; i < sims.size(); i++)
        {
            String sim = sims.get(i);
            GpsInfoData gpsInfoData = gpsInfoMap.get(sim);

            //车辆不在线跳过
            if ((gpsInfoData == null) || (gpsInfoData.getCarStatue() == null) || ((gpsInfoData.getCarStatue().intValue() & 1)  == 0)){
                continue;
            }

            for (int j = 1; j <= 5; j++)
            {
                String key =   sim + "_" + j;
                //AlarmInfoData alarm = getAlarmInfo(sim, j);
                AlarmInfoData alarm = map.get(key);
                if (null != alarm)
                {
                    alarms.add(alarm);
                }
            }
        }
        
        Collections.sort(alarms, new Comparator<AlarmInfoData>()
        {
            public int compare(AlarmInfoData o1, AlarmInfoData o2)
            {
                return o1.getAlarmDate().compareTo(o2.getAlarmDate());
            }
        });
        
        if (alarms.size() > size)
        {
            return alarms.subList(alarms.size() - size, alarms.size() - 1);
        }
        return alarms;
    }

    // 获取所有缓存数据对象Map(key,value)
    public Map<String,AlarmInfoData> getAllAlarmInfoMap(){
        Map<String,AlarmInfoData> map = redisService.getAllMapJson(CacheKeyConstants.ALARM_KEY,AlarmInfoData.class);
        return map;
    }
    
    // 获取缓存数据对象
    public AlarmInfoData getAlarmInfo(String sim, Integer type)
    {
        String key =   sim + "_" + type;
        AlarmInfoData gpsInfo = redisService.getJson(CacheKeyConstants.ALARM_KEY, key, AlarmInfoData.class);
        return gpsInfo;
    }
    
    // 位置数据转换成报警对象
    public void alarmConverter(String sim, LCLocationData.LocationData locationData)
    {
        String alarm = AlarmUtil.changeAlarm(locationData);
        if (alarm.contains(","))
        {
            String[] al = alarm.split(",");
            for (int i = 0; i < al.length; i++)
            {
                AlarmInfoData alarmremind = new AlarmInfoData();
                alarmremind.setAlarmDate(locationData.getGpsDate());
                alarmremind.setSim(sim);
                alarmremind.setLatitudes(locationData.getLatitude() * 0.000001);
                alarmremind.setLongitudes(locationData.getLongitude() * 0.000001);
                alarmremind.setAlarmType(Integer.parseInt(al[i]));
                alarmremind.setAlarmOverType(0);
                add(alarmremind);
            }
        }
        else
        {
            AlarmInfoData alarmremind = new AlarmInfoData();
            alarmremind.setSim(sim);
            alarmremind.setAlarmType(0);
            alarmremind.setAlarmDate(new Date().getTime() / 1000);
            add(alarmremind);
        }
    }
    
    public synchronized void add(AlarmInfoData alarmremind)
    {
        switch (alarmremind.getAlarmType())
        {
            case 0:// GPS无报警
                endAlarmRemind(alarmremind);
                break;
            case 4:// 解锁车报警
                putAlarmRemind(alarmremind);
                break;
            case 5:// ID不匹配
                if (alarmremind.getAlarmOverType() == 1)
                { // 报警结束
                    redisService.del(CacheKeyConstants.ALARM_KEY , alarmremind.getSim() + "_" + 5);
                }
                else
                {// GPS有报警
                    handleAlarmRemind(alarmremind);
                }
                break;
            default:// GPS有报警
                handleAlarmRemind(alarmremind);
                break;
        }
    }
    
    // 增加或更新报警
    public void putAlarmRemind(AlarmInfoData alarmremind)
    {
        try
        {
            redisService.saveObjectToJson(CacheKeyConstants.ALARM_KEY , alarmremind.getSim() + "_"
                + alarmremind.getAlarmType(),
                alarmremind);
        }
        catch (JsonProcessingException e)
        {
            logger.error(e.getMessage(),e);
        }
    }
    
    // 报警结束
    public void endAlarmRemind(AlarmInfoData alarmremind)
    {
        for (int i = 1; i <= 4; i++)
        {
            redisService.del(CacheKeyConstants.ALARM_KEY , alarmremind.getSim() + "_" + i);
        }
    }
    
    // 判断缓存中是否有次报警,如果没有新增加
    public void handleAlarmRemind(AlarmInfoData alarmremind)
    {
        AlarmInfoData ar =
            redisService.getJson(CacheKeyConstants.ALARM_KEY , alarmremind.getSim() + "_"
                + alarmremind.getAlarmType(),
                AlarmInfoData.class);
        if (ar == null)
        {
            putAlarmRemind(alarmremind);
        }
    }
    
}
