package com.navinfo.opentsp.dongfeng.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalSubscribeInfoPojo;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 终端订阅缓存
 * <p>
 * 新增车辆并绑定终端时，将绑定的终端加入该缓存
 * tcp服务定时从该缓存中获取增量数据，并下发增量订阅请求到位置云
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-06-01
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class TerminalSubscribeCache {
    private static final Logger logger = LoggerFactory.getLogger(GpsInfoCache.class);

    @Resource
    private IRedisService redisService;


    /**
     * 添加终端订阅缓存
     *
     * @param communicationId 终端通讯号
     */
    public void addSubscribeTerminal(Long communicationId) {
        if (communicationId == null) {
            return;
        }
        String key = CacheKeyConstants.ADD_SUBSCRIBE_TERMINAL_PREFIX + communicationId;
        // 打印key
        logger.debug("terminal subscribe add,key: " + key);
        try {
            TerminalSubscribeInfoPojo pojo = new TerminalSubscribeInfoPojo();
            pojo.setCommunicationId(communicationId);
            pojo.setTimestamp(System.currentTimeMillis());
            redisService.saveObjectToJson(CacheKeyConstants.ADD_SUBSCRIBE_TERMINAL_PREFIX, StringUtil.valueOf(communicationId), pojo);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 获取所有待订阅的终端
     *
     * @return
     */
    public List<TerminalSubscribeInfoPojo> getAllSubscribeTerminal() {
        List<TerminalSubscribeInfoPojo> list = redisService.getAllListJson(CacheKeyConstants.ADD_SUBSCRIBE_TERMINAL_PREFIX, TerminalSubscribeInfoPojo.class);
        return list;
    }

    /**
     * 删除增量订阅终端
     *
     * @param communicationId
     */
    public void deleteSubscribeTerminal(Long communicationId) {
        redisService.del(CacheKeyConstants.ADD_SUBSCRIBE_TERMINAL_PREFIX, String.valueOf(communicationId));
    }
}
