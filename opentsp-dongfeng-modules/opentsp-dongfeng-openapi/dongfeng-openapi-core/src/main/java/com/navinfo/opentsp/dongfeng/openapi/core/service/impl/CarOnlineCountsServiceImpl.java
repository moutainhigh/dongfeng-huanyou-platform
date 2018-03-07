package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.common.messaging.ResultCode;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.openapi.commands.bigdata.QueryCarOnlineCountsCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.service.ICarOnlineCountsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/11/28
 */
@Service
public class CarOnlineCountsServiceImpl extends BaseService implements ICarOnlineCountsService {
    @Resource
    private GpsInfoCache gpsInfoCache;
    @Override
    public HttpCommandResultWithData queryCarOnlineCounts(QueryCarOnlineCountsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        // 获取所有车辆状态缓存
        Map<String, GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();
        Integer onLineCounts = 0;
        for (Map.Entry<String, GpsInfoData> entry : gpsInfoMap.entrySet()) {
            //是否在线
            if(isOnline(entry.getValue())){
                onLineCounts++;
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("onLineCounts",onLineCounts);

        result.setResultCode(ResultCode.OK.code());
        result.setData(resultMap);
        return result;
    }

    /**
     * 是否在线
     * @param gpsInfo
     * @return
     */
    private Boolean isOnline(GpsInfoData gpsInfo){
        if(gpsInfo != null && gpsInfo.getCarStatue() != null && (gpsInfo.getCarStatue() & 1) >0){
            Long gpsDate = gpsInfo.getGpsDate();
            // 最新gps时间在10分钟内
            if (gpsDate != null && gpsDate != 0 && System.currentTimeMillis() / 1000 - gpsDate < 600)
            {
                return true;
            }
        }
        return false;
    }
}
