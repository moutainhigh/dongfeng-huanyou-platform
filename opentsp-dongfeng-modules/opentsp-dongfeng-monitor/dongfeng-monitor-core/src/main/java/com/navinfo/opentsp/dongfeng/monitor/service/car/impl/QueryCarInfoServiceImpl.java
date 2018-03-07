package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarInfoCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryCarInfoConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarInfoPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 车辆详情
 *
 * @wenya
 * @create 2017-03-13 17:54
 **/
@Service
public class QueryCarInfoServiceImpl extends BaseService implements IQueryCarInfoService{
    @Autowired
    private GpsCache gpsCache;
    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Override
    public HttpCommandResultWithData queryCarInfo(QueryCarInfoCommand command) {
        logger.info("=====  queryCarInfo start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> parm = new HashMap<String,Object>();
        parm.put("id", command.getCarId());
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType", command.getUserInfor().getType());
        //sql定义在car-QueryCarTip-dynamic.xml中
        QueryCarInfoPojo carInfoPojo = (QueryCarInfoPojo)dao.sqlFindObject("queryCarInfo", parm, QueryCarInfoPojo.class);
        //获取保养时间，保养地点
        carInfoPojo = queryMaintainInfo(parm,carInfoPojo);
        //位置数据填充对象其余值
        LCLocationData.LocationData locationData = gpsCache.getLastGpsVlid(carInfoPojo.getFkCommNum().toString());
        if(locationData!=null){
            carInfoPojo.setLngVaildI(locationData.getLongitude());
            carInfoPojo.setLatValidI(locationData.getLatitude());
            carInfoPojo.setGpstimeVaildL(locationData.getGpsDate());
        }
        //在线状态封装
        GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(carInfoPojo.getFkCommNum().toString());
        if(gpsInfo!=null){
            carInfoPojo.setCarStauts(gpsInfo.getCarStatue()==null? CloudConstants.CarState.VehicleStatusOfflineInvalid.getValue():gpsInfo.getCarStatue());
        }
        result.setData(QueryCarInfoConverter.queryCarInfoConverter(carInfoPojo));
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryCarInfo end  =====");
        return result;
    }
    //获取保养地点、时间
    private QueryCarInfoPojo queryMaintainInfo(Map<String, Object> parm, QueryCarInfoPojo carInfoPojo) {
        //sql定义在car-QueryCarTip-dynamic.xml中
        parm.put("chassisNum", carInfoPojo.getChassisNum());
        QueryCarInfoPojo carInfoRepairPojo = (QueryCarInfoPojo)dao.sqlFindObject("queryMaintainInfo", parm, QueryCarInfoPojo.class);
        if(carInfoRepairPojo!=null){
            if(carInfoRepairPojo.getMaintainTimeL()!=null&&carInfoRepairPojo.getMaintainTimeL().longValue()!=0) {
                carInfoPojo.setMaintainTimeL(carInfoRepairPojo.getMaintainTimeL());
                carInfoPojo.setMaintainPlace(carInfoRepairPojo.getMaintainPlace());
            }
        }else{
            if(carInfoPojo.getMaintainTimeL()!=null&&carInfoPojo.getMaintainTimeL().longValue()!=0){
                parm.put("repairDate", carInfoPojo.getMaintainTimeL());
                //sql定义在car-QueryCarTip-dynamic.xml中
                QueryCarInfoPojo carInfoRepair2Pojo = (QueryCarInfoPojo)dao.sqlFindObject("queryMaintainInfo2", parm, QueryCarInfoPojo.class);
                carInfoPojo.setMaintainPlace(carInfoRepair2Pojo.getMaintainPlace());
            }
        }
       return carInfoPojo;
    }


}
