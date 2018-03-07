package com.navinfo.opentsp.dongfeng.common.cache;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCLocationData.LocationData;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.constant.RedisActionConstant;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.tcp.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gps位置数据缓存
 *
 * @author zhangy
 * @date 7/26
 * @common key --tAutoCommunicationId --和位置云通信唯一标示 value --LocationData ---Gps protobuf对象
 */
@Component
public class GpsCache
{
    private static final Logger logger = LoggerFactory.getLogger(GpsCache.class);

    @Resource
    private IRedisService redisService;

    @Autowired
    private GpsInfoCache gpsInfoCache;

    // 获取所有缓存数据对象Map(key,value)
    public Map<String,LCLocationData.LocationData> getAllLastGpsMap()
    {
        Map<String,LCLocationData.LocationData> map_loc = new HashMap<String,LCLocationData.LocationData>();
        Map<String,byte[]> map = redisService.getAllMapByte(CacheKeyConstants.GPS_VALID_KEY);
        for (String key : map.keySet()){
            try
            {
                map_loc.put(key, LCLocationData.LocationData.parseFrom(map.get(key)));
            }
            catch (InvalidProtocolBufferException e)
            {
                logger.error(e.getMessage(),e);
            }
        }
        return map_loc;
    }

    /**
     * 获取末次有效位置缓存
     *
     * @param sim
     */
    public LocationData getLastGpsVlid(String sim)
    {
        String key = CacheKeyConstants.GPS_VALID_KEY + sim;
        byte[] dataByte = redisService.getByte(CacheKeyConstants.GPS_VALID_KEY,sim);
        if(dataByte == null){
            return null;
        }
        LocationData data = null;
        try
        {
            data = LocationData.parseFrom(dataByte);
        }
        catch (InvalidProtocolBufferException e)
        {
            logger.error(e.getMessage(),e);
        }
        return data;
    }

    /**
     * 添加末次位置缓存
     *
     * @param sim
     * @param locationDataByte
     * @param locationData
     */
    public void addLastGps(String sim, byte[] locationDataByte, LocationData locationData)
    {
		try
		{
			if (locationData == null || locationData.getGpsDate() < 0)
			{ // gps时间必须>0
				return;
			}
			LocationData oldLocationDate = getLastGps(sim);
			if (oldLocationDate != null && oldLocationDate.getGpsDate() > locationData.getGpsDate())
			{ // gps时间不是最新的，补传数据
				return;
			}
			String key = CacheKeyConstants.GPS_KEY + sim;
			// 打印key
			logger.debug("GPS_KEY IS:" + key);

			redisService.saveByteToString(CacheKeyConstants.GPS_KEY, sim, locationDataByte);
        } catch (Exception e) {
			logger.error("添加末次位置缓存异常", e);
        }
    }

    /**
     * 添加末次位置缓存-管道方式
     *
     * @param sim
     * @param locationDataByte
     * @param locationData
     */
    public void commitLastGps(String sim, byte[] locationDataByte, LocationData locationData,List<Map> params)
    {
        try
        {
            if (locationData == null || locationData.getGpsDate() < 0)
            { // gps时间必须>0
                return;
            }
            LocationData oldLocationDate = getLastGps(sim);
            if (oldLocationDate != null && oldLocationDate.getGpsDate() > locationData.getGpsDate())
            { // gps时间不是最新的，补传数据
                return;
            }
            String key = CacheKeyConstants.GPS_KEY + sim;
            // 打印key
            logger.debug("GPS_KEY IS:" + key);
            Map param = new HashMap();
            param.put(RedisActionConstant.ACTION,RedisActionConstant.RedisActionType.HSET.getValue());
            param.put(RedisActionConstant.KEY1,CacheKeyConstants.GPS_KEY);
            param.put(RedisActionConstant.KEY2,sim);
            param.put(RedisActionConstant.VALUE, JsonUtil.toJson(Convert.bytesToHexString(locationDataByte)));
            params.add(param);
        } catch (Exception e) {
            logger.error("添加末次位置缓存异常", e);
        }
    }

    /**
     * 获取末次位置缓存
     *
     * @param sim
     */
    public LocationData getLastGps(String sim)
    {
        String key = CacheKeyConstants.GPS_KEY + sim;
        // 打印key
        logger.debug("GPS_KEY IS:" + key);
        byte[] dataByte = redisService.getByte(CacheKeyConstants.GPS_KEY ,sim);
        if(dataByte == null){
            return null;
        }

        LocationData data = null;
        try
        {
            data = LocationData.parseFrom(dataByte);
        }
        catch (InvalidProtocolBufferException e)
        {
            logger.error(e.getMessage(), e);
        }
        return data;
    }

    /**
     * 添加末次有效位置缓存
     *
     * @param sim
     * @param locationDataByte
     * @param locationData
     */
    public void addLastGpsValid(String sim, byte[] locationDataByte, LocationData locationData)
    {
		try
		{
			if (locationData == null || locationData.getGpsDate() < 0)
			{ // gps时间必须>0
				return;
			}
			if (locationData.getLatitude() <= 0 || locationData.getLongitude() <= 0)
			{ // 不是有效位置数据
				return;
			}
			LocationData oldLocationDate = getLastGpsVlid(sim);
			if (oldLocationDate != null && oldLocationDate.getGpsDate() > locationData.getGpsDate())
			{ // gps时间不是最新的，补传数据
				return;
			}
			String key = CacheKeyConstants.GPS_VALID_KEY + sim;
			// 打印key
			logger.debug("GPS_VALID_KEY IS:" + key);
			redisService.saveByteToString(CacheKeyConstants.GPS_VALID_KEY, sim, locationDataByte);

			// 添加里程，油量信息
			gpsInfoCache.addGpsInfo(sim, locationData);
        } catch (Exception e) {
			logger.error("添加末次有效位置缓存异常", e);
        }
    }

    /**
     * 添加末次有效位置缓存-管道方式
     *
     * @param sim
     * @param locationDataByte
     * @param locationData
     */
    public void commitLastGpsValid(String sim, byte[] locationDataByte, LocationData locationData, List<Map> params)
    {
        try
        {
            if (locationData == null || locationData.getGpsDate() < 0)
            { // gps时间必须>0
                return;
            }
            if (locationData.getLatitude() <= 0 || locationData.getLongitude() <= 0)
            { // 不是有效位置数据
                return;
            }
            LocationData oldLocationDate = getLastGpsVlid(sim);
            if (oldLocationDate != null && oldLocationDate.getGpsDate() > locationData.getGpsDate())
            { // gps时间不是最新的，补传数据
                return;
            }
            String key = CacheKeyConstants.GPS_VALID_KEY + sim;
            // 打印key
            logger.debug("GPS_VALID_KEY IS:" + key);
            redisService.saveByteToString(CacheKeyConstants.GPS_VALID_KEY, sim, locationDataByte);

            Map param = new HashMap();
            param.put(RedisActionConstant.ACTION,RedisActionConstant.RedisActionType.HSET.getValue());
            param.put(RedisActionConstant.KEY1,CacheKeyConstants.GPS_VALID_KEY);
            param.put(RedisActionConstant.KEY2,sim);
            param.put(RedisActionConstant.VALUE,JsonUtil.toJson(Convert.bytesToHexString(locationDataByte)));
            params.add(param);

            // 添加里程，油量信息
            gpsInfoCache.commitGpsInfo(sim, locationData,params);
        } catch (Exception e) {
            logger.error("添加末次有效位置缓存异常", e);
        }
    }

}
