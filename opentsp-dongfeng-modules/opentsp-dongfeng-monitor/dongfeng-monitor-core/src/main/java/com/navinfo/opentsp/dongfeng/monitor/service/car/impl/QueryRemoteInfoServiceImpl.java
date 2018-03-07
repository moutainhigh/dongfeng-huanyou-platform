package com.navinfo.opentsp.dongfeng.monitor.service.car.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCStatusType.StatusType;
import com.lc.core.protocol.common.LCVehicleBreakdown;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.webservice.newstatisticsdata.LCGetTerminalLocationDataRes;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryRemoteInfoCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryRemoteInfoConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryRemoteInfoPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryRemoteInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程诊断
 *
 * @wenya
 * @create 2017-03-13 12:18
 **/
@Service
public class QueryRemoteInfoServiceImpl extends BaseService implements IQueryRemoteInfoService {
    /**
     * 远程诊断
     * @param command
     * @return
     */
    @Resource
    private IBaseDataService baseDataService;
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    public HttpCommandResultWithData queryRemoteInfo(QueryRemoteInfoCommand command) {
        logger.info("=====  QueryRemoteInfo start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Long> parm = new HashMap<String,Long>();
        parm.put("carId", Long.parseLong(command.getCarId()));
        parm.put("accountId", command.getUserInfor().getUserId());
        parm.put("accountType", Long.valueOf(command.getUserInfor().getType()));
        //车辆id转换成通信号
        QueryRemoteInfoPojo queryRemoteInfoPojo = (QueryRemoteInfoPojo)dao.sqlFindObject("queryCommonCar_extend_2", parm,QueryRemoteInfoPojo.class);
        //时间参数转换
        long endTime = 0l;
        if(command.getDateTime()==null||"".equals(command.getDateTime())){
            //获取当天的起始时间和结束时间
            endTime = DateUtil.getDayTime(DateUtil.formatDate(new Date()))[1];
        }else{
            //获取时间参数所在天的起始时间和结束时间
            endTime = DateUtil.getDayTime(command.getDateTime())[1];
        }
        //位置数据填充对象其余值
        queryRemoteInfoPojo = getTerminalLocationData(queryRemoteInfoPojo,endTime,Integer.parseInt(command.getIndex()));
        result.setData(QueryRemoteInfoConverter.queryRemoteInfoConverter(queryRemoteInfoPojo));
        result.fillResult(ReturnCode.OK);
        logger.info("===== QueryRemoteInfo end  =====");
        return result;
    }

    public QueryRemoteInfoPojo getTerminalLocationData(QueryRemoteInfoPojo queryRemoteInfoPojo,
                                                       Long endTime, int index) {
        try {
            if (queryRemoteInfoPojo.getCommId() != null && queryRemoteInfoPojo.getCarId() !=null) {
                byte[] data = cloudDataRmiClientConfiguration.getBasicDataQueryWebService().getTerminalLocationData(
                        queryRemoteInfoPojo.getCommId().longValue(),endTime,index);
                if (data != null) {
                    LCGetTerminalLocationDataRes.GetTerminalLocationDataRes builder = LCGetTerminalLocationDataRes.GetTerminalLocationDataRes
                            .parseFrom(data);
                    int current = builder.getCurrent();
                    // 获取位置数据
                    LCLocationData.LocationData locationData = builder.getLocationData();
                    //仪表盘
                    signalStatusPasrse(locationData,queryRemoteInfoPojo);
                    //车辆状态附加信息
                    getVehicleStatusAddition(locationData,queryRemoteInfoPojo);
                    getcarMessageData(locationData,queryRemoteInfoPojo);
                    getBreakdown(locationData,queryRemoteInfoPojo);
                    queryRemoteInfoPojo.setIndex(current);//当上一条是 index-1,下一条时index+1
                    queryRemoteInfoPojo.setGpstime(DateUtil.timeStr(locationData.getGpsDate()));
                    if(index==-1){
                        queryRemoteInfoPojo.setTotal(current);
                    }

                }
            }
        } catch (Exception e) {
            logger.error("调用远程诊断接口出现异常：" , e);
        }
        return queryRemoteInfoPojo;
    }
    //仪表盘数据解析
    private QueryRemoteInfoPojo signalStatusPasrse(LCLocationData.LocationData locationData, QueryRemoteInfoPojo queryRemoteInfoPojo) {
        try {
            queryRemoteInfoPojo.setSpeed(locationData.getSpeed()+"");
            int signalStatus = locationData.getSignalStatus();
            if((signalStatus & 1 ) == 1) {//nearLight	0x00000001	近光灯信号（0位）
                queryRemoteInfoPojo.setNearLight("1");
            }
            if((signalStatus >> 1 & 1) == 1) {//farLight	0x00000002	远光灯信号（1位）
                queryRemoteInfoPojo.setFarLight("1");
            }
            if((signalStatus >> 6 & 1) == 1) {//fogLight	0x00000040	雾灯信号（6位）
                queryRemoteInfoPojo.setFogLight("1");
            }
            if((signalStatus >> 9 & 1) == 1) {//airConditioning	0x00000200	空调状态（9位）
                queryRemoteInfoPojo.setAirConditioning("1");
            }
            if((signalStatus >> 10 & 1) == 1) {//neutral	0x00000400	空挡信号（10位）
                queryRemoteInfoPojo.setNeutral("1");
            }
            if((signalStatus >> 3 & 1) == 1) {//leftTurningLight	0x00000008	左转向灯信号（3位）
                queryRemoteInfoPojo.setLeftTurningLight("1");
            }
            if((signalStatus >> 2 & 1) == 1) {//rightTurningLight	0x00000004	右转向灯信号（2位）
                queryRemoteInfoPojo.setRightTurningLight("1");
            }
            if((signalStatus >> 11 & 1) == 1) {//retarder	0x00000800	缓速器工作（11位）
                queryRemoteInfoPojo.setRetarder("1");
            }
            if((signalStatus >> 12 & 1) == 1) {//absWorking	0x00001000	ABS工作（12位）
                queryRemoteInfoPojo.setAbsWorking("1");
            }
            if((signalStatus >> 13 & 1) == 1) {//heaterWorking	0x00002000	加热器工作（13位）
                queryRemoteInfoPojo.setHeaterWorking("1");
            }
            if((signalStatus >> 14 & 1) == 1) {//clutchStatus	0x00004000	离合器状态（14位）
                queryRemoteInfoPojo.setClutchStatus("1");
            }
            if((signalStatus >> 4 & 1) == 1) { //制动信号braking
                queryRemoteInfoPojo.setBraking("1");
            }
            if((signalStatus >> 5 & 1) == 1) {//倒档信号 reverse
                queryRemoteInfoPojo.setReverse("1");
            }
            if((signalStatus >> 7 & 1)==1){ //示廓灯
                queryRemoteInfoPojo.setMarkerLight("1");
            }
            if((signalStatus >> 8 & 1)==1){//喇叭信号
                queryRemoteInfoPojo.setSpeaker("1");
            }
        }catch(Exception e) {
            logger.error("",e);
        }
        return queryRemoteInfoPojo;
    }
    //车辆状态附加信息additionAlarm 1
    public QueryRemoteInfoPojo getVehicleStatusAddition(LCLocationData.LocationData locationData, QueryRemoteInfoPojo queryRemoteInfoPojo) {
        try {
            //update fwm 2017.08.03 gps里程赋值为标准里程|总油耗赋值为标准油耗
            queryRemoteInfoPojo.setGpsMileage(locationData.getStandardMileage() + "");
            queryRemoteInfoPojo.setTotalOil(locationData.getStandardFuelCon() +"");
            //update fwm 2017.08.03 gps里程赋值为标准里程|总油耗赋值为标准油耗
            LCLocationData.VehicleStatusAddition statusAddition = locationData.getStatusAddition();
            if(statusAddition != null) {
                List<LCVehicleStatusData.VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
                if(VehicleStatusDataList != null) {
                    for(LCVehicleStatusData.VehicleStatusData data : VehicleStatusDataList) {
                        switch(data.getTypes().getNumber()) {
                            case LCStatusType.StatusType.TripDistanceDD_VALUE://仪表的小计里程--新增内部协议（TripDistanceDD）
                                double tripDistanceDD_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setTripDistanceDD(NumberFormatUtil.getDoubleValueData((double)(tripDistanceDD_VALUE/100), 2)+"");
                                break;
                            case LCStatusType.StatusType.mileageDD_VALUE://仪表总里程--新增内部协议（mileageDD）
                                double mileageDD_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setMileageDD(NumberFormatUtil.getDoubleValueData((double)(mileageDD_VALUE/100), 2)+"");
                                break;
                            case LCStatusType.StatusType.atmosphericTem_VALUE ://大气温度
                                //这里应该 除100保留2为有效数字
                                long atmosphericTem_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setAtmosphericTem(NumberFormatUtil.getDoubleValueData((double)(atmosphericTem_VALUE/100), 2)+"");
                                break;
                            case LCStatusType.StatusType.atmosphericPressure_VALUE://大气压力
                                int atmosphericPressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setAtmosphericPressure(NumberFormatUtil.getDoubleValueData((double)atmosphericPressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.dpfPressure_VALUE: //颗粒捕集器进气压力
                                int dpfPressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setDpfPressure(NumberFormatUtil.getDoubleValueData((double)dpfPressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.relativePressure_VALUE://相对增压压力
                                int relativePressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setRelativePressure(NumberFormatUtil.getDoubleValueData((double)relativePressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.absolutePressure_VALUE://绝对增压压力
                                int absolutePressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setAbsolutePressure(NumberFormatUtil.getDoubleValueData((double)absolutePressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.engineTem_VALUE://
                                int engineTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setEngineTem(NumberFormatUtil.getDoubleValueData((double)engineTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.coolantLevel_VALUE://冷却液液位（百分比）
                                int coolantLevel_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setCoolantLevel(NumberFormatUtil.getDoubleValueData((double)coolantLevel_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.pavementTem_VALUE ://pavementTem;//路面温度50C
                                int pavementTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setPavementTem(NumberFormatUtil.getDoubleValueData((double)pavementTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.exhaustTem_VALUE ://exhaustTem;//排气温度50C
                                int exhaustTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setExhaustTem(NumberFormatUtil.getDoubleValueData((double)exhaustTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.fuelTem_VALUE: //fuelTem;  //燃油温度50C
                                int fuelTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setFuelTem(NumberFormatUtil.getDoubleValueData((double)fuelTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.fuelPressure_VALUE ://fuelPressure;//燃油压力1Kpa
                                int fuelPressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setFuelPressure(NumberFormatUtil.getDoubleValueData((double)fuelPressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.oilTem_VALUE ://oilTem;//机油温度40C
                                int oilTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setOilTem(NumberFormatUtil.getDoubleValueData((double)oilTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.oilLevel_VALUE:   //机油液位
                                int oilLevel_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setOilLevel(NumberFormatUtil.getDoubleValueData((double)oilLevel_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.oilPressure_VALUE ://oilPressure;//机油压力1Kpa
                                int oilPressure_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setOilPressure(NumberFormatUtil.getDoubleValueData((double)oilPressure_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.ureaTankTem_VALUE ://ureaTankTem 尿素箱温度30C
                                int ureaTankTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setUreaTankTem(NumberFormatUtil.getDoubleValueData((double)ureaTankTem_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.ureaConcentration_VALUE ://尿素浓度
                                final int value = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setUreaConcentration(NumberFormatUtil.getDoubleValueData((double)value/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.ureaTankLiquidLevel_VALUE : // ureaTankLiquidLevel 尿素箱液位34%
                                int ureaTankLiquidLevel_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setUreaTankLiquidLevel(NumberFormatUtil.getDoubleValueData((double)ureaTankLiquidLevel_VALUE/100, 2)+"");
                                break;
                            case LCStatusType.StatusType.lampStatus_VALUE : //冷启动灯（1表示正常，0表示异常）
                                if(data.getStatusValue() == 0l) {
                                    queryRemoteInfoPojo.setLampStatus("1");
                                }
                                break;
                            case LCStatusType.StatusType.waterInOilStatus_VALUE ://油中有水指示（1表示正常，0表示异常）
                                if(data.getStatusValue() == 0l) {
                                    queryRemoteInfoPojo.setWaterInOilStatus("1");
                                }
                                break;
                            case StatusType.oilValue_VALUE ://车辆当前油量（百分比）||汽量
                                if(null!=queryRemoteInfoPojo.getFuel()&&queryRemoteInfoPojo.getFuel()==0){
                                    queryRemoteInfoPojo.setOilValue(String.valueOf(CloudUtil.getResGas(locationData)));
                                }else{
                                    double oilPercent = NumberFormatUtil.getDoubleValueData((double)new Long(data.getStatusValue()).intValue()/100l, 2);
                                    if(queryRemoteInfoPojo.getOilCapacity() != null) {
                                        try {
                                            int oilCapacityIntValue = Integer.parseInt(queryRemoteInfoPojo.getOilCapacity());
                                            queryRemoteInfoPojo.setOilValue(NumberFormatUtil.getDoubleValueData((double)oilPercent*oilCapacityIntValue/100,2)+"");//真实油量值
                                        }catch(Exception e) {
                                            logger.error("",e);
                                        }
                                    }else {
                                        queryRemoteInfoPojo.setOilValue(NumberFormatUtil.getDoubleValueData((double)oilPercent*200/100,2)+"");//真实油量值
                                    }
                                }
                                break;
                            case StatusType.rotation_VALUE ://车辆当前转速
                                int rotation_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setRotation(NumberFormatUtil.getDoubleValueData((double)rotation_VALUE/100, 2)+"");
                                break;
                            case StatusType.coolingWaterTem_VALUE ://冷却液液位（百分比）,改为改为 coolingWaterTem 0x14	发动机冷却水温度
                                int coolingWaterTem_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setCoolingWaterTem(NumberFormatUtil.getDoubleValueData((double)coolingWaterTem_VALUE/100, 2)+"");
                                break;
                            case StatusType.mileage_VALUE://整车里程
                                int mileage_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setMileage(NumberFormatUtil.getDoubleValueData((double)mileage_VALUE/100, 2)+"");//里程
                                break;
                            case StatusType.fuelConsumptionRate_VALUE ://发动机燃油消耗率（毫升/小时）
                                int fuelConsumptionRate_VALUE = new Long(data.getStatusValue()).intValue();
                                double df = fuelConsumptionRate_VALUE*0.00001;//保留两位小数-20160727：chenfangbo
                                queryRemoteInfoPojo.setFuelConsumptionRate(NumberFormatUtil.getDoubleValueData((double)df, 2)+"");
                                break;
                            case StatusType.averageFuelConsumption_VALUE ://平均燃油消耗率（千米/升）
                                int averageFuelConsumption_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setAverageFuelConsumption(NumberFormatUtil.getDoubleValueData((double)averageFuelConsumption_VALUE/100, 2)+"");
                                break;
                            case StatusType.cumulativeRunningTime_VALUE ://发动机累计运行时间（秒）
                                Long cumulativeRunningTime_VALUE = new Long(data.getStatusValue());
                                Double time = cumulativeRunningTime_VALUE * 0.01;
                                String timeStr = DateUtil.convert(time.longValue());
                                queryRemoteInfoPojo.setCumulativeRunningTimeStr(timeStr);
                                break;
                            case StatusType.cumulativeTurningNumber_VALUE ://发动机累计转数（单位:1000rpm）
                                long cumulativeTurningNumber_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setCumulativeTurningNumber(NumberFormatUtil.formatNumber(cumulativeTurningNumber_VALUE/100, 2)+"");
                                break;
                            case StatusType.dayFuelConsumption_VALUE : //小计里程总燃油量
                                long dayFuelConsumption_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setDayFuelConsumption(NumberFormatUtil.formatNumber(dayFuelConsumption_VALUE/100, 2)+"");
                                break;
                            case StatusType.lng1Gross_VALUE :
                                queryRemoteInfoPojo.setLynCylinder1TotalCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng1Surplus_VALUE :
                                queryRemoteInfoPojo.setLynCylinder1ResidualCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng2Gross_VALUE :
                                queryRemoteInfoPojo.setLynCylinder2TotalCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng2Surplus_VALUE :
                                queryRemoteInfoPojo.setLynCylinder2ResidualCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng3Gross_VALUE :
                                queryRemoteInfoPojo.setLynCylinder3TotalCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng3Surplus_VALUE :
                                queryRemoteInfoPojo.setLynCylinder3ResidualCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng4Gross_VALUE :
                                queryRemoteInfoPojo.setLynCylinder4TotalCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            case StatusType.lng4Surplus_VALUE :
                                queryRemoteInfoPojo.setLynCylinder4ResidualCapacity(NumberFormatUtil.formatNumber(data.getStatusValue()/100, 2)+"");
                                break;
                            /**
                             *  新增需求 新添加字段
                             */
                            case StatusType.truboOilTem_VALUE: // 增压器机油温度(℃)
                                long truboOilTem_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setTruboOilTem(NumberFormatUtil.getDoubleValueData((double)truboOilTem_VALUE / 100, 2)+"");
                                break;
                            case StatusType.batteryPot_VALUE:// Battery
                                // Potential（SPN
                                // 158）
                                long batteryPot_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setBatteryPot(NumberFormatUtil.getDoubleValueData((double)batteryPot_VALUE / 100, 2) + "");
                                break;
                            case StatusType.engineProtection_VALUE:// Engine
                                // protection
                                // system has
                                int engineProtection_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                if(engineProtection_VALUE==0){
                                    queryRemoteInfoPojo.setEngineProtection("shutdown not active");
                                }else{
                                    queryRemoteInfoPojo.setEngineProtection("shutdown active");
                                }
                                break;
                            case StatusType.EstimaParLoss_VALUE: // Estimated Engine
                                // Parasitic
                                // Losses
                                long EstimaParLoss_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setEstimaParLoss(NumberFormatUtil.getDoubleValueData((double)EstimaParLoss_VALUE / 100, 2) + "");
                                break;
                            case StatusType.exhGasMassflow_VALUE: // Exhaust gas
                                // mass flow
                                long exhGasMassflow_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setExhGasMassflow(NumberFormatUtil.getDoubleValueData((double)exhGasMassflow_VALUE / 100, 2) + "");
                                break;
                            case StatusType.af1Intake_VALUE: // Aftertreatment 1
                                // Intake Dew Point
                                int af1Intake_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setAf1Intake(getAfIntake(af1Intake_VALUE));
                                break;
                            case StatusType.af1Exhaust_VALUE: // Aftertreatment 1
                                // Exhaust Dew
                                // Point:内容同上
                                int af1Exhaust_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setAf1Exhaust(getAfIntake(af1Exhaust_VALUE));

                                break;
                            case StatusType.af2Intake_VALUE: // Aftertreatment 2
                                // Intake Dew
                                // Point:内容同上
                                int af2Intake_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setAf2Intake(getAfIntake(af2Intake_VALUE));
                                break;
                            case StatusType.af2Exhaust_VALUE: // Aftertreatment 2
                                // Exhaust Dew
                                // Point:内容同上
                                int af2Exhaust_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setAf2Exhaust(getAfIntake(af2Exhaust_VALUE));
                                break;
                            case StatusType.cruPauseSwitch_VALUE: // Cruise control
                                // Pause Switch
                                long cruPauseSwitch_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setCruPauseSwitch(NumberFormatUtil.getDoubleValueData((double)cruPauseSwitch_VALUE / 100,2) + "");
                                break;
                            case StatusType.cruiControlEnable_VALUE: // Cruise
                                // control
                                // enable/巡航控制使能
                                int  cruiControlEnable_VALUE = new Long( data
                                        .getStatusValue()).intValue();
                                queryRemoteInfoPojo.setCruiControlEnable(NumberFormatUtil.getDoubleValueData((double)cruiControlEnable_VALUE / 100, 2) + "");
                                break;
                            case StatusType.ptoState_VALUE: // PTO state/PTO 状态
                                int  ptoState_VALUE = new Long(data.getStatusValue()).intValue();
                                switch (ptoState_VALUE) {
                                    case 0:
                                        queryRemoteInfoPojo.setPtoState("Disable/Off");
                                    case 1:
                                        queryRemoteInfoPojo.setPtoState("Hold");
                                    case 2:
                                        queryRemoteInfoPojo.setPtoState("Not used");
                                    case 3:
                                        queryRemoteInfoPojo.setPtoState("Standby/Neutral");
                                    case 4:
                                        queryRemoteInfoPojo.setPtoState("Not used");
                                    case 5:
                                        queryRemoteInfoPojo.setPtoState("Set");
                                    case 6:
                                        queryRemoteInfoPojo.setPtoState("Decelerate");
                                    case 7:
                                        queryRemoteInfoPojo.setPtoState("Resume");
                                    case 8:
                                        queryRemoteInfoPojo.setPtoState("Acc");
                                    case 9:
                                        queryRemoteInfoPojo.setPtoState("Not used");
                                    case 10:
                                        queryRemoteInfoPojo.setPtoState("Programmed set speed 0");
                                    case 11:
                                        queryRemoteInfoPojo.setPtoState("Programmed set speed 1");
                                    case 12:
                                        queryRemoteInfoPojo.setPtoState("Programmed set speed 2");
                                    case 13:
                                        queryRemoteInfoPojo.setPtoState("Programmed set speed 3");
                                    case 31:
                                        queryRemoteInfoPojo.setPtoState("Not available");
                                    default:
                                        queryRemoteInfoPojo.setPtoState("Not used");
                                }
                                break;
                            case StatusType.engShutOverSwitch_VALUE: // Engine shut
                                // down
                                // override
                                // switch
                                // :00:Off
                                int engShutOverSwitch_VALUE = new Long(data
                                        .getStatusValue()).intValue()/100;
                                if(engShutOverSwitch_VALUE == 0){
                                    queryRemoteInfoPojo.setEngShutOverSwitch("Off");
                                }else{
                                    queryRemoteInfoPojo.setEngShutOverSwitch("On");
                                }

                                break;
                            case StatusType.engIdleDecSwitch_VALUE: // Engine Idle
                                // decrement
                                // Switch.同上
                                int engIdleDecSwitch_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                if(engIdleDecSwitch_VALUE == 0){
                                    queryRemoteInfoPojo.setEngIdleDecSwitch("Off");
                                }else{
                                    queryRemoteInfoPojo.setEngIdleDecSwitch("On");
                                }

                                break;
                            case StatusType.engIdleIncSwitch_VALUE: // Engine Idle
                                // increment
                                // switch.同上
                                int engIdleIncSwitch_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                if(engIdleIncSwitch_VALUE == 0){
                                    queryRemoteInfoPojo.setEngIdleIncSwitch("Off");
                                }else{
                                    queryRemoteInfoPojo.setEngIdleIncSwitch("On");
                                }

                                break;
                            case StatusType.retarTorMode_VALUE: // Retarder Torque
                                // Mode/缓速器扭矩模式
                                int retarTorMode_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setRetarTorMode(getRetarTorMode(retarTorMode_VALUE));
                                break;
                            case StatusType.retBrakeAssSwitch_VALUE: // Retarder
                                // Enable -
                                // Brake
                                // Assist
                                // Switch/刹车辅助开关
                                long retBrakeAssSwitch_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setRetBrakeAssSwitch(NumberFormatUtil.getDoubleValueData((double)retBrakeAssSwitch_VALUE / 100, 2) + "");
                                break;
                            case StatusType.retShiftAssSwitch_VALUE: // Retarder
                                // Enable -
                                // Shift
                                // Assist
                                // Switch/换挡辅助开关
                                long retShiftAssSwitch_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setRetShiftAssSwitch(NumberFormatUtil.getDoubleValueData((double)retShiftAssSwitch_VALUE / 100, 2) + "");
                                break;
                            case StatusType.actRetTorPer_VALUE: // Actual Retarder
                                // Torque
                                // Percentage//缓速器实际扭矩百分比
                                long actRetTorPer_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setActRetTorPer(NumberFormatUtil.getDoubleValueData((double)actRetTorPer_VALUE / 100, 2)+"");
                                break;
                            case StatusType.intRetPerTor_VALUE: // Intended Retarder
                                // percent
                                // Torque/缓速器需求扭矩
                                long intRetPerTor_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setIntRetPerTor(NumberFormatUtil.getDoubleValueData((double)intRetPerTor_VALUE / 100, 2) + "");
                                break;
                            case StatusType.coolLoadIncrease_VALUE: // Coolant Load
                                // Increase
                                long coolLoadIncrease_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setCoolLoadIncrease(NumberFormatUtil.getDoubleValueData((double)coolLoadIncrease_VALUE / 100, 2) + "");
                                break;
                            case StatusType.retReqBrakeLight_VALUE: // Retarder
                                // Requesting
                                // Brake
                                // Light/缓速器需求灯
                                long retReqBrakeLight_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setRetReqBrakeLight(NumberFormatUtil.getDoubleValueData((double)retReqBrakeLight_VALUE / 100,2)+ "");
                                break;
                            case StatusType.addOfControDevice_VALUE: // Source
                                // Address
                                // of
                                // controlling
                                // device
                                // for
                                // retarder
                                // control/缓速器控制单元地址
                                long addOfControDevice_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setAddOfControDevice(NumberFormatUtil.getDoubleValueData((double)addOfControDevice_VALUE / 100, 2)+ "");
                                break;
                            case StatusType.demRetPerTor_VALUE: // Driver’s Demand
                                // Retarder Percent
                                // Torque/缓速器需求扭矩百分比
                                long demRetPerTor_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setDemRetPerTor(NumberFormatUtil.getDoubleValueData((double)demRetPerTor_VALUE / 100, 2)+"");
                                break;
                            case StatusType.retSwitchPerTor_VALUE: // Retarder
                                // Switch
                                // Percent
                                // Torque/缓速器开关扭矩百分比
                                long retSwitchPerTor_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setRetSwitchPerTor(NumberFormatUtil.getDoubleValueData((double)retSwitchPerTor_VALUE / 100, 2)+"");
                                break;
                            case StatusType.actMaxAvailableRetPerTor_VALUE: // Actual
                                // Maximum
                                // Available
                                // retarder
                                // Percent
                                // Torque/实际最大缓速器扭矩百分比
                                long actMaxAvailableRetPerTor_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setActMaxAvailableRetPerTor(NumberFormatUtil.getDoubleValueData((double)actMaxAvailableRetPerTor_VALUE / 100,2)+"");
                                break;
                            case StatusType.retarderLocation_VALUE: // Retarder
                                // Location/缓速器位置
                                int retarderLocation_VALUE = new Long(data.getStatusValue()).intValue();
                                if(retarderLocation_VALUE ==0){
                                    queryRemoteInfoPojo.setRetarderLocation("Engine compression Release");
                                }else if(retarderLocation_VALUE ==1){
                                    queryRemoteInfoPojo.setRetarderLocation("Engine Exhaust Brake");
                                }else if(retarderLocation_VALUE ==31){
                                    queryRemoteInfoPojo.setRetarderLocation("Not available");
                                }else{
                                    queryRemoteInfoPojo.setRetarderLocation("Not used");
                                }
                                break;
                            case StatusType.retarderType_VALUE: // Retarder
                                // Type/缓速器类型
                                int retarderType_VALUE = new Long(data.getStatusValue()).intValue();
                                switch (retarderType_VALUE) {
                                    case 3:
                                        queryRemoteInfoPojo.setRetarderType("compression Release");
                                    case 4:
                                        queryRemoteInfoPojo.setRetarderType("Exhaust");
                                    case 31:
                                        queryRemoteInfoPojo.setRetarderType("Not available");
                                    default:
                                        queryRemoteInfoPojo.setRetarderType("Not used");
                                }
                                break;
                            case StatusType.retarderConMethod_VALUE: // Retarder
                                // Control
                                // Method/缓速器控制方式
                                int retarderConMethod_VALUE =new Long(data
                                        .getStatusValue()).intValue();
                                if(retarderConMethod_VALUE==0){
                                    queryRemoteInfoPojo.setRetarderConMethod("continuous control");
                                }else if(retarderConMethod_VALUE==1){
                                    queryRemoteInfoPojo.setRetarderConMethod("On/Off control");
                                }else{
                                    queryRemoteInfoPojo.setRetarderConMethod("Number of steps");
                                }
                                break;
                            case StatusType.retarderSpIdlePoint1_VALUE: // Retarder
                                // Speed at
                                // Idle -
                                // Point
                                // 1/缓速器低怠速
                                long retarderSpIdlePoint1_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setRetarderSpIdlePoint1(NumberFormatUtil.getDoubleValueData((double)retarderSpIdlePoint1_VALUE / 100,2) + "");
                                break;
                            case StatusType.retarderPerTorIdle_VALUE: // Retarder
                                // Percent
                                // Torque
                                // Idle/缓速器怠速扭矩百分比
                                long retarderPerTorIdle_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setRetarderPerTorIdle(NumberFormatUtil.getDoubleValueData((double)retarderPerTorIdle_VALUE / 100, 2)+"");
                                break;
                            case StatusType.maxRetSpeedPoint2_VALUE: // Maximum
                                // Retarder
                                // Speed
                                // Point
                                // 2/最大速度2
                                long maxRetSpeedPoint2_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setMaxRetSpeedPoint2(NumberFormatUtil.getDoubleValueData((double)maxRetSpeedPoint2_VALUE / 100, 2)+ "");
                                break;
                            case StatusType.retPerTorMaxSpeedPoint2_VALUE: // Retarder
                                // Percent
                                // Torque
                                // at
                                // Maximum
                                // Speed,
                                // Point
                                // 2/转速2缓速器扭矩百分比
                                long retPerTorMaxSpeedPoint2_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setRetPerTorMaxSpeedPoint2(NumberFormatUtil.getDoubleValueData((double)retPerTorMaxSpeedPoint2_VALUE / 100,2)+"");
                                break;
                            case StatusType.engSpeedPoint3_VALUE: // Engine Speed
                                // Point 3/转速3
                                long engSpeedPoint3_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setEngSpeedPoint3(NumberFormatUtil.getDoubleValueData((double)engSpeedPoint3_VALUE / 100,2)+"");
                                break;
                            case StatusType.retarderPerTorPoint3_VALUE: // Retarder
                                // Percent
                                // Torque -
                                // Point
                                // 3/转速3扭矩百分比
                                long retarderPerTorPoint3_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setRetarderPerTorPoint3(NumberFormatUtil.getDoubleValueData((double)retarderPerTorPoint3_VALUE / 100,2)+"");
                                break;
                            case StatusType.engSpeedPoint4_VALUE: // Engine Speed
                                // Point 4/转速4
                                long engSpeedPoint4_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setEngSpeedPoint4(NumberFormatUtil.getDoubleValueData((double)engSpeedPoint4_VALUE / 100, 2)+"");
                                break;
                            case StatusType.retarderPerTorPoint4_VALUE: // Retarder
                                // Percent
                                // Torque,
                                // Point
                                // 4/转速4扭矩百分比
                                long retarderPerTorPoint4_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setRetarderPerTorPoint4(NumberFormatUtil.getDoubleValueData((double)retarderPerTorPoint4_VALUE / 100, 2)+"");
                                break;
                            case StatusType.retSpeedTorPoint5_VALUE: // Retarder
                                // Speed at
                                // peak
                                // torque
                                // Point
                                // 5/最大扭矩转速5
                                long retSpeedTorPoint5_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setRetSpeedTorPoint5(NumberFormatUtil.getDoubleValueData((double)retSpeedTorPoint5_VALUE / 100, 2)+"");
                                break;
                            case StatusType.refRetarTorque_VALUE: // Reference
                                // Retarder
                                // Torque/缓速器参考扭矩
                                long refRetarTorque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setRefRetarTorque(NumberFormatUtil.getDoubleValueData((double)refRetarTorque_VALUE / 100,2) + "");
                                break;
                            case StatusType.retarderPerTorPoint5_VALUE: // Retarder
                                // Percent
                                // Torque -
                                // Point
                                // 5/转速5扭矩百分比
                                long retarderPerTorPoint5_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setRetarderPerTorPoint5(NumberFormatUtil.getDoubleValueData((double)retarderPerTorPoint5_VALUE / 100,2)+"");
                                break;

                            case StatusType.P1Speed_VALUE:// Point 1-engine speed at
                                // idle/怠速转速
                                long P1Speed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP1Speed(NumberFormatUtil.getDoubleValueData((double)P1Speed_VALUE / 100, 2)+"");
                                break;
                            case StatusType.P1Torque_VALUE:// Point 1-percent torque
                                // at idle/怠速扭矩
                                long P1Torque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setP1Torque(NumberFormatUtil.getDoubleValueData((double)
                                                P1Torque_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P3Speed_VALUE:// Point 3-engine speed
                                long P3Speed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP3Speed(NumberFormatUtil.getDoubleValueData((double)P3Speed_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P3Torque_VALUE:// Point 3-percent torque
                                long P3Torque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setP3Torque(NumberFormatUtil.getDoubleValueData((double)
                                                P3Torque_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P4Speed_VALUE:// Point 4-percent speed
                                long P4Speed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP4Speed(NumberFormatUtil.getDoubleValueData((double)P4Speed_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P4Troque_VALUE:// Point 4-percent torque
                                long P4Troque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setP4Troque(NumberFormatUtil.getDoubleValueData((double)
                                                P4Troque_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P5Speed_VALUE:// Point 5-engine speed
                                long P5Speed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP5Speed(NumberFormatUtil.getDoubleValueData((double)P5Speed_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P5Torque_VALUE:// Point 5-percent torque
                                long P5Torque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setP5Torque(NumberFormatUtil.getDoubleValueData((double)
                                                P5Torque_VALUE / 100, 2) + "");
                                break;
                            case StatusType.P6Speed_VALUE:// Point 6-engine speed
                                long P6Speed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP6Speed(NumberFormatUtil.getDoubleValueData((double)P6Speed_VALUE / 100, 2) + "");
                                break;
                            case StatusType.gainSpeed_VALUE:// Byte of gain of
                                // end-speed governor
                                long gainSpeed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setGainSpeed(NumberFormatUtil.getDoubleValueData((double)gainSpeed_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.RefEngineTorque_VALUE:// Reference
                                // engine
                                // torque/参考扭矩
                                long RefEngineTorque_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setRefEngineTorque(NumberFormatUtil.getDoubleValueData((double)RefEngineTorque_VALUE / 100,
                                        2)
                                        + "");
                                break;
                            case StatusType.MaxSpeedLimit_VALUE:// Point 7-Byte of
                                // max momentary
                                // engine override
                                // speed limit
                                long MaxSpeedLimit_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setMaxSpeedLimit(NumberFormatUtil.getDoubleValueData((double)MaxSpeedLimit_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.MaxTimeLimit_VALUE:// Maximum momentary
                                // engine override
                                // time limit
                                long MaxTimeLimit_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setMaxTimeLimit(NumberFormatUtil.getDoubleValueData((double)MaxTimeLimit_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.SpUpperLimit_VALUE:// Engine Requested
                                // speed control
                                // Range Upper limit
                                long SpUpperLimit_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setSpUpperLimit(NumberFormatUtil.getDoubleValueData((double)SpUpperLimit_VALUE / 100, 2)
                                        + "");
                                break;

                            case StatusType.TorLowerLimit_VALUE:// Engine Requested
                                // Torque control
                                // Range Lower limit
                                long TorLowerLimit_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setTorLowerLimit(NumberFormatUtil.getDoubleValueData((double)TorLowerLimit_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.MomentIner_VALUE:// Engine Moment of
                                // Inertia
                                long MomentIner_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setMomentIner(NumberFormatUtil.getDoubleValueData((double)MomentIner_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.RelEngOilPressure_VALUE:// Relative
                                // Engine oil
                                // pressure
                                long RelEngOilPressure_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo.setRelEngOilPressure(NumberFormatUtil.getDoubleValueData((double)RelEngOilPressure_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.engOilPreLow_VALUE:// Engine Oil
                                // Pressure Low
                                int engOilPreLow_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setEngOilPreLow(NumberFormatUtil.getDoubleValueData((double)engOilPreLow_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.engCoolTem_VALUE:// Engine Over Coolant
                                // Temperature
                                int engCoolTem_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                queryRemoteInfoPojo.setEngCoolTem(getEngCoolTem(engCoolTem_VALUE));
                                break;
                            case StatusType.StartHeartStat_VALUE:// Cold Start
                                // Heater Status
                                int StartHeartStat_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                String status = getStartHeartStart(StartHeartStat_VALUE);
                                queryRemoteInfoPojo.setStartHeartStat(status);
                                break;
                            case StatusType.OBDLampStatus_VALUE:// OBD Lamp Status
                                int OBDLampStatus_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                switch (OBDLampStatus_VALUE) {
                                    case 1:
                                        queryRemoteInfoPojo.setObDLampStatus("Lamp On");
                                    case 2:
                                        queryRemoteInfoPojo.setObDLampStatus("Lamp Blinking");
                                    case 3:
                                        queryRemoteInfoPojo.setObDLampStatus("Lamp Off(V732)");
                                }
                                break;
                            case StatusType.ExOutput_VALUE:// Exhaust flap valve
                                // output
                                long ExOutput_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setExOutput(NumberFormatUtil.getDoubleValueData((double)
                                                ExOutput_VALUE / 100, 2) + "");
                                break;
                            case StatusType.EsFanSp_VALUE:// Estimated percent Fan
                                // speed
                                long EsFanSp_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setEsFanSp(NumberFormatUtil.getDoubleValueData((double)EsFanSp_VALUE / 100, 2) + "");
                                break;
                            case StatusType.FanDriState_VALUE:// Fan Drive state
                                int FanDriState_VALUE = new Long(data.getStatusValue()).intValue()/100;
                                switch (FanDriState_VALUE) {
                                    case 0:
                                        queryRemoteInfoPojo.setFanDriState("Fan off");
                                    case 1:
                                        queryRemoteInfoPojo.setFanDriState("Engine system");
                                    case 2:
                                        queryRemoteInfoPojo.setFanDriState("Excessive engine air temp");
                                    case 3:
                                        queryRemoteInfoPojo.setFanDriState("Excessive engine oil temp");
                                    case 4:
                                        queryRemoteInfoPojo.setFanDriState("Excessive engine Coolant temp");
                                    case 9:
                                        queryRemoteInfoPojo.setFanDriState("manual control");
                                    case 10:
                                        queryRemoteInfoPojo.setFanDriState("Transmission retarder");
                                    case 11:
                                        queryRemoteInfoPojo.setFanDriState("A/C system");
                                    case 12:
                                        queryRemoteInfoPojo.setFanDriState("Timer");
                                    case 13:
                                        queryRemoteInfoPojo.setFanDriState("Engine brake");
                                    case 14:
                                        queryRemoteInfoPojo.setFanDriState("other");
                                    case 15:
                                        queryRemoteInfoPojo.setFanDriState("Not available");
                                    default:
                                        queryRemoteInfoPojo.setFanDriState("Not defined");
                                }
                                break;
                            case StatusType.FanSpeed_VALUE:// Fan speed
                                long FanSpeed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setFanSpeed(NumberFormatUtil.getDoubleValueData((double)FanSpeed_VALUE / 100, 2)+"");
                                break;
                            case StatusType.airFilterPre_VALUE:// Air filter 1
                                // differential
                                // pressure/空滤器压差
                                int airFilterPre_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setAirFilterPre(NumberFormatUtil.getDoubleValueData((double)airFilterPre_VALUE / 100, 2)+"");
                                break;
                            case StatusType.CoolDifferTem_VALUE:// Coolant filter
                                // differential
                                // temperature/冷却器温差
                                long CoolDifferTem_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setCoolDifferTem(NumberFormatUtil.getDoubleValueData((double)CoolDifferTem_VALUE / 100,
                                                2)+"");
                                break;
                            case StatusType.AirInTem_VALUE:// Air inlet
                                // temperature/进气温度
                                long AirInTem_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setAirInTem(NumberFormatUtil.getDoubleValueData((double)AirInTem_VALUE / 100, 2)+"");
                                break;
                            case StatusType.perTorResolution_VALUE:// Actual engine
                                // - percent
                                // torque high
                                // resolution
                                int perTorResolution_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setPerTorResolution(NumberFormatUtil.getDoubleValueData((double)perTorResolution_VALUE / 100,
                                        2) + "");
                                break;
                            case StatusType.addControlDevice_VALUE:// Source Address
                                // of
                                // Controlling
                                // Device for
                                // Engine
                                // Control/控制发动机设备源地址
                                long addControlDevice_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setAddControlDevice(NumberFormatUtil.getDoubleValueData((double)addControlDevice_VALUE / 100,
                                        2) + "");
                                break;
                            case StatusType.engineStartMode_VALUE:// Engine Starter
                                // Mode/起动机模式
                                int engineStartMode_VALUE =new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setEngineStartMode(NumberFormatUtil.getDoubleValueData((double)engineStartMode_VALUE / 100,
                                        2) + "");
                                break;
                            case StatusType.engineDePerTor_VALUE:// Engine Demand -
                                // Percent
                                // Torque/发动机需求扭矩百分比
                                long engineDePerTor_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setEngineDePerTor(NumberFormatUtil.getDoubleValueData((double)engineDePerTor_VALUE / 100,
                                                2)+"");
                                break;
                            case StatusType.speedLimitStatus_VALUE:// Road speed
                                // limit
                                // status/速度限制状态
                                int speedLimitStatus_VALUE = new Long(
                                        data.getStatusValue()).intValue() / 100;
                                queryRemoteInfoPojo
                                        .setSpeedLimitStatus(getSpeedLimitStatus(speedLimitStatus_VALUE));
                                break;
                            case StatusType.reAccPosition_VALUE:// Remote
                                // accelerator
                                // position
                                long reAccPosition_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setReAccPosition(NumberFormatUtil.getDoubleValueData((double)reAccPosition_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.reAccPosition2_VALUE:// Remote
                                // accelerator
                                // position2
                                long reAccPosition2_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo
                                        .setReAccPosition2(NumberFormatUtil.getDoubleValueData((double)reAccPosition2_VALUE / 100,
                                                2) + "");
                                break;
                            case StatusType.maxAvailableEngPerTor_VALUE:// Actual
                                // maximum
                                // available
                                // engine
                                // percentage
                                // torque
                                long maxAvailableEngPerTor_VALUE = data
                                        .getStatusValue();
                                queryRemoteInfoPojo
                                        .setMaxAvailableEngPerTor(NumberFormatUtil.getDoubleValueData((double)maxAvailableEngPerTor_VALUE / 100,
                                                2) + "");
                                break;
                            case StatusType.desirOperaSpeed_VALUE:// engine's
                                // desired
                                // operating
                                // speed/发动机目标运行速度
                                long desirOperaSpeed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setDesirOperaSpeed(NumberFormatUtil.getDoubleValueData((double)desirOperaSpeed_VALUE / 100,
                                        2)+"");
                                break;
                            case StatusType.engAsyAdjust_VALUE:// engine's operating
                                // speed asymmetry
                                // adjustment/发动机平稳调整
                                long engAsyAdjust_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setEngAsyAdjust(NumberFormatUtil.getDoubleValueData((double)engAsyAdjust_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.engineInterTemper_VALUE:
                                long engineInterTemper_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setEngineInterTemper(NumberFormatUtil.getDoubleValueData((double) engineInterTemper_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.batteryPotInput1_VALUE:
                                long batteryPotInput1_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setBatteryPotInput1(NumberFormatUtil.getDoubleValueData((double) batteryPotInput1_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.P2HighestSpeed_VALUE:
                                long P2HighestSpeed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setP2HighestSpeed(NumberFormatUtil.formatNumber(P2HighestSpeed_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.P2PercentTorSpeed_VALUE:
                                long P2PercentTorSpeed_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setEngineInterTemper(NumberFormatUtil.getDoubleValueData((double) P2PercentTorSpeed_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.integralFuelConsumption_VALUE:    //积分油耗
                                long integralFuelConsumption_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setIntegralFuelConsumption(NumberFormatUtil.getDoubleValueData((double) integralFuelConsumption_VALUE / 100, 2)
                                        + "");
                                break;
                            case StatusType.dynamicLoad_VALUE :    //整车载重
                                  long dynamicLoad_VALUE = data.getStatusValue();
                                  queryRemoteInfoPojo.setCarLoad(NumberFormatUtil.getDoubleValueData((double) dynamicLoad_VALUE / 100, 2)
                                          + "");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }catch(Exception e) {
            logger.error("",e);
        }
        return queryRemoteInfoPojo;
    }
    public QueryRemoteInfoPojo getcarMessageData(LCLocationData.LocationData locationData,QueryRemoteInfoPojo queryRemoteInfoPojo){
        try {
            queryRemoteInfoPojo.setAccPedalLowIdleSwitch("");
            LCLocationData.VehicleStatusAddition statusAddition = locationData.getStatusAddition();
            if(statusAddition != null) {
                List<LCVehicleStatusData.VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
                if(VehicleStatusDataList != null) {
                    for(LCVehicleStatusData.VehicleStatusData data : VehicleStatusDataList) {
                        switch(data.getTypes().getNumber()) {
                            case StatusType.engineTorMode_VALUE://转矩控制模式
                                int engineTorMode_VALUE = new Long(data.getStatusValue()/100).intValue();
                                switch (engineTorMode_VALUE) {
                                    case 0:
                                        queryRemoteInfoPojo.setEngineTorMode("低怠速调节器/无请求");
                                        break;
                                    case 1:
                                        queryRemoteInfoPojo.setEngineTorMode("加速踏板");
                                        break;
                                    case 2:
                                        queryRemoteInfoPojo.setEngineTorMode("巡航控制");
                                        break;
                                    case 3:
                                        queryRemoteInfoPojo.setEngineTorMode("PTO调节器");
                                        break;
                                    case 4:
                                        queryRemoteInfoPojo.setEngineTorMode("车速调节器");
                                        break;
                                    case 5:
                                        queryRemoteInfoPojo.setEngineTorMode("ASR控制");
                                        break;
                                    case 6:
                                        queryRemoteInfoPojo.setEngineTorMode("变速器控制");
                                        break;
                                    case 7:
                                        queryRemoteInfoPojo.setEngineTorMode("ABS控制");
                                        break;
                                    case 8:
                                        queryRemoteInfoPojo.setEngineTorMode("转矩限定");
                                        break;
                                    case 9:
                                        queryRemoteInfoPojo.setEngineTorMode("高速调节器");
                                        break;
                                    case 10:
                                        queryRemoteInfoPojo.setEngineTorMode("制动系统");
                                        break;
                                    case 11:
                                        queryRemoteInfoPojo.setEngineTorMode("遥控加速器");
                                        break;
                                }
                                break;
                            case StatusType.driverEnginePercentTor_VALUE://驾驶员需求发动机转矩百分比
                                int driverEnginePercentTor_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setDriverEnginePercentTor(NumberFormatUtil.getDoubleValueData((double)driverEnginePercentTor_VALUE/100, 2)+"");
                                break;
                            case StatusType.actualEnginePercentTor_VALUE://实际发动机转矩百分比
                                int actualEnginePercentTor_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setActualEnginePercentTor(NumberFormatUtil.getDoubleValueData((double)actualEnginePercentTor_VALUE/100, 2)+"");
                                break;
                            case StatusType.accPedalLowIdleSwitch_VALUE://加速踏板低怠速开关
                                int accPedalLowIdleSwitch_VALUE = new Long(data.getStatusValue()).intValue();
                                if(accPedalLowIdleSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setAccPedalLowIdleSwitch("不处于低怠速模式");
                                }else{
                                    queryRemoteInfoPojo.setAccPedalLowIdleSwitch("处于低怠速模式");
                                }
                                break;
                            case StatusType.accPedalKickdownSwitch_VALUE:
                                int accPedalKickdownSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(accPedalKickdownSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setAccPedalKickdownSwitch("被动");
                                }else if(accPedalKickdownSwitch_VALUE==1){
                                    queryRemoteInfoPojo.setAccPedalKickdownSwitch("主动");
                                }
                                break;
                            case StatusType.accPedalPos_VALUE://加速踏板开度
                                int accPedalPos_VALUE = new Long(data.getStatusValue()/100).intValue();
                                queryRemoteInfoPojo.setAccPedalPos(String.valueOf(accPedalPos_VALUE));
                                break;
                            case StatusType.percentLoadAtCurrentSpd_VALUE://当前速度下，负载百分比
                                int percentLoadAtCurrentSpd_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setPercentLoadAtCurrentSpd(NumberFormatUtil.getDoubleValueData((double)percentLoadAtCurrentSpd_VALUE/100, 2)+"");
                                break;
                            case StatusType.nominalFrictionPercentTrq_VALUE://名义摩擦力矩百分比
                                int nominalFrictionPercentTrq_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setNominalFrictionPercentTrq(NumberFormatUtil.getDoubleValueData((double)nominalFrictionPercentTrq_VALUE/100, 2)+"");
                                break;
                            case StatusType.parkingBrakeSwitch_VALUE://驻车制动器开关
                                int parkingBrakeSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(parkingBrakeSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setParkingBrakeSwitch("未操作");
                                }else if(parkingBrakeSwitch_VALUE==1){
                                    queryRemoteInfoPojo.setParkingBrakeSwitch("操作");
                                }
                                break;
                            case StatusType.wheelBasedVehicleSpd_VALUE://车轮车速
                                Long wheelBasedVehicleSpd_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setWheelBasedVehicleSpd(NumberFormatUtil.getDoubleValueData((double)wheelBasedVehicleSpd_VALUE/100, 2)+"");
                                break;
                            case StatusType.cruiseCtrlActive_VALUE:
                                int cruiseCtrlActive_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(cruiseCtrlActive_VALUE==0){
                                    queryRemoteInfoPojo.setCruiseCtrlActive("未激活");
                                }else{
                                    queryRemoteInfoPojo.setCruiseCtrlActive("激活");
                                }
                                break;
                            case StatusType.brakeSwitch_VALUE:
                                int brakeSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(brakeSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setBrakeSwitch("未操作");
                                }else{
                                    queryRemoteInfoPojo.setBrakeSwitch("操作");
                                }
                                break;
                            case StatusType.clutchSwitch_VALUE://离合器开关
                                int clutchSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(clutchSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setClutchSwitch("未分离");
                                }else{
                                    queryRemoteInfoPojo.setClutchSwitch("分离");
                                }
                                break;
                            case StatusType.cruiseCtrlSetSwitch_VALUE://巡航控制设置开关
                                int cruiseCtrlSetSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(cruiseCtrlSetSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setCruiseCtrlSetSwitch("无按钮按下");
                                }else{
                                    queryRemoteInfoPojo.setCruiseCtrlSetSwitch("退出巡航");
                                }
                                break;
                            case StatusType.cruiseCtrlCoastSwitch_VALUE:
                                int cruiseCtrlCoastSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(cruiseCtrlCoastSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setCruiseCtrlCoastSwitch("未按下");
                                }else{
                                    queryRemoteInfoPojo.setCruiseCtrlCoastSwitch("按下");
                                }
                                break;
                            case StatusType.cruiseCtrlResumeSwitch_VALUE://巡航控制恢复开关
                                int cruiseCtrlResumeSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(cruiseCtrlResumeSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setCruiseCtrlResumeSwitch("未按下");
                                }else{
                                    queryRemoteInfoPojo.setCruiseCtrlResumeSwitch("按下");
                                }
                                break;
                            case StatusType.cruiseCtrlAccSwitch_VALUE://巡航控制加速开关
                                int cruiseCtrlAccSwitch_VALUE = new Long(data.getStatusValue()/100).intValue();
                                if(cruiseCtrlAccSwitch_VALUE==0){
                                    queryRemoteInfoPojo.setCruiseCtrlAccSwitch("未按下");
                                }else{
                                    queryRemoteInfoPojo.setCruiseCtrlAccSwitch("按下");
                                }
                                break;
                            case StatusType.cruiseCtrlSetSpd_VALUE://巡航控制设置速度
                                long cruiseCtrlSetSpd_VALUE =new Long( data.getStatusValue()/100).intValue();
                                queryRemoteInfoPojo.setCruiseCtrlSetSpd(NumberFormatUtil.getDoubleValueData((double)cruiseCtrlSetSpd_VALUE, 2)+"");
                                break;
                            case StatusType.cruiseCtrlStates_VALUE://巡航控制状态
                                int cruiseCtrlStates_VALUE = new Long(data.getStatusValue()/100).intValue();
                                switch (cruiseCtrlStates_VALUE) {
                                    case 0:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("关闭/禁止");
                                        break;
                                    case 1:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("保持");
                                        break;
                                    case 2:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("加速");
                                        break;
                                    case 3:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("减速/滑行");
                                        break;
                                    case 4:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("恢复");
                                        break;
                                    case 5:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("设置");
                                        break;
                                    case 6:
                                        queryRemoteInfoPojo.setCruiseCtrlStates("加速踏板取代");
                                        break;
                                }
                                break;
                            case StatusType.tripDistance_VALUE://日里程
                                long tripDistance_VALUE = data.getStatusValue();
                                queryRemoteInfoPojo.setDailyMileage(String.valueOf(NumberFormatUtil.getDoubleValueData((double)tripDistance_VALUE/100, 2)));
                                break;
                            case StatusType.realTimeOilConsumption_VALUE://实时油耗
                                int realTimeOilConsumption_VALUE = new Long(data.getStatusValue()).intValue();
                                queryRemoteInfoPojo.setRealTimeOilConsumption(NumberFormatUtil.getDoubleValueData((double)realTimeOilConsumption_VALUE/100, 2)+"");
                                break;
//                            case StatusType.totalFuelConsumption_VALUE ://总油耗
//                                if(null!=queryRemoteInfoPojo.getFuel()&&queryRemoteInfoPojo.getFuel()==0){
//                                    queryRemoteInfoPojo.setTotalOil(String.valueOf(CloudUtil.getUseGas(locationData)));
//                                }else{
//                                    int totalFuelConsumption_VALUE = new Long(data.getStatusValue()).intValue();
//                                    queryRemoteInfoPojo.setTotalOil(new BigDecimal(String.valueOf(NumberFormatUtil.getStatusValue(totalFuelConsumption_VALUE))).toPlainString());
//                                }
//                                break;
                            case StatusType.outPutRoateSpeed_VALUE://输出轴速度
                                int outPutRoateSpeed_VALUE = new Long(data.getStatusValue()/100).intValue();
                                queryRemoteInfoPojo.setOutPutRoateSpeed(outPutRoateSpeed_VALUE+"");
                                break;
                            case StatusType.tachographVehicleSpeed_VALUE://车速(km/h)
                                int tachographVehicleSpeed_VALUE = new Long(data.getStatusValue()/100).intValue();
                                queryRemoteInfoPojo.setTachographVehicleSpeed(NumberFormatUtil.getDoubleValueData((double)tachographVehicleSpeed_VALUE/100, 2)+"");
                                break;
                        }
                    }
                }
            }
        }catch(Exception e) {
            logger.error("",e);
        }
        return queryRemoteInfoPojo;
    }
    //故障码
    public  QueryRemoteInfoPojo getBreakdown(LCLocationData.LocationData locationData,QueryRemoteInfoPojo queryRemoteInfoPojo) {
        try {
            List<BaseData> faulist = baseDataService.queryList(Integer.parseInt(queryRemoteInfoPojo.getEngineType()));
            //update fwm 20160811 故障数据未查到处理
            if(ListUtil.isEmpty(faulist)){
                return queryRemoteInfoPojo;
            }
            LCLocationData.VehicleBreakdownAddition vehicleBreakdownAddition = locationData.getBreakdownAddition();
            if(vehicleBreakdownAddition != null) {
                if(vehicleBreakdownAddition.getBreakdownList() != null) {
                    StringBuilder spn_fmis = new StringBuilder("");
                    for(LCVehicleBreakdown.VehicleBreakdown breakdown : vehicleBreakdownAddition.getBreakdownList()) {
                        int spn = breakdown.getBreakdownSPNValue();
                        int fmi = breakdown.getBreakdownFMIValue();
                        String result = "";
                        if(!StringUtil.isEmpty(queryRemoteInfoPojo.getEngineType())&& CheckUtil.isNumericZidai(queryRemoteInfoPojo.getEngineType())){
                            result = CloudUtil.getFault2(breakdown,faulist ,queryRemoteInfoPojo.getEngineType());
                        }
                        if(result != null && "".equals(result)) {
                            result = CloudConstants.UNKNOW;
                        }
                        String temp = "SPN:"+spn +" FMI:"+fmi+"          "+result;
                        spn_fmis.append(temp).append(",");
                    }
                    queryRemoteInfoPojo.setBreakdown(spn_fmis.toString());
                }
            }
        }catch(Exception e) {
            logger.error("",e);
        }
        return queryRemoteInfoPojo;
    }

    public static String getStartHeartStart(int value) {
        String status = "";
        switch (value) {
            case 0:
                status = "Off phase";
                break;
            case 1:
                status = "Pre-heating phase";
                break;
            case 2:
                status = "heater on,lamp blinking,cranking recommended";
                break;
            case 3:
                status = "heater off,lamp off";
                break;
            case 4:
                status = "heater on,lamp off";
                break;
            case 5:
                status = "Crank phase";
                break;
            case 6:
                status = "Post heating phase";
                break;
            case 7:
                status = "Heating phase end";
                break;
            case 8:
                status = "After run phase";
                break;
            default:
                status = "Not defined";
                break;
        }
        return status;
    }
    /**
     * engCoolTem字段转换
     **/
    public static String getEngCoolTem(int value){
        if(value == 0){
            return "Normal";
        }
        if(value == 1){
            return "Pre-warning";
        }
        if(value == 2){
            return "Warning";
        }
        return "";
    }
    /**
     * engOilPreLow字段转换
     */
    public static String getOilPreLow(int value){
        if(value == 0){
            return "Normal";
        }
        if(value == 1){
            return "Below operating range";
        }
        if(value == 2){
            return "Not available";
        }
        return "";
    }
    /**
     * speedLimitStatus字段转换
     */
    public static String getSpeedLimitStatus(int value){
        if(value == 0){
            return "active";
        }
        if(value == 1){
            return "Not active";
        }
        if(value == 2){
            return "Error";
        }
        if(value == 3){
            return "Not available";
        }
        return "";
    }
    /**
     * af1Intake字段转换 af1Exhaust af2Exhaust
     */
    public static String getAfIntake(int value){
        String status="";
        switch (value) {
            case 0:
                status="Not exceeded the dew point";
                break;
            case 1:
                status="Exceeded the dew point";
                break;
            case 3:
                status="Error";
                break;
            case 4:
                status="Not available";
                break;
        }
        return status;
    }
    /**
     * retarTorMode字段转换
     */
    public static String getRetarTorMode(int value){
        String status="";
        switch (value) {
            case 0:
                status="No request";
                break;
            case 5:
                status="ASR control";
                break;
            case 6:
                status="Transmission control";
                break;
            case 10:
                status="DR control";
                break;
            case 14:
                status="VM control";
                break;
        }
        return status;
    }
}
