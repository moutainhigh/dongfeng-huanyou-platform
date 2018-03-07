package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.google.protobuf.ByteString;
import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.dataaccess.terminal.LCLastestLocationDataRes;
import com.lc.core.protocol.dataaccess.terminal.LCMileageAndOilDataRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.CloudUtil;
import com.navinfo.opentsp.dongfeng.common.util.NumberFormatUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarTipCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryCarTipConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCarTipPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆tip详情实现类
 *
 * @wenya
 * @create 2017-03-11 10:02
 **/
@Service
public class QueryCarTipServiceImpl extends BaseService implements IQueryCarTipService {
    @Resource
    private IBaseDataService baseDataService;
    @Autowired
    private GpsCache gpsCache;
    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Value("${tip.cloud.location.enable}")
    private Boolean tipCloudLocationEnable;

    /**
     * 点击车辆树上节点，显示车辆tip详细信息
     * @param command
     * @return
     */

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    public HttpCommandResultWithData queryCarTip(QueryCarTipCommand command) {
        logger.info("=====  queryCarTip start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> parm = new HashMap<String,Object>();
        parm.put("id", command.getId());
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType", command.getUserInfor().getType());
        //sql定义在car-QueryCarTip-dynamic.xml中
        QueryCarTipPojo carTipPojo = (QueryCarTipPojo)dao.sqlFindObject("queryCarTip", parm, QueryCarTipPojo.class);
        if (null != carTipPojo) {
            //默认油量处理
            if (StringUtil.isEmpty(carTipPojo.getOilCapacity())) {
                carTipPojo.setOilCapacity(CloudConstants.DEFAULT_OILCAPACITY);
            }
        }else{
            result.fillResult(ReturnCode.DATA_NO_EXIST);
            result.setMessage("车辆不存在或车辆未绑定终端");
            return result;
        }
        //位置云接口填充当日里程
        double mileage = getTodayMilleage(carTipPojo.getCommId());
        carTipPojo.setTodayMilleage(mileage);
        //位置数据填充其余值
        //末次位置
        LCLocationData.LocationData locationData = gpsCache.getLastGps(carTipPojo.getCommId().toString());
        if(locationData!=null){
            carTipPojo.setGpstime(locationData.getGpsDate());
            carTipPojo.setLat(locationData.getLatitude());
            carTipPojo.setLng(locationData.getLongitude());
        }

        LCLocationData.LocationData location = null;
        if(tipCloudLocationEnable){
            ArrayList<Long> commIds = new ArrayList<Long>();
            commIds.add(carTipPojo.getCommId().longValue());
            Map<Long, LCLocationData.LocationData> map = getTerminalLocationData(commIds);
            location = map.get(carTipPojo.getCommId().longValue());
        }else{
            location = gpsCache.getLastGpsVlid(carTipPojo.getCommId().toString());
        }

        if(location!=null){
            carTipPojo.setGpstimeVaild(location.getGpsDate());
            carTipPojo.setLatValid(location.getLatitude());
            carTipPojo.setLngVaild(location.getLongitude());
            carTipPojo.setSpeed(location.getSpeed());
            carTipPojo.setAccStauts(getStatue(location));
            carTipPojo.setFault(getFault(location, carTipPojo.getEngineType()));
            carTipPojo.setDirection(location.getDirection());

            LCLocationData.VehicleStatusAddition statusAddition = location.getStatusAddition();
            if(statusAddition != null) {
                List<LCVehicleStatusData.VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
                if (VehicleStatusDataList != null) {
                    for (LCVehicleStatusData.VehicleStatusData data : VehicleStatusDataList) {
                        switch (data.getTypes().getNumber()) {
                            case LCStatusType.StatusType.dynamicLoad_VALUE :    //整车载重
                                long dynamicLoad_VALUE = data.getStatusValue();
                                carTipPojo.setCarLoad(NumberFormatUtil.getDoubleValueData((double) dynamicLoad_VALUE / 100, 2)
                                        + "");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            double tempOil = 0d;
            if(null!=carTipPojo.getFuel()&&carTipPojo.getFuel()==0){
                tempOil = CloudUtil.getResGas(location);
            }else{
                tempOil = NumberFormatUtil.formatNumber(CloudUtil.getResOil(location)* 0.01 * 0.01 * Double.valueOf(carTipPojo.getOilCapacity()).doubleValue(), 2);
            }
            carTipPojo.setResOil(tempOil);
            carTipPojo.setTotalMilleage(NumberFormatUtil.getDoubleValueData((double)location.getStandardMileage(), 2));
        }




/*        //末次有效位置
        LCLocationData.LocationData locationDataValid = gpsCache.getLastGpsVlid(carTipPojo.getCommId().toString());
        if(locationDataValid!=null){
            carTipPojo.setGpstimeVaild(locationDataValid.getGpsDate());
            carTipPojo.setLatValid(locationDataValid.getLatitude());
            carTipPojo.setLngVaild(locationDataValid.getLongitude());
            carTipPojo.setSpeed(locationDataValid.getSpeed());
            carTipPojo.setAccStauts(getStatue(locationDataValid));
            carTipPojo.setFault(getFault(locationDataValid, carTipPojo.getEngineType()));
            carTipPojo.setDirection(locationDataValid.getDirection());
        }*/
        //tip中剩余油量和总里程
        GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(carTipPojo.getCommId().toString());
        if(gpsInfo!=null){
/*            double tempOil = 0d;
            if(null!=carTipPojo.getFuel()&&carTipPojo.getFuel()==0){
                tempOil = gpsInfo.getResGas();
            }else{
                if(gpsInfo.getTempOil() != null){
                    tempOil =  NumberFormatUtil.formatNumber(gpsInfo.getTempOil() * Double.valueOf(carTipPojo.getOilCapacity()).doubleValue(), 2);
                }
            }
            carTipPojo.setResOil(tempOil);
            if (!StringUtil.isEmpty(gpsInfo.getTempMileage())) {
                carTipPojo.setTotalMilleage(NumberFormatUtil.getDoubleValueData(gpsInfo.getTempMileage().doubleValue(), 2));
            }*/
            carTipPojo.setCarStauts(gpsInfo.getCarStatue());
        }
        result.setData(QueryCarTipConverter.CarTipConverter(carTipPojo));
        result.fillResult(ReturnCode.OK);
        logger.info("===== queryCarTip end  =====");
        return result;
    }
    //从位置云接口获取当日里程
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
                        mileage = getTTMile(list.get(0));
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        return mileage;
    }

    //获取车辆状态（车门+acc+报警+定位）
    public String getStatue(LCLocationData.LocationData locationData){
        String alarmaddition = "";
        if(locationData.getAdditionAlarm().size()>7){
            ByteString additionalarm = locationData.getAdditionAlarm();
            alarmaddition = CloudUtil.getAdditinAlarm(additionalarm.byteAt(5),additionalarm.byteAt(6),additionalarm.byteAt(7));
        }
        return CloudUtil.getDoorStatus(locationData.getStatus())+CloudUtil.getAccStatus(locationData.getStatus())
                +alarmaddition +CloudUtil.getGpsStatus(locationData.getStatus());

    }

    //获取故障码
    public String getFault(LCLocationData.LocationData locationData,String engineType){
        if(engineType==null||engineType.equals("")){
            return "";
        }else{
            //基础数据表中故障码list
            List<BaseData> faulist = baseDataService.queryList(Integer.parseInt(engineType));
            String str = CloudUtil.getFault(locationData.getBreakdownAddition().getBreakdownList(), faulist, engineType);
            return str;
        }
    }
    /*
    * 当日里程
     */
    public double getTTMile(LCMileageAndOilDataRes.MileageAndOilData mc){
        //实际返回里程
        double lastMile = 0;
        //ECU里程
        long ecumile = mc.getMileage();
        if(ecumile!=0l){
            lastMile = NumberFormatUtil.getDoubleValueData((double)ecumile/1000, 2);
            return lastMile;
        }
        return lastMile;
    }
    /**
     * 获取位置云末次有效位置数据
     *
     * @param commIds
     * @return
     */
    public Map<Long, LCLocationData.LocationData> getTerminalLocationData(List<Long> commIds) {
        Map<Long, LCLocationData.LocationData> map = new HashMap<Long, LCLocationData.LocationData>();
        try {
            BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration.getBasicDataQueryWebService();

            CommonParameterSerializer commonParameter = new CommonParameterSerializer();
            commonParameter.setMultipage(false);
            byte[] data = basicDataQueryWebService.getLastestLocationData(commIds, "", 3, commonParameter);
            if (data != null) {
                LCLastestLocationDataRes.LastestLocationDataRes builder = LCLastestLocationDataRes.LastestLocationDataRes
                        .parseFrom(data);
                List<LCTerminalLocationData.TerminalLocationData> list = builder.getDatasList();
                for (LCTerminalLocationData.TerminalLocationData tld : list) {
                    map.put(tld.getTerminalId(), tld.getLocationData());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }
}
