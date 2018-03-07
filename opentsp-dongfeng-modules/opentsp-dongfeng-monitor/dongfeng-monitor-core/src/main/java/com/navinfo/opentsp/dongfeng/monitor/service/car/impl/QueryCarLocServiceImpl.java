package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRedisService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarLocCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryCarLocConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarLocPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarLocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 地图打点实现类
 * @wenya
 * @create 2017-03-11 9:56
 **/
@Service
public class QueryCarLocServiceImpl extends BaseService implements IQueryCarLocService {
    protected static final Logger logger = LoggerFactory.getLogger(QueryCarLocServiceImpl.class);
    @Resource
    private IRedisService redisService;
    @Autowired
    private GpsCache gpsCache;
    @Autowired
    private GpsInfoCache gpsInfoCache;
    /**
     * 地图打点
     * @param command
     * @return
     */
    public HttpCommandResultWithData queryCarLoc(QueryCarLocCommand command) {
        logger.info("=====  queryCarLoc start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> parm = new HashMap<String,Object>();
        parm.put("id", command.getId());
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType", command.getUserInfor().getType());
        //sql定义在car-QueryCarTip-dynamic.xml中
        QueryCarLocPojo carLocPojo = (QueryCarLocPojo)dao.sqlFindObject("queryCarLoc", parm, QueryCarLocPojo.class);
        //位置数据填充对象其余值
        LCLocationData.LocationData locationData = gpsCache.getLastGpsVlid(carLocPojo.getCommId().toString());
        if(locationData!=null){
            carLocPojo.setGpstime(locationData.getGpsDate());
            carLocPojo.setDirection(locationData.getDirection());
            carLocPojo.setLat(locationData.getLatitude());
            carLocPojo.setLng(locationData.getLongitude());
        }
        //在线状态封装入对象
        GpsInfoData data = gpsInfoCache.getGpsInfo(carLocPojo.getCommId().toString());
        carLocPojo.setCarStauts(data==null||data.getCarStatue()==null? CloudConstants.CarState.VehicleStatusOfflineInvalid.getValue():data.getCarStatue().intValue());
        //  end
        result.setData(QueryCarLocConverter.CarLocConverter(carLocPojo));
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryCarLoc end  =====");
        return result;
    }
}
