package com.navinfo.opentsp.dongfeng.rpws.cache;

import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.rpws.common.utils.douglas.WebServiceIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 云平台返回RP服务信息
 *
 */
@Component
public class RPNodeDataCache
{
    // @Autowired
    // private StringRedisTemplate commonRedisTemplate;
    
    // public final static String WEB_SERVICE_URL_ = "WEB_SERVICE_URL_";
    //
    // public final static String RP_URL_ = "RP_URL_";
    
    @Autowired
    public IRedisService redisService;
    
    public List<WebServiceIp> getWebServiceCache(String key)
    {
        List<LinkedHashMap> list = redisService.getJson(key, List.class);
        // List<WebServiceIp>集合
        List<WebServiceIp> listWip = new ArrayList<WebServiceIp>();
        // 循环组装
        if (!StringUtil.isEmpty(list))
        {
            for (LinkedHashMap map : list)
            {
                WebServiceIp wsIp = new WebServiceIp();
                wsIp.setIdentifies((String)map.get("identifies"));
                wsIp.setServiceIp((String)map.get("serviceIp"));
                listWip.add(wsIp);
            }
        }
        return listWip;
    }
}
