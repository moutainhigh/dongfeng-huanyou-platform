package com.navinfo.opentsp.dongfeng.monitor.service.station.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.LocationUtil;
import com.navinfo.opentsp.dongfeng.common.util.PagingInfoUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.QueryNearbyCarsCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryNearbyCarsPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.station.QueryStationPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryNearbyCarsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务站附近车辆
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/29
 */
@Service
public class QueryNearbyCarsServiceImpl  extends BaseService implements IQueryNearbyCarsService {

    @Resource
    private GpsInfoCache gpsInfoCache;
    @Resource
    private GpsCache gpsCache;

    @Override
    public HttpCommandResultWithData queryNearbyCars(QueryNearbyCarsCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("accountId", command.getUserInfor().getUserId());
        params.put("accountType", command.getUserInfor().getType());
        params.put("stationId",command.getStationId());
        QueryStationPojo queryStationPojo = (QueryStationPojo)dao.sqlFindObject("queryStationById", params, QueryStationPojo.class);

        //如果服务站不存在
        if(queryStationPojo == null){
            result.setResultCode(507);
            result.setMessage("服务站不存在");
            return result;
        }
        params.put("chassisNum",command.getChassisNum());
        List<QueryNearbyCarsPojo> list = dao.sqlFind("queryNearbyCars", params, QueryNearbyCarsPojo.class);
        Map<String,LCLocationData.LocationData> map = gpsCache.getAllLastGpsMap();
        Map<String,GpsInfoData> gpsInfoMap = gpsInfoCache.getAllGpsInfoMap();


        ArrayList<QueryNearbyCarsPojo> resultList = new ArrayList<QueryNearbyCarsPojo>();
        for (QueryNearbyCarsPojo pojo:list) {
            LCLocationData.LocationData locationData = map.get(pojo.getCommId().longValue()+"");
            if(locationData == null){
                continue;
            }
            Double fromLat = locationData.getLatitude()* 0.000001;
            Double fromLng = locationData.getLongitude()* 0.000001;
            Double toLat = queryStationPojo.getLatitude().longValue()* 0.000001;
            Double toLng = queryStationPojo.getLongitude().longValue()* 0.000001;
            Double dis = LocationUtil.getDistance(fromLat, fromLng, toLat, toLng);
            Boolean flag = true;

            if(command.getLocType().intValue() == -1){
                if(dis > queryStationPojo.getServiceRadius()){
                    flag = false;
                }
            }else if(command.getLocType().intValue() == 1){
                if(dis > queryStationPojo.getRadius()){
                    flag = false;
                }
            }else if(command.getLocType().intValue() == 2){
                if(dis < queryStationPojo.getRadius() || dis > queryStationPojo.getServiceRadius()){
                    flag = false;
                }
            }


            GpsInfoData gpsInfo = gpsInfoMap.get(pojo.getCommId().longValue()+"");
            if(gpsInfo == null){
                flag = false;
            }else if((gpsInfo.getCarStatue().intValue()&1) == 0){
                flag = false;
            }

            if(flag){
                pojo.setDistance(dis);
                pojo.setLastGpsLat(fromLat);
                pojo.setLastGpsLng(fromLng);
                pojo.setLastGpsTime(DateUtil.timeStr(locationData.getGpsDate()));
                pojo.setTotalMilleage(gpsInfo.getTempMileage() == null? 0 : gpsInfo.getTempMileage());
                resultList.add(pojo);

            }
        }
        int pn = Integer.parseInt(command.getPage_number());
        int ps = Integer.parseInt(command.getPage_size());
        result.setData(PagingInfoUtil.getPagingInfo(resultList,pn,ps));
        return result;
    }
}
