package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.dataaccess.terminal.LCMileageAndOilDataRes;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryTimelyMonitorCommand;
import com.navinfo.opentsp.dongfeng.monitor.dto.car.QueryTimelyMonitorDto;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCommonCarPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTimelyMonitorPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryTimelyMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryTimelyMonitorServiceImpl extends BaseService implements IQueryTimelyMonitorService {

    @Autowired
    private GpsCache gpsCache;
    @Autowired
    private GpsInfoCache gpsInfoCache;
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Override
    public HttpCommandResultWithData queryTimelyMonitor(QueryTimelyMonitorCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            //根据车辆id获取车辆信息
            Map<String, Object> parm = new HashMap<>();
            parm.put("carId", command.getCarId());
            parm.put("accountId", command.getUserInfor().getUserId());
            parm.put("accountType", command.getUserInfor().getType());
            QueryCommonCarPojo queryCommonCarPojo = (QueryCommonCarPojo) dao.sqlFindObject("queryCommonCar_extend_2", parm, QueryCommonCarPojo.class);
            long commId = 0;
            if (null != queryCommonCarPojo) {
                commId = queryCommonCarPojo.getCommId().longValue();
                //默认油量处理
                if (StringUtil.isEmpty(queryCommonCarPojo.getOilCapacity())) {
                    queryCommonCarPojo.setOilCapacity(CloudConstants.DEFAULT_OILCAPACITY);
                }
            }
            //返回结果对象
            QueryTimelyMonitorPojo timelyMonitorPojo = new QueryTimelyMonitorPojo();
            //末次位置
            LCLocationData.LocationData gpsdata = gpsCache.getLastGpsVlid(String.valueOf(commId));
            QueryTimelyMonitorDto monitorDto = new QueryTimelyMonitorDto();
            if(null!=gpsdata){
                //gps时间
                timelyMonitorPojo.setGpsdate(DateUtil.timeStr(gpsdata.getGpsDate()));
                //车辆速度
                timelyMonitorPojo.setVelocity(NumberFormatUtil.getDoubleValueData(new Double(gpsdata.getSpeed()), 1));
                //剩余油/汽量
                if(null!=queryCommonCarPojo.getFuel()&&queryCommonCarPojo.getFuel()==0){
                    timelyMonitorPojo.setOil(CloudUtil.getResGas(gpsdata));
                }else{
                    timelyMonitorPojo.setOil(NumberFormatUtil.getOilwear(CloudUtil.getResOil(gpsdata), Double.valueOf(queryCommonCarPojo.getOilCapacity())));
                }
                //转速
                List<LCVehicleStatusData.VehicleStatusData> statuslist = gpsdata.getStatusAddition().getStatusList();
                if (null != statuslist) {
                    for (LCVehicleStatusData.VehicleStatusData vehicleStatusData : statuslist) {
                        if (vehicleStatusData.getTypes() == LCStatusType.StatusType.rotation) {
                            double rev = vehicleStatusData.getStatusValue();
                            timelyMonitorPojo.setRev(rev/100);
                        }
                    }
                } else {
                    timelyMonitorPojo.setRev(0d);
                }
                //当日里程
                timelyMonitorPojo.setTodaymileage(getTodayMilleage(new BigInteger(String.valueOf(commId))));
                //ACC状态
                timelyMonitorPojo.setAccStatus(CloudUtil.getAccStatus(gpsdata.getStatus()) + CloudUtil.getGpsStatus(gpsdata.getStatus()));
                //方向
                timelyMonitorPojo.setDirection(String.valueOf(gpsdata.getDirection()));
                String status = (gpsdata.getStatus() & Math.round(Math.pow(2,13))) > 0 ? Constants.QIANMEN_OPEN: Constants.QIANMEN_CLOSE;
                //车门状态
                timelyMonitorPojo.setDoorStatus(status);
                //报警
                timelyMonitorPojo.setAlarm(CloudUtil.getAlarm(gpsdata.getAlarm()));
                //经度
                timelyMonitorPojo.setLat(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLatitude()));
                //纬度
                timelyMonitorPojo.setLng(NumberFormatUtil.getLatitudeOrLongitude(gpsdata.getLongitude()));
                //车辆在线状态
                GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(String.valueOf(commId));
                if(gpsInfo!=null){
                    timelyMonitorPojo.setCarStauts(gpsInfo.getCarStatue());
                }
                //天然气or柴油车：0表示燃气，1表示燃油
                timelyMonitorPojo.setFuel(queryCommonCarPojo.getFuel());
                CopyPropUtil.copyProp(timelyMonitorPojo, monitorDto);
            }
            result.setData(monitorDto);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 从位置云接口获取当日里程
     * @param sim
     * @return
     */
    public double getTodayMilleage(BigInteger sim){
        double mileage = 0.0;
        if(sim!=null&&sim.longValue()!=0){
            List<Long> commIds = new ArrayList<Long>();
            commIds.add(sim.longValue());
            try {
                DataAnalysisWebService dataAnalysisWebService = cloudDataRmiClientConfiguration.getDataAnalysisWebService();
                byte[] data = dataAnalysisWebService.getMileageAndOilData(commIds);
                if(data!=null){
                    LCMileageAndOilDataRes.MileageAndOilDataRes builder = LCMileageAndOilDataRes.MileageAndOilDataRes
                            .parseFrom(data);
                    List<LCMileageAndOilDataRes.MileageAndOilData> list = builder.getRecordsList();
                    if (list != null && list.size() > 0) {
                        //标准里程
                        mileage = NumberFormatUtil.getDoubleValueData((double) list.get(0).getMileage() / 1000, 2);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        return mileage;
    }
}