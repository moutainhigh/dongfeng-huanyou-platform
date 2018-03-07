package com.navinfo.dongfeng.terminal.comm.cache;

import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.model.cache.TerminalSubscribeInfo;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
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
    @Resource
    private IRedisService redisService;

    /**
     * 获取所有待订阅的终端
     *
     * @return
     */
    public List<TerminalSubscribeInfo> getAllSubscribeTerminal() {
        List<TerminalSubscribeInfo> list = redisService.getAllListJson(CacheKeyConstants.ADD_SUBSCRIBE_TERMINAL_PREFIX, TerminalSubscribeInfo.class);
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
