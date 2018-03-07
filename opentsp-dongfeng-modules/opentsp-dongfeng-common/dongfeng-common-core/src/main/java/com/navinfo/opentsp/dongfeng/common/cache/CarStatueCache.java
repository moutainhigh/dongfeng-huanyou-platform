package com.navinfo.opentsp.dongfeng.common.cache;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.RedisActionConstant;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆在线状态缓存 车辆在线状态（在线行驶111(7)，在线停车011(3)，在线不定位001(1)，不在线行驶110(6),不在线停车010(2)，不在线不定位000(0)） 第一位表示在线状态（0：不在线，1：在线）
 * 第二位表示定位状态（0：不定位，1：定位） 第三位表示行驶状态（0：停车，1：行驶）
 *
 * @wenya
 * @create 2017-03-15 12:06
 **/
@Component
public class CarStatueCache
{
    
    private static final Logger logger = LoggerFactory.getLogger(CarStatueCache.class);
    
    @Resource
    private IRedisService redisService;
    
    @Autowired
    private GpsCache gpsCache;
    
    @Autowired
    private GpsInfoCache gpsInfoCache;
    
    /**
     * 添加车辆在线状态
     *
     * @param sim
     * @param locationData
     */
    public Integer getStatueByLoc(String sim, LCLocationData.LocationData locationData)
    {
        if (locationData == null)
        {
            return null;
        }
        if (locationData != null && System.currentTimeMillis() / 1000 - locationData.getGpsDate() >= 600)
        { // 10分钟以前的补传数据，不改变在线状态
            return null;
        }
        Integer statue = null;
        if (locationData.getLatitude() <= 0 || locationData.getLongitude() <= 0)
        { // 不定位状态，显示最新有效状态
          // LocationData oldLocationDate = gpsCache.getLastGpsVlid(sim);
          // statue = putOnlineStatue(oldLocationDate);
            statue = changeOnlineStatue(sim);
        }
        else if (locationData.getSpeed() == 0)
        { // 在线停车
            statue = Constants.CarState.VehicleStatusOnlineStop.getValue();
        }
        else if (locationData.getSpeed() > 0)
        { // 在线行驶
            statue = Constants.CarState.VehicleStatusOnline.getValue();
        }
        return statue;
    }
    
    // 在线状态只需修改最低位改为在线
    private Integer changeOnlineStatue(String sim)
    {
        GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(sim);
        if (gpsInfo == null)
        {
            return Constants.CarState.VehicleStatusOfflineInvalid.getValue();
        }
        
        Integer statue = gpsInfo.getCarStatue();
        if (statue == null)
        {// 以前缓存中无有效在线状态
            statue = Constants.CarState.VehicleStatusOnlineInvalid.getValue();
        }
        else
        {
            statue = statue | 1;
        }
        return statue;
    }
    
    // 增加上线状态
    private Integer putOnlineStatue(LCLocationData.LocationData oldLocationDate)
    {
        Integer statue = null;
        if (oldLocationDate == null)
        { // 以前缓存中无有效在线状态
            statue = Constants.CarState.VehicleStatusOnlineInvalid.getValue();
        }
        else
        {
            if (oldLocationDate.getSpeed() == 0)
            {
                statue = Constants.CarState.VehicleStatusOnlineStop.getValue();
            }
            else if (oldLocationDate.getSpeed() > 0)
            {
                statue = Constants.CarState.VehicleStatusOnline.getValue();
            }
            else
            {
                statue = Constants.CarState.VehicleStatusOnlineInvalid.getValue();
            }
        }
        return statue;
    }
    
    // 通过上下线指令，获取车辆状态
    public Integer getStatueByFlag(String sim, boolean status)
    {
        // LocationData locationDate = gpsCache.getLastGpsVlid(sim);
        Integer statue = null;
        if (status)
        { // true 上线
          // statue = putOnlineStatue(locationDate);
            statue = changeOnlineStatue(sim);
        }
        else
        { // 下线
            statue = changeOfflineStatue(sim);
            /*
             * if (locationDate == null) { // 以前缓存中无有效在线状态 statue =
             * Constants.CarState.VehicleStatusOfflineInvalid.getValue(); } else { if (locationDate.getSpeed() == 0) {
             * statue = Constants.CarState.VehicleStatusOfflineStop.getValue(); } else if (locationDate.getSpeed() > 0)
             * { statue = Constants.CarState.VehicleStatusOffline.getValue(); } else { statue =
             * Constants.CarState.VehicleStatusOfflineInvalid.getValue(); } }
             */
        }
        return statue;
    }
    
