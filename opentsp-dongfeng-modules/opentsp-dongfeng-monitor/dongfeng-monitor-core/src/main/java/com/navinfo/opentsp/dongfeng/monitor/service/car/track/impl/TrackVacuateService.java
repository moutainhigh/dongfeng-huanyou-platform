package com.navinfo.opentsp.dongfeng.monitor.service.car.track.impl;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCStatusType;
import com.lc.core.protocol.common.LCVehicleStatusData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData;
import com.lc.core.protocol.webservice.originaldata.LCTerminalTrackRes;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.CloudConstants;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.ExportTrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.track.TrackVacuateCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.car.QueryTracesConverter;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCommonCarPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryTracesPojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.track.ITrackVacuateService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author xltianc.zh
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class TrackVacuateService extends BaseService implements ITrackVacuateService {

	protected static final Logger logger = LoggerFactory.getLogger(TrackVacuateService.class);

	@Autowired
	private IBaseDataService iBaseDataService;

	@Autowired
	private MailClient mailClient;

	@Resource
	private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
	@Value("${trace.acc.data.enable}")
	private String accDataSwitch;

	@SuppressWarnings("unchecked")
	@Override
	public HttpCommandResultWithData trackVacuate(TrackVacuateCommand command) throws Exception {
		HttpCommandResultWithData resultWithData = new HttpCommandResultWithData();

		String beginDate = command.getBeginDate();
		String endDate = command.getEndDate();
		//COMMAND已经验空
		if(DateUtil.strTimeChangeLong(beginDate)>DateUtil.strTimeChangeLong(endDate)){
			resultWithData = resultWithData.fillResult(ReturnCode.TIME_BEGIN_AND_END_CHECK);
			return resultWithData;
		}
		List<QueryTracesPojo> list = new ArrayList<QueryTracesPojo>();
		String carId = command.getCarId();
		Map<String, Object> parm = new HashMap<>();
		parm.put("carId", Long.valueOf(carId));
		parm.put("accountId", command.getUserInfor().getUserId());
		parm.put("accountType", command.getUserInfor().getType());
		QueryCommonCarPojo queryCommonCarPojo = (QueryCommonCarPojo) dao.sqlFindObject("queryCommonCar_extend_2", parm,
				QueryCommonCarPojo.class);
		if (null == queryCommonCarPojo || null == queryCommonCarPojo.getCommId()) {
			return resultWithData;
		}

		long commId = queryCommonCarPojo.getCommId().longValue();
		String engineType = queryCommonCarPojo.getEngineType();
		if (StringUtil.isEmpty(queryCommonCarPojo.getOilCapacity())) {
			queryCommonCarPojo.setOilCapacity(CloudConstants.DEFAULT_OILCAPACITY);
		}
		List<BaseData> faulist = new ArrayList<BaseData>();
		if (!(StringUtil.isEmpty(engineType) || !CheckUtil.isNumericZidai(engineType))) {
			faulist = iBaseDataService.queryList(Integer.valueOf(engineType));
		}
		// 根据车辆id获取车辆信息 ------end
		// 发起rmi接口调用
		BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration
				.getBasicDataQueryWebService();
		CommonParameterSerializer commonParameter = new CommonParameterSerializer();
		commonParameter.setAccessTocken(0);
		commonParameter.setMultipage(false);

		// TODO 需要适配新的轨迹抽稀接口
		byte[] data = basicDataQueryWebService.getTerminalTrack(commId, DateUtil.strTimeChangeLong(beginDate),
				DateUtil.strTimeChangeLong(endDate), false, -1L, true, Integer.parseInt(command.getLevel()),
				commonParameter,-1);
		if (null != data) {
			// 解析返回结果
			LCTerminalTrackRes.TerminalTrackRes builder = LCTerminalTrackRes.TerminalTrackRes.parseFrom(data);
			List<LCTerminalLocationData.TerminalLocationData> resultList = builder.getDatasList();
			// 循环轨迹接口返回的结果,并进行分值
			if (!ListUtil.isEmpty(resultList)) {
				for (LCTerminalLocationData.TerminalLocationData entry : resultList) {
					LCLocationData.LocationData gpslist = entry.getLocationData();
//					if (gpslist != null && (gpslist.getStatus() & 1) > 0) {
					if (gpslist != null && (Boolean.valueOf(accDataSwitch).booleanValue()?((gpslist.getStatus() & 1) > 0):true)) {
					QueryTracesPojo forPojo = new QueryTracesPojo();
						// gps时间
						forPojo.setGpsdate(DateUtil.timeStr(gpslist.getGpsDate()));
						// 车辆速度
						forPojo.setVelocity(
								String.valueOf(NumberFormatUtil.getDoubleValueData(new Double(gpslist.getSpeed()), 1)));
						// 经纬度
						forPojo.setLat(NumberFormatUtil.getLatitudeOrLongitude(gpslist.getLatitude()));
						forPojo.setLng(NumberFormatUtil.getLatitudeOrLongitude(gpslist.getLongitude()));
						// 剩余油/汽量
						if (null != queryCommonCarPojo.getFuel() && queryCommonCarPojo.getFuel() == 0) {
							forPojo.setOilwear(String.valueOf(CloudUtil.getResGas(gpslist)));
						} else {
							forPojo.setOilwear(NumberFormatUtil.getOilwear(CloudUtil.getResOil(gpslist),
									Double.valueOf(queryCommonCarPojo.getOilCapacity())) + "");
						}
						forPojo.setTotolmileage(String.valueOf(gpslist.getStandardMileage()));
						forPojo.setTotalFuelConsumption(String.valueOf(gpslist.getStandardFuelCon()));
						// 状态
						forPojo.setStatue(CloudUtil.getDoorStatus(gpslist.getStatus())
								+ CloudUtil.getAccStatus(gpslist.getStatus())
								+ CloudUtil.getGpsStatus(gpslist.getStatus()));
						// 报警
						forPojo.setAlarm(CloudUtil.getAlarm(gpslist.getAlarm()));
						// 故障
						if (StringUtil.isEmpty(engineType) || !CheckUtil.isNumericZidai(engineType)) {
							forPojo.setFault("");
						} else {
							String fault = CloudUtil.getFault(gpslist.getBreakdownAddition().getBreakdownList(),
									faulist, engineType);
							forPojo.setFault(fault);
						}
						// 接收时间
						forPojo.setReceiveDate(DateUtil.timeStr(gpslist.getReceiveDate()));
						// 方向
						forPojo.setDirection(CloudUtil.getDirection(gpslist.getDirection()));
						// 终端里程
						forPojo.setMileage(
								String.valueOf(NumberFormatUtil.getDoubleValueData(gpslist.getMileage() * 0.001, 1)));
						// 地址
						forPojo.setAddress(forPojo.getLat() + ";" + forPojo.getLng());
						list.add(forPojo);
					}
				}
			}
		}
		return resultWithData.setData(QueryTracesConverter.convertToDtoList(list));
	}

	@Override
	public List<QueryTracesPojo> queryTrackVacuateList(TrackVacuateCommand command) {
		try {
			return traceDataList(command);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<QueryTracesPojo> traceDataList(TrackVacuateCommand command) throws Exception {
		String beginDate = command.getBeginDate();
		String endDate = command.getEndDate();
		String carId = command.getCarId();
		// 组装结果数据
		List<QueryTracesPojo> list = new ArrayList<QueryTracesPojo>();
		// 根据车辆id获取车辆信息 ------start
		Map<String, Object> parm = new HashMap<>();
		parm.put("carId", Long.valueOf(carId));
		parm.put("accountId", command.getUserInfor().getUserId());
		parm.put("accountType", command.getUserInfor().getType());
		QueryCommonCarPojo queryCommonCarPojo = (QueryCommonCarPojo) dao.sqlFindObject("queryCommonCar_extend_2", parm,
				QueryCommonCarPojo.class);
		long commId = 0;
		String engineType = "";
		if (null != queryCommonCarPojo && null != queryCommonCarPojo.getCommId()) {
			commId = queryCommonCarPojo.getCommId().longValue();
			engineType = queryCommonCarPojo.getEngineType();
			// 默认油量处理
			if (StringUtil.isEmpty(queryCommonCarPojo.getOilCapacity())) {
				queryCommonCarPojo.setOilCapacity(CloudConstants.DEFAULT_OILCAPACITY);
			}
		} else {
			return list;
		}
		List<BaseData> faulist = new ArrayList<BaseData>();
		if (!(StringUtil.isEmpty(engineType) || !CheckUtil.isNumericZidai(engineType))) {
			faulist = iBaseDataService.queryList(Integer.valueOf(engineType));
		}
		// 根据车辆id获取车辆信息 ------end
		BasicDataQueryWebService basicDataQueryWebService = cloudDataRmiClientConfiguration
				.getBasicDataQueryWebService();
		/**
		 * 组装“里程能耗数据检索”接口参数
		 */
		// 公共参数
		CommonParameterSerializer commonParameter = new CommonParameterSerializer();
		commonParameter.setAccessTocken(0);
		commonParameter.setMultipage(false);
		// 发起rmi接口调用
		byte[] data = basicDataQueryWebService.getTerminalTrack(commId, DateUtil.strTimeChangeLong(beginDate),
				DateUtil.strTimeChangeLong(endDate), false, -1L, true, Integer.parseInt(command.getLevel()),
				commonParameter,-1);
		if (null != data) {
			// 解析返回结果
			LCTerminalTrackRes.TerminalTrackRes builder = LCTerminalTrackRes.TerminalTrackRes.parseFrom(data);
			List<LCTerminalLocationData.TerminalLocationData> resultList = builder.getDatasList();
			// 循环轨迹接口返回的结果,并进行分值
			if (!ListUtil.isEmpty(resultList)) {
				for (LCTerminalLocationData.TerminalLocationData entry : resultList) {
					LCLocationData.LocationData gpslist = entry.getLocationData();
//					if (gpslist != null && (gpslist.getStatus() & 1) > 0) {
					if (gpslist != null && (Boolean.valueOf(accDataSwitch).booleanValue()?((gpslist.getStatus() & 1) > 0):true)) {
						QueryTracesPojo forPojo = new QueryTracesPojo();
						// gps时间
						forPojo.setGpsdate(DateUtil.timeStr(gpslist.getGpsDate()));
						// 车辆速度
						forPojo.setVelocity(
								String.valueOf(NumberFormatUtil.getDoubleValueData(new Double(gpslist.getSpeed()), 1)));
						// 经纬度
						forPojo.setLat(NumberFormatUtil.getLatitudeOrLongitude(gpslist.getLatitude()));
						forPojo.setLng(NumberFormatUtil.getLatitudeOrLongitude(gpslist.getLongitude()));
						// 剩余油/汽量
						if (null != queryCommonCarPojo.getFuel() && queryCommonCarPojo.getFuel() == 0) {
							forPojo.setOilwear(String.valueOf(CloudUtil.getResGas(gpslist)));
						} else {
							forPojo.setOilwear(NumberFormatUtil.getOilwear(CloudUtil.getResOil(gpslist),
									Double.valueOf(queryCommonCarPojo.getOilCapacity())) + "");
						}
						forPojo.setTotolmileage(String.valueOf(gpslist.getStandardMileage()));
						forPojo.setTotalFuelConsumption(String.valueOf(gpslist.getStandardFuelCon()));
						// 状态
						forPojo.setStatue(CloudUtil.getDoorStatus(gpslist.getStatus())
								+ CloudUtil.getAccStatus(gpslist.getStatus())
								+ CloudUtil.getGpsStatus(gpslist.getStatus()));
						// 报警
						forPojo.setAlarm(CloudUtil.getAlarm(gpslist.getAlarm()));
						// 故障
						if (StringUtil.isEmpty(engineType) || !CheckUtil.isNumericZidai(engineType)) {
							forPojo.setFault("");
						} else {
							String fault = CloudUtil.getFault(gpslist.getBreakdownAddition().getBreakdownList(),
									faulist, engineType);
							forPojo.setFault(fault);
						}
						// 接收时间
						forPojo.setReceiveDate(DateUtil.timeStr(gpslist.getReceiveDate()));
						// 方向
						forPojo.setDirection(CloudUtil.getDirection(gpslist.getDirection()));
						// 终端里程
						forPojo.setMileage(
								String.valueOf(NumberFormatUtil.getDoubleValueData(gpslist.getMileage() * 0.001, 1)));
						// 地址
						forPojo.setAddress(forPojo.getLng() + ";" + forPojo.getLat());
						// 导出明细增加字段
						forPojo.setHeight(NumberFormatUtil.getDoubleValueData(new Double(gpslist.getHeight()), 1));// 高程
						forPojo = getVehicleStatusAdditionForTrack(gpslist, forPojo);
						forPojo = getcarMessageData(gpslist, forPojo);
						list.add(forPojo);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 车辆状态附加信息additionAlarm
	 * 
	 * @param locationData
	 * @param remoteDiagnosis
	 * @return
	 */
	public QueryTracesPojo getVehicleStatusAdditionForTrack(LCLocationData.LocationData locationData,
			QueryTracesPojo remoteDiagnosis) {
		try {
			LCLocationData.VehicleStatusAddition statusAddition = locationData.getStatusAddition();
			if (statusAddition != null) {
				List<LCVehicleStatusData.VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
				if (VehicleStatusDataList != null) {
					for (LCVehicleStatusData.VehicleStatusData data : VehicleStatusDataList) {
						switch (data.getTypes().getNumber()) {
						case LCStatusType.StatusType.rotation_VALUE:// 车辆当前转速
							double rotation_VALUE = new Double(data.getStatusValue()).doubleValue();
							remoteDiagnosis.setRotation(formatNumber(rotation_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.coolingWaterTem_VALUE:// 冷却液液位（百分比）,改为改为
																			// coolingWaterTem
																			// 0x14
																			// 发动机冷却水温度
							double coolingWaterTem_VALUE = new Double(data.getStatusValue()).doubleValue();
							remoteDiagnosis.setCoolingWaterTem(formatNumber(coolingWaterTem_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.fuelConsumptionRate_VALUE:// 发动机燃油消耗率（毫升/小时）
							double fuelConsumptionRate_VALUE = new Double(data.getStatusValue()).doubleValue();
							remoteDiagnosis
									.setFuelConsumptionRate(formatNumber(fuelConsumptionRate_VALUE / 100 / 1000, 2));// (升/小时)
							break;
						case LCStatusType.StatusType.averageFuelConsumption_VALUE:// 平均燃油消耗率（千米/升）
							double averageFuelConsumption_VALUE = new Double(data.getStatusValue()).doubleValue();
							remoteDiagnosis
									.setAverageFuelConsumption(formatNumber(averageFuelConsumption_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.cumulativeRunningTime_VALUE:// 发动机累计运行时间（秒）
							long cumulativeRunningTime_VALUE = new Long(data.getStatusValue());
							double time = formatNumber(cumulativeRunningTime_VALUE / 100l, 2);
							remoteDiagnosis.setCumulativeRunningTime((long) time);
							String timeStr = DateUtils.convert1((long) time);
							remoteDiagnosis.setCumulativeRunningTimeStr(timeStr);
							break;
						case LCStatusType.StatusType.cumulativeTurningNumber_VALUE:// 发动机累计转数（单位:1000rpm）
							double cumulativeTurningNumber_VALUE = data.getStatusValue();
							remoteDiagnosis
									.setCumulativeTurningNumber(formatNumber(cumulativeTurningNumber_VALUE / 100, 2));
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return remoteDiagnosis;
	}

	/**
	 * 车辆状态附加信息additionAlarm
	 * 
	 * @param locationData
	 * @param remoteDiagnosis
	 * @return
	 */
	public QueryTracesPojo getcarMessageData(LCLocationData.LocationData locationData,
			QueryTracesPojo remoteDiagnosis) {
		try {
			remoteDiagnosis.setAccPedalLowIdleSwitch("");
			LCLocationData.VehicleStatusAddition statusAddition = locationData.getStatusAddition();
			if (statusAddition != null) {
				List<LCVehicleStatusData.VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
				if (VehicleStatusDataList != null) {
					for (LCVehicleStatusData.VehicleStatusData data : VehicleStatusDataList) {
						switch (data.getTypes().getNumber()) {
						case LCStatusType.StatusType.engineTorMode_VALUE:// 转矩控制模式
							int engineTorMode_VALUE = new Long(data.getStatusValue() / 100).intValue();
							switch (engineTorMode_VALUE) {
							case 0:
								remoteDiagnosis.setEngineTorMode("低怠速调节器/无请求");
								break;
							case 1:
								remoteDiagnosis.setEngineTorMode("加速踏板");
								break;
							case 2:
								remoteDiagnosis.setEngineTorMode("巡航控制");
								break;
							case 3:
								remoteDiagnosis.setEngineTorMode("PTO调节器");
								break;
							case 4:
								remoteDiagnosis.setEngineTorMode("车速调节器");
								break;
							case 5:
								remoteDiagnosis.setEngineTorMode("ASR控制");
								break;
							case 6:
								remoteDiagnosis.setEngineTorMode("变速器控制");
								break;
							case 7:
								remoteDiagnosis.setEngineTorMode("ABS控制");
								break;
							case 8:
								remoteDiagnosis.setEngineTorMode("转矩限定");
								break;
							case 9:
								remoteDiagnosis.setEngineTorMode("高速调节器");
								break;
							case 10:
								remoteDiagnosis.setEngineTorMode("制动系统");
								break;
							case 11:
								remoteDiagnosis.setEngineTorMode("遥控加速器");
								break;
							}
							break;
						case LCStatusType.StatusType.driverEnginePercentTor_VALUE:// 驾驶员需求发动机转矩百分比
							int driverEnginePercentTor_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis
									.setDriverEnginePercentTor(formatNumber(driverEnginePercentTor_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.actualEnginePercentTor_VALUE:// 实际发动机转矩百分比
							int actualEnginePercentTor_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis
									.setActualEnginePercentTor(formatNumber(actualEnginePercentTor_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.accPedalLowIdleSwitch_VALUE:// 加速踏板低怠速开关
							int accPedalLowIdleSwitch_VALUE = new Long(data.getStatusValue()).intValue();
							if (accPedalLowIdleSwitch_VALUE == 0) {
								remoteDiagnosis.setAccPedalLowIdleSwitch("不处于低怠速模式");
							} else {
								remoteDiagnosis.setAccPedalLowIdleSwitch("处于低怠速模式");
							}
							break;
						case LCStatusType.StatusType.accPedalKickdownSwitch_VALUE:
							int accPedalKickdownSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (accPedalKickdownSwitch_VALUE == 0) {
								remoteDiagnosis.setAccPedalKickdownSwitch("被动");
							} else if (accPedalKickdownSwitch_VALUE == 1) {
								remoteDiagnosis.setAccPedalKickdownSwitch("主动");
							}
							break;
						case LCStatusType.StatusType.accPedalPos_VALUE:// 加速踏板开度
							int accPedalPos_VALUE = new Long(data.getStatusValue() / 100).intValue();
							remoteDiagnosis.setAccPedalPos(String.valueOf(accPedalPos_VALUE));
							break;
						case LCStatusType.StatusType.percentLoadAtCurrentSpd_VALUE:// 当前速度下，负载百分比
							int percentLoadAtCurrentSpd_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis
									.setPercentLoadAtCurrentSpd(formatNumber(percentLoadAtCurrentSpd_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.nominalFrictionPercentTrq_VALUE:// 名义摩擦力矩百分比
							int nominalFrictionPercentTrq_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis.setNominalFrictionPercentTrq(
									formatNumber(nominalFrictionPercentTrq_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.parkingBrakeSwitch_VALUE:// 驻车制动器开关
							int parkingBrakeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (parkingBrakeSwitch_VALUE == 0) {
								remoteDiagnosis.setParkingBrakeSwitch("未操作");
							} else if (parkingBrakeSwitch_VALUE == 1) {
								remoteDiagnosis.setParkingBrakeSwitch("操作");
							}
							break;
						case LCStatusType.StatusType.wheelBasedVehicleSpd_VALUE:// 车轮车速
							Long wheelBasedVehicleSpd_VALUE = data.getStatusValue();
							remoteDiagnosis.setWheelBasedVehicleSpd(formatNumber(wheelBasedVehicleSpd_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.cruiseCtrlActive_VALUE:
							int cruiseCtrlActive_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlActive_VALUE == 0) {
								remoteDiagnosis.setCruiseCtrlActive("未激活");
							} else {
								remoteDiagnosis.setCruiseCtrlActive("激活");
							}
							break;
						case LCStatusType.StatusType.brakeSwitch_VALUE:
							int brakeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (brakeSwitch_VALUE == 0) {
								remoteDiagnosis.setBrakeSwitch("未操作");
							} else {
								remoteDiagnosis.setBrakeSwitch("操作");
							}
							break;
						case LCStatusType.StatusType.clutchSwitch_VALUE:// 离合器开关
							int clutchSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (clutchSwitch_VALUE == 0) {
								remoteDiagnosis.setClutchSwitch("未分离");
							} else {
								remoteDiagnosis.setClutchSwitch("分离");
							}
							break;
						case LCStatusType.StatusType.cruiseCtrlSetSwitch_VALUE:// 巡航控制设置开关
							int cruiseCtrlSetSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlSetSwitch_VALUE == 0) {
								remoteDiagnosis.setCruiseCtrlSetSwitch("无按钮按下");
							} else {
								remoteDiagnosis.setCruiseCtrlSetSwitch("退出巡航");
							}
							break;
						case LCStatusType.StatusType.cruiseCtrlCoastSwitch_VALUE:
							int cruiseCtrlCoastSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlCoastSwitch_VALUE == 0) {
								remoteDiagnosis.setCruiseCtrlCoastSwitch("未按下");
							} else {
								remoteDiagnosis.setCruiseCtrlCoastSwitch("按下");
							}
							break;
						case LCStatusType.StatusType.cruiseCtrlResumeSwitch_VALUE:// 巡航控制恢复开关
							int cruiseCtrlResumeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlResumeSwitch_VALUE == 0) {
								remoteDiagnosis.setCruiseCtrlResumeSwitch("未按下");
							} else {
								remoteDiagnosis.setCruiseCtrlResumeSwitch("按下");
							}
							break;
						case LCStatusType.StatusType.cruiseCtrlAccSwitch_VALUE:// 巡航控制加速开关
							int cruiseCtrlAccSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlAccSwitch_VALUE == 0) {
								remoteDiagnosis.setCruiseCtrlAccSwitch("未按下");
							} else {
								remoteDiagnosis.setCruiseCtrlAccSwitch("按下");
							}
							break;
						case LCStatusType.StatusType.cruiseCtrlSetSpd_VALUE:// 巡航控制设置速度
							long cruiseCtrlSetSpd_VALUE = new Long(data.getStatusValue() / 100).intValue();
							remoteDiagnosis.setCruiseCtrlSetSpd(formatNumber(cruiseCtrlSetSpd_VALUE, 2));
							break;
						case LCStatusType.StatusType.cruiseCtrlStates_VALUE:// 巡航控制状态
							int cruiseCtrlStates_VALUE = new Long(data.getStatusValue() / 100).intValue();
							switch (cruiseCtrlStates_VALUE) {
							case 0:
								remoteDiagnosis.setCruiseCtrlStates("关闭/禁止");
								break;
							case 1:
								remoteDiagnosis.setCruiseCtrlStates("保持");
								break;
							case 2:
								remoteDiagnosis.setCruiseCtrlStates("加速");
								break;
							case 3:
								remoteDiagnosis.setCruiseCtrlStates("减速/滑行");
								break;
							case 4:
								remoteDiagnosis.setCruiseCtrlStates("恢复");
								break;
							case 5:
								remoteDiagnosis.setCruiseCtrlStates("设置");
								break;
							case 6:
								remoteDiagnosis.setCruiseCtrlStates("加速踏板取代");
								break;
							}
							break;
						case LCStatusType.StatusType.tripDistance_VALUE:// 日里程
							long tripDistance_VALUE = data.getStatusValue();
							remoteDiagnosis.setDailyMileage(String.valueOf(formatNumber(tripDistance_VALUE / 100, 2)));
							break;
						case LCStatusType.StatusType.realTimeOilConsumption_VALUE:// 实时油耗
							int realTimeOilConsumption_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis
									.setRealTimeOilConsumption(formatNumber(realTimeOilConsumption_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.totalFuelConsumption_VALUE:
							int totalFuelConsumption_VALUE = new Long(data.getStatusValue()).intValue();
							remoteDiagnosis.setTotalOil(formatNumber(totalFuelConsumption_VALUE / 100, 2));
							break;
						case LCStatusType.StatusType.outPutRoateSpeed_VALUE:// 输出轴速度
							int outPutRoateSpeed_VALUE = new Long(data.getStatusValue() / 100).intValue();
							remoteDiagnosis.setOutPutRoateSpeed(outPutRoateSpeed_VALUE);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return remoteDiagnosis;
	}

	public double formatNumber(double src, int validBit) {
		double result = 0;
		try {
			BigDecimal b = new BigDecimal(src);
			result = b.setScale(validBit, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public void asyncDonwload(String fservice, ExportTrackVacuateCommand command, String sourcePath,
			List<? extends Object> list) {
		try {
			JSONObject uploadResult = download(fservice, list, command, sourcePath);
			// 调用邮件接口发送邮件信息
			SendEmailCommand mailCommand = new SendEmailCommand();
			mailCommand.setToEmails(command.getEmail());
			mailCommand.setSubject("车辆轨迹抽稀");
			mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
			mailCommand.setWm("1");
			mailClient.sendMail(mailCommand);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public JSONObject download(String fservice, List<? extends Object> list, ExportTrackVacuateCommand command,
			String sourcePath) {
		ReportConfig config = new ReportConfig("车辆轨迹");

		config.setColumn("", "序号", 0);
		config.setColumn("receiveDate", "接收时间", 1);
		config.setColumn("gpsdate", "GPS时间", 2);
		config.setColumn("lat", "经度", 3);
		config.setColumn("lng", "纬度", 4);
		config.setColumn("direction", "方向", 5);
		config.setColumn("velocity", "速度(KM/H)", 6);
		config.setColumn("statue", "车辆状态", 7);
		config.setColumn("totolmileage", "整车里程(KM)", 8);
		config.setColumn("mileage", "终端里程(KM)", 9);
		config.setColumn("oilwear", "剩余油/气量(L)", 10);
		config.setColumn("fault", "故障", 11);
		config.setColumn("alarm", "报警", 12);
		config.setColumn("address", "地址", 13);
		// 导出明细增加字段
		config.setColumn("height", "高程（M）", 14);
		config.setColumn("dailyMileage", "小计里程（KM）", 15);
		config.setColumn("rotation", "发动机转速（r/min）", 16);
		config.setColumn("cumulativeTurningNumber", "发动机累计转数（r）", 17);
		config.setColumn("cumulativeRunningTime", "发动机累计运行时长", 18);
		config.setColumn("coolingWaterTem", "冷却液温度（度）", 19);
		config.setColumn("totalFuelConsumption", "油/汽量总消耗（L）", 20);
		config.setColumn("fuelConsumptionRate", "发动机燃油消耗率（L/h）", 21);
		config.setColumn("averageFuelConsumption", "平均燃油消耗率（KM/h）", 22);
		config.setColumn("realTimeOilConsumption", "瞬时油耗率（KM/L）", 23);
		config.setColumn("engineTorMode", "转矩控制模式", 24);
		config.setColumn("driverEnginePercentTor", "驾驶指令百分比扭矩（%）", 25);
		config.setColumn("actualEnginePercentTor", "发动机实际扭矩百分比（%）", 26);
		config.setColumn("accPedalLowIdleSwitch", "加速踏板低怠速开关", 27);
		config.setColumn("accPedalKickdownSwitch", "加速踏板Kickdown开关", 28);
		config.setColumn("accPedalPos", "加速踏板开度（%）", 29);
		config.setColumn("percentLoadAtCurrentSpd", "当前速度负载百分比（%）", 30);
		config.setColumn("nominalFrictionPercentTrq", "名义摩擦力矩百分比（%）", 31);
		config.setColumn("parkingBrakeSwitch", "驻车制动器开关", 32);
		config.setColumn("wheelBasedVehicleSpd", "车轮车速（KM/h）", 33);
		config.setColumn("cruiseCtrlActive", "巡航控制开关状态", 34);
		config.setColumn("brakeSwitch", "制动开关", 35);
		config.setColumn("clutchSwitch", "离合器开关", 36);
		config.setColumn("cruiseCtrlSetSwitch", "巡航控制设置开关", 37);
		config.setColumn("cruiseCtrlCoastSwitch", "巡航控制减速开关", 38);
		config.setColumn("cruiseCtrlResumeSwitch", "巡航控制恢复开关", 39);
		config.setColumn("cruiseCtrlAccSwitch", "巡航控制加速开关", 40);
		config.setColumn("cruiseCtrlSetSpd", "巡航控制设置速度（KM/h）", 41);
		config.setColumn("cruiseCtrlStates", "巡航控制状态", 42);
		config.setColumn("outPutRoateSpeed", "转出轴转速（r）", 43);

		config.setSourcePath(sourcePath);
		File file = ExcelUtil.getExcel(config, list, command.getSheetName(), 0);

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		param.add("account", command.getUserInfor().getUserId());
		param.add("file", new FileSystemResource(file));
		RestTemplate restTemplate = new RestTemplate();
		String httpResult = restTemplate.postForObject(fservice, param, String.class);
		return JSONObject.fromObject(httpResult);
	}
}
