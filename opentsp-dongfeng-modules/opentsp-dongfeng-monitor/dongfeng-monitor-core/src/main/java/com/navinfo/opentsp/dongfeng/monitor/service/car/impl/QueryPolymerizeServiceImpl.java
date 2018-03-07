package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.common.messaging.ResultCode;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryPolymerizeCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryPolymerizePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryPolymerizeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取聚合点
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/23
 */
@Service
public class QueryPolymerizeServiceImpl extends BaseService implements IQueryPolymerizeService {
    @Autowired
    private GpsCache gpsCache;
    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Value("${polymerizePath}")
    private String polymerizePath;

    @Override
    public HttpCommandResultWithData queryPolymerize(QueryPolymerizeCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            String param = "userId="+command.getUserInfor().getUserId()+"&level="+command.getLevel()+"&leftLongitude="+command.getLeftLng()+"&leftLatitude="+command.getLeftLat()+"&rightLongitude="+command.getRightLng()+"&rightLatitude="+command.getRightLat();
            String resultPolyz = HttpUtil.get(polymerizePath,param,"");
            JSONObject rj = JSON.parseObject(resultPolyz);
            int resultCode = rj.getIntValue("code");
            if(resultCode == 200){
                JSONArray arr = new JSONArray();
                JSONObject data = rj.getJSONObject("data");
                JSONArray list = data.getJSONArray("list");
                List<String> chassisNums = new ArrayList<String>();
                Map<String,JSONObject> map = new HashMap<String,JSONObject>();
                for (Object object : list) {
                    JSONObject jb = (JSONObject) JSON.toJSON(object);
                    int count = jb.getIntValue("count");
                    if(count == 1){
                        String chassisNum = jb.getString("carNo");
                        chassisNums.add(chassisNum.trim());
                        map.put(chassisNum.trim(),jb);
                    }
                    jb.put("lat",jb.getDoubleValue("latitude"));
                    jb.put("lng",jb.getDoubleValue("longitude"));
                    jb.remove("carNo");
                    jb.remove("latitude");
                    jb.remove("longitude");
                    arr.add(jb);
                }
                if(chassisNums.size()>0){
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("chassisNums", StringUtils.join(chassisNums, ","));
                    List<QueryPolymerizePojo> pojos =  dao.sqlFind("selectCarsBychassisNums", params, QueryPolymerizePojo.class);
                    for (QueryPolymerizePojo carLocPojo:pojos) {
                        //位置数据填充对象其余值
                        LCLocationData.LocationData locationData = gpsCache.getLastGpsVlid(carLocPojo.getCommId().toString());
                        if(locationData!=null){
                            carLocPojo.setGpstime(locationData.getGpsDate());
                            carLocPojo.setDirection(locationData.getDirection());
                            carLocPojo.setLat(locationData.getLatitude()* 0.000001);
                            carLocPojo.setLng(locationData.getLongitude()* 0.000001);
                        }
                        //在线状态封装入对象
                        GpsInfoData gpsInfodata = gpsInfoCache.getGpsInfo(carLocPojo.getCommId().toString());
                        carLocPojo.setCarStauts(gpsInfodata==null||gpsInfodata.getCarStatue()==null? CloudConstants.CarState.VehicleStatusOfflineInvalid.getValue():gpsInfodata.getCarStatue());
                        JSONObject jb = map.get(carLocPojo.getChassisNum());
                        jb.put("car",carLocPojo);
                    }
                }
                result.setResultCode(ResultCode.OK.code());
                result.setData(arr);
            }else{
                result.setResultCode(resultCode);
                result.setMessage(rj.getString("msg"));
            }
        } catch (IOException e) {
            result.fillResult(ReturnCode.SERVER_ERROR);
        }
        return result;
    }
}
