package com.navinfo.opentsp.dongfeng.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.constant.RedisActionConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.CloudUtil;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GPS位置数据相关数据缓存
 *
 * @wenya
 * @create 2017-03-15 15:24
 **/
@Component
public class GpsInfoCache
{
    private static final Logger logger = LoggerFactory.getLogger(GpsInfoCache.class);

    @Resource
    private IRedisService redisService;

    // 获取所有缓存数据对象
    public List<GpsInfoData> getAllGpsInfo()
    {
        List<GpsInfoData> list = redisService.getAllListJson(CacheKeyConstants.GPS_INFO_KEY, GpsInfoData.class);
        return list;
    }

    // 获取所有缓存数据对象Map(key,value)
    public Map<String,GpsInfoData> getAllGpsInfoMap()
    {
        Map<String,GpsInfoData> map = redisService.getAllMapJson(CacheKeyConstants.GPS_INFO_KEY, GpsInfoData.class);
        return map;
    }

    // 获取缓存数据对象
    public GpsInfoData getGpsInfo(String sim)
    {
        String key = CacheKeyConstants.GPS_INFO_KEY + sim;
        GpsInfoData gpsInfo = redisService.getJson(CacheKeyConstants.GPS_INFO_KEY, sim, GpsInfoData.class);
        return gpsInfo;
    }

    // 添加里程，油量信息
    public void addGpsInfo(String sim, LCLocationData.LocationData locationData) throws JsonProcessingException
    {
        if (locationData == null)
        {
            return;
        }
        String key = CacheKeyConstants.GPS_INFO_KEY + sim;
        // 获取里程油量信息
        GpsInfoData gpsInfo = getMilOil(sim, locationData);
        if (gpsInfo == null)
        {
            return;
        }

        // 增加经纬度
        gpsInfo.setLat(locationData.getLatitude());
        gpsInfo.setLng(locationData.getLongitude());

        // 添加最新gps时间
        gpsInfo.setGpsDate(locationData.getGpsDate());

        // 打印key
        logger.debug("GPS_INFO_KEY_MILOIL IS:" + key);
        redisService.saveObjectToJson(CacheKeyConstants.GPS_INFO_KEY, sim, gpsInfo);
    }

    public void addGpsInfo(String sim, GpsInfoData gpsInfo) throws JsonProcessingException
    {
        redisService.saveObjectToJson(CacheKeyConstants.GPS_INFO_KEY, sim, gpsInfo);
    }



    /**
     * 添加里程，油量信息-管道方式
     * @param sim
     * @param locationData
     * @param params
     * @throws JsonProcessingException
     */
    public void commitGpsInfo(String sim, LCLocationData.LocationData locationData,List<Map> params) throws JsonProcessingException
    {
        if (locationData == null)
        {
            return;
        }
        String key = CacheKeyConstants.GPS_INFO_KEY + sim;
        // 获取里程油量信息
        GpsInfoData gpsInfo = getMilOil(sim, locationData);
        if (gpsInfo == null)
        {
            return;
        }

        // 增加经纬度
        gpsInfo.setLat(locationData.getLatitude());
        gpsInfo.setLng(locationData.getLongitude());

        // 添加最新gps时间
        gpsInfo.setGpsDate(locationData.getGpsDate());

        // 打印key
        logger.debug("GPS_INFO_KEY_MILOIL IS:" + key);
        Map param = new HashMap();
        param.put(RedisActionConstant.ACTION,RedisActionConstant.RedisActionType.HSET.getValue());
        param.put(RedisActionConstant.KEY1,CacheKeyConstants.GPS_INFO_KEY);
        param.put(RedisActionConstant.KEY2,sim);
        param.put(RedisActionConstant.VALUE, JsonUtil.toJson(gpsInfo));
        params.add(param);
    }