    // 在线状态只需修改最低位改为不在线
    private Integer changeOfflineStatue(String sim)
    {
        GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(sim);
        if (gpsInfo == null)
        {
            return Constants.CarState.VehicleStatusOfflineInvalid.getValue();
        }
        Integer statue = gpsInfo.getCarStatue();
        if (statue == null)
        {// 以前缓存中无有效在线状态
            statue = Constants.CarState.VehicleStatusOfflineInvalid.getValue();
        }
        else
        {
            statue = statue & 110;
        }
        return statue;
    }
    
    public void addCarStatue(String sim, LCLocationData.LocationData locationData, Boolean flag)
    {
		GpsInfoData gpsInfo = null;
		try
        {
			String key = CacheKeyConstants.GPS_INFO_KEY + sim;
			Integer statue = null;
			if (null == flag)
			{ // 通过位置数据改变车辆状态
				statue = getStatueByLoc(sim, locationData);
			}
			else
			{ // 通过上下线指令改变车辆状态
				statue = getStatueByFlag(sim, flag);
			}
			if (statue != null)
            {
				gpsInfo = gpsInfoCache.getGpsInfo(sim);
				if (gpsInfo == null)
                {
					gpsInfo = new GpsInfoData();
				}
				gpsInfo.setCarStatue(statue);

				// 设置最新gps时间
				if (locationData == null)
				{
					byte[] dataByte = redisService.getByte(CacheKeyConstants.GPS_KEY, sim);
					if (dataByte != null)
                    {
						locationData = LCLocationData.LocationData.parseFrom(dataByte);
                    }
                }
				if (locationData != null)
				{
					gpsInfo.setGpsDate(locationData.getGpsDate());
				}
				// 打印key
				logger.debug("GPS_INFO_KEY_STATUE IS:" + key);
            }
			redisService.saveObjectToJson(CacheKeyConstants.GPS_INFO_KEY, sim, gpsInfo);
		}
		catch (Exception e)
            {
			logger.error("添加车辆相关信息缓存异常", e);
            }
        }

    /**
     *  添加车辆状态-管道方式
      * @param sim
     * @param locationData
     * @param flag
     */
    public void commitCarStatue(String sim, LCLocationData.LocationData locationData, Boolean flag, List<Map> params)
    {
        try
        {
            GpsInfoData gpsInfo = null;
            String key = CacheKeyConstants.GPS_INFO_KEY + sim;
            Integer statue = null;
            if (null == flag)
            { // 通过位置数据改变车辆状态
                statue = getStatueByLoc(sim, locationData);
            }
            else
            { // 通过上下线指令改变车辆状态
                statue = getStatueByFlag(sim, flag);
            }
            if (statue != null)
            {
                gpsInfo = gpsInfoCache.getGpsInfo(sim);
                if (gpsInfo == null)
                {
                    gpsInfo = new GpsInfoData();
                }
                gpsInfo.setCarStatue(statue);

                // 设置最新gps时间
                if (locationData == null)
                {
                    byte[] dataByte = redisService.getByte(CacheKeyConstants.GPS_KEY, sim);
                    if (dataByte != null)
                    {
                        locationData = LCLocationData.LocationData.parseFrom(dataByte);
                    }
                }
                if (locationData != null)
                {
                    gpsInfo.setGpsDate(locationData.getGpsDate());
                }
                // 打印key
                logger.debug("GPS_INFO_KEY_STATUE IS:" + key);
            }
            Map param = new HashMap();
            param.put(RedisActionConstant.ACTION,RedisActionConstant.RedisActionType.HSET.getValue());
            param.put(RedisActionConstant.KEY1,CacheKeyConstants.GPS_INFO_KEY);
            param.put(RedisActionConstant.KEY2,sim);
            param.put(RedisActionConstant.VALUE, JsonUtil.toJson(gpsInfo));
            params.add(param);
        }
        catch (Exception e)
        {
            logger.error("添加车辆相关信息缓存异常", e);
        }
    }
}
