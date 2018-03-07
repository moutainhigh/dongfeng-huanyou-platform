package com.navinfo.dongfeng.terminal.comm.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.googlecode.protobuf.format.JsonFormat;
import com.lc.core.protocol.platform.LCRPNodeData;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.model.cache.WebServiceIp;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 云平台返回RP服务信息
 *
 */
@Component
public class RPNodeDataCache
{
    
    @Resource
    private IRedisService redisService;
    
    /**
     * 添加webservice
     * 
     * @param key
     * @param listIps
     * @throws JsonProcessingException
     */
    public void addWebServiceRP(String key, List<WebServiceIp> listIps)
        throws JsonProcessingException
    {
        redisService.saveObjectToJson(key, listIps);
    }
    
    public void clear()
    {
        Set<String> nameSet = redisService.getKeys(CacheKeyConstants.WEB_SERVICE_URL_KEY + "*");
        Iterator<String> nameIt = nameSet.iterator();
        while (nameIt.hasNext())
        {
            String keyStr = nameIt.next();
            redisService.del(keyStr);
        }
    }
    
    // gps RP服务信息
    public void addGpsRP(String serverIdentifies, List<LCRPNodeData.RPNodeData> listRps)
        throws JsonProcessingException
    {
        List<String> jsonlist = new ArrayList<String>();
        for (LCRPNodeData.RPNodeData rPNodeData : listRps)
        {
            jsonlist.add(JsonFormat.printToString(rPNodeData));
        }
        redisService.saveObjectToJson(CacheKeyConstants.RP_URL_KEY + serverIdentifies, jsonlist);
    }
    
    public List<LCRPNodeData.RPNodeData> getGpsRP(String serverIdentifies)
    {
        List<LCRPNodeData.RPNodeData> result = null;
        List<String> jsonlist = redisService.getJson(CacheKeyConstants.RP_URL_KEY + serverIdentifies, List.class);
        if (jsonlist != null)
        {
            result = new ArrayList<LCRPNodeData.RPNodeData>();
            LCRPNodeData.RPNodeData.Builder dataBuilder = LCRPNodeData.RPNodeData.newBuilder();
            for (String oneJson : jsonlist)
            {
                try
                {
                    JsonFormat.merge(oneJson, dataBuilder);
                    result.add(dataBuilder.build());
                }
                catch (JsonFormat.ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public void clearGpsRP()
    {
        Set<String> nameSet = redisService.getKeys(CacheKeyConstants.RP_URL_KEY + "*");
        Iterator<String> nameIt = nameSet.iterator();
        while (nameIt.hasNext())
        {
            String keyStr = nameIt.next();
            redisService.del(keyStr);
        }
    }
    
    @PostConstruct
    public void initClear()
    {
        clear();
        clearGpsRP();
    }
}
