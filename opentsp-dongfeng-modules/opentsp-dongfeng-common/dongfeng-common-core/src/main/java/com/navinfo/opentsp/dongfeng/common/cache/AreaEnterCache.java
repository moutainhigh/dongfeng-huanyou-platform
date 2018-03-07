package com.navinfo.opentsp.dongfeng.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.dto.AreaEnterData;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 进出区域数据缓存
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-19
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class AreaEnterCache {
    private static final Logger logger = LoggerFactory.getLogger(AreaEnterCache.class);

    @Resource
    private IRedisService redisService;

    /**
     * 添加车辆进出区域缓存数据
     *
     * @param areaEnterData
     */
    public void saveAreaEnterData(AreaEnterData areaEnterData) {
        if (StringUtil.isEmpty(areaEnterData) || StringUtil.isEmpty(areaEnterData.getSimNo())) {
            return;
        }
        String key1 = CacheKeyConstants.AREA_ENTER_KEY;
        String key2 = areaEnterData.getSimNo() + "_" + areaEnterData.getAreaId() + "_" + areaEnterData.getAreaType();
        // 打印key
        logger.debug("area enter data add,key1:{},key2:{} ", key1, key2);
        try {
            redisService.saveObjectToJson(key1, key2, areaEnterData);
        } catch (JsonProcessingException e) {
            logger.error("redis存入车辆进出区域数据异常",e);
        }
    }

    /**
     * 获取车辆的进出区域记录
     *
     * @param simNo    终端通讯号
     * @param areaId   区域ID
     * @param areaType 区域类型
     * @return
     */
    public AreaEnterData getAreaEnterData(String simNo, Long areaId, Integer areaType) {
        String key1 = CacheKeyConstants.AREA_ENTER_KEY;
        String key2 = simNo + "_" + areaId + "_" + areaType;
        return redisService.getJson(key1, key2, AreaEnterData.class);
    }
}