    // 添加里程，油量信息
    private GpsInfoData getMilOil(String sim, LCLocationData.LocationData locationData)
    {
        Double tempMileage = null; // 总里程
        Double tempOil = null; // 当前油量
        Double tempRunTime = null; // 运行时间
        Double tempOilCons = null; // 总油耗
        long accStatus = locationData.getStatus();
        if ((accStatus & 0x000001) != 0)
        {// acc开的时候替换缓存
            if (null != locationData.getStatusAddition() && null != locationData.getStatusAddition().getStatusList() && locationData.getStatusAddition().getStatusList().size() > 0)
            {
                LCLocationData.VehicleStatusAddition vstatus = locationData.getStatusAddition();
                List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
                for (LCVehicleStatusData.VehicleStatusData status : vlists)
                {
                    if(status.getTypes() == null){
                        continue;
                    }
                    int t = status.getTypes().getNumber();
//                    if (t == LCStatusType.StatusType.mileage_VALUE)
//                    {
//                        tempMileage = status.getStatusValue() * 0.01;// 整车里程（Km）
//                    }
//                    else
                    if (t == LCStatusType.StatusType.oilValue_VALUE)
                    {
                        tempOil = status.getStatusValue() * 0.01 * 0.01;// 当前油量：单位是“百分比”
                    }
                    else if (t == LCStatusType.StatusType.cumulativeRunningTime_VALUE)
                    {
                        tempRunTime = status.getStatusValue() * 0.01;//发动机累计运行时长（s）
                    }
//                    else if (t == LCStatusType.StatusType.totalFuelConsumption_VALUE)
//                    {
//                        tempOilCons = status.getStatusValue() * 0.01;//总油耗
//                    }
                }
                tempOilCons = (double)locationData.getStandardFuelCon();
                //标准里程
                tempMileage = (double)locationData.getStandardMileage();// 整车里程（Km）
            }
        }
        GpsInfoData gpsInfo = getGpsInfo(sim);
        // 当位置数据上报的数值无效时，不更新缓存
        if (tempMileage != null || tempOil != null)
        {
            if (gpsInfo == null)
            {
                gpsInfo = new GpsInfoData();
            }
            if (tempMileage != null)
            {
                gpsInfo.setTempMileage(tempMileage);
            }
            if (tempOil != null)
            {
                gpsInfo.setTempOil(tempOil);
            }
            if(tempRunTime != null)
            {
                gpsInfo.setTempRunTime(tempRunTime);
            }
            if(tempOilCons != null)
            {
                gpsInfo.setTempOilCons(tempOilCons);
            }
        }
        //添加气量
        if (gpsInfo != null)
        {
            gpsInfo.setResGas(CloudUtil.getResGas(locationData));
        }
        return gpsInfo;
    }
    /*
    * 总里程
    */
    public double getTTMile(LCLocationData.LocationData gpslist){
        //仪表行驶
        long ybmile = 0;
        //ECU里程
        long ecumile = 0;
        //GPS里程
        long gpsmile = gpslist.getMileage();
        LCLocationData.VehicleStatusAddition vstatus = gpslist.getStatusAddition();
        if(vstatus != null){
            List<LCVehicleStatusData.VehicleStatusData> vlists = vstatus.getStatusList();
            if(vlists != null){
                for (LCVehicleStatusData.VehicleStatusData status : vlists)
                {
                    if(status.getTypes() == null){
                        continue;
                    }
                    int t = status.getTypes().getNumber();
                    if (t == LCStatusType.StatusType.mileage_VALUE)
                    {
                        ecumile = status.getStatusValue();
                        continue;
                    }

                    if (t == LCStatusType.StatusType.mileageDD_VALUE)
                    {
                        ybmile = status.getStatusValue();
                        continue;
                    }
                }
            }
        }

        //实际返回里程
        double lastMile = 0;
        if(ybmile!=0l){
            lastMile = NumberFormatUtil.getDoubleValueData((double)ybmile/100, 2);
            return lastMile;
        }
        if(ecumile!=0l){
            lastMile = NumberFormatUtil.getDoubleValueData((double)ecumile/100, 2);
            return lastMile;
        }
        if(gpsmile!=0l){
            lastMile = NumberFormatUtil.getDoubleValueData((double)gpsmile/1000, 2);
            return lastMile;
        }
        return lastMile;
    }
}
