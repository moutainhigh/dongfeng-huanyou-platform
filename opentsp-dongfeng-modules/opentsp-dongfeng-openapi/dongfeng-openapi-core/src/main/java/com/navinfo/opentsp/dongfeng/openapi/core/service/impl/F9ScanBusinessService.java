package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.lc.core.protocol.common.LCLastestTerminalStatus.LastestTerminalStatus;
import com.lc.core.protocol.common.LCLocationData.LocationData;
import com.lc.core.protocol.common.LCLocationData.VehicleStatusAddition;
import com.lc.core.protocol.common.LCStatusType.StatusType;
import com.lc.core.protocol.common.LCVehicleBreakdown.VehicleBreakdown;
import com.lc.core.protocol.common.LCVehicleStatusData.VehicleStatusData;
import com.lc.core.protocol.dataaccess.terminal.LCLastestLocationDataRes.LastestLocationDataRes;
import com.lc.core.protocol.webservice.newstatisticsdata.LCLastestTerminalStatusRes.LastestTerminalStatusRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData.TerminalLocationData;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.pojo.HyBasicdataPojo;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ReportInfoCommand;
import com.navinfo.opentsp.dongfeng.openapi.commands.scan.F9ScanResultCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.converter.scan.F4ScanResultToDto;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.F9ReportInfoPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.F9ScanResultPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.F9SimCodePojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IF9ScanBusinessService;
import com.navinfo.opentsp.dongfeng.openapi.core.util.OpenApiLoginUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class F9ScanBusinessService extends BaseService implements IF9ScanBusinessService {

	@Resource
	private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

	@Override
	public HttpCommandResultWithData scanResult(F9ScanResultCommand command) throws Exception {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		String chassic = command.getChassic();
		if (chassic == null || chassic.isEmpty()) {
			result.setResultCode(507);
			result.setMessage("底盘号不能为空");
			return result;
		}
		if (chassic.trim().length() > 8 || chassic.trim().length() < 8) {
			result.setResultCode(507);
			result.setMessage("底盘号长度必须是八位数的字符串");
			return result;
		}
		return processScanResult(command);
	}

	@SuppressWarnings("unchecked")
	private HttpCommandResultWithData processScanResult(F9ScanResultCommand command) throws Exception {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();

		F9ScanResultPojo resultPojo = (F9ScanResultPojo) dao.sqlFindObject("queryScanInfo", command,
				F9ScanResultPojo.class);
		if (resultPojo == null || resultPojo.getCommId() == null) {
			result.setResultCode(520);
			result.setMessage("系统中未发现您输入的VIN号对应的车辆");
			return result;
		}
		Long sim = resultPojo.getCommId().longValue();
		List<Long> terminalIds = new ArrayList<Long>();
		terminalIds.add(sim);
		BasicDataQueryWebService service = (BasicDataQueryWebService) cloudDataRmiClientConfiguration
				.getBasicDataQueryWebService();
		byte[] data = service.getLastestTerminalStatus(terminalIds, 0);
		Map<Long, Boolean> terMap = new HashMap<Long, Boolean>();
		if (data != null) {
			LastestTerminalStatusRes builder = LastestTerminalStatusRes.parseFrom(data);
			List<LastestTerminalStatus> list = builder.getStatusList();
			if (list != null && list.size() > 0) {
				for (LastestTerminalStatus lastestTerminalStatus : list) {
					long terminalId = lastestTerminalStatus.getTerminalId();// 终端通讯号
					boolean flag = lastestTerminalStatus.getStatus(); // 上下线状态
					terMap.put(terminalId, flag);
				}
			}
		}

		LocationData locationData2 = getLastGpsByCloud(sim + "", 2);// 所有
		if (terMap.get(terminalIds) != null) {
			resultPojo.setTer("检测正常");
			resultPojo.setTerTime("末次连接平台的时间：" + DateUtil.timeStr(locationData2.getGpsDate()));
		} else {
			if (locationData2 == null) {
				resultPojo.setTer("未检测到数据");
			} else {
				resultPojo.setTer("检测正常");
				resultPojo.setTerTime("末次连接平台的时间：" + DateUtil.timeStr(locationData2.getGpsDate()));
			}
		}
		LocationData locationData0 = getLastGpsByCloud(sim + "", 0);// 有效位置
		LocationData locationData1 = getLastGpsByCloud(sim + "", 1);// 有效can

		if (locationData2 == null) {
			resultPojo.setGps("未检测到数据");
			resultPojo.setAcc("未检测到数据");
			resultPojo.setCan("未检测到数据");
		} else {
			if (locationData0 == null) {
				resultPojo.setGps("尚未获得有效位置数据");
			} else {
				resultPojo.setGps("检测正常");
				resultPojo.setGpsTime("末次有效位置的时间：" + DateUtil.timeStr(locationData0.getGpsDate()));
				resultPojo.setGpsLongitude(locationData0.getLongitude() * 0.000001);
				resultPojo.setGpsLatitude(locationData0.getLatitude() * 0.000001);
			}
		}
		if (locationData2 != null) {
			if (locationData1 == null) {
				resultPojo.setCan("尚未获得CAN数据");
				resultPojo.setAcc("尚未获得ACC数据");
			} else {
				resultPojo.setCan("检测正常");
				resultPojo.setAcc("检测正常");
				resultPojo.setCanTime("末次有效CAN信号的时间：" + DateUtil.timeStr(locationData1.getGpsDate()));
				resultPojo.setAccTime("末次ACC为ON的时间：" + DateUtil.timeStr(locationData1.getGpsDate()));
			}
		}
		result.setResultCode(200);
		result.setMessage("success");
		result.setData(F4ScanResultToDto.convert(resultPojo));
		return result;
	}

	/**
	 * 根据不同类型获取位置云最新gps
	 * 
	 * @param sim
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public LocationData getLastGpsByCloud(String sim, int type) throws Exception {
		LocationData gpsdata = null;
		if (sim != null && !sim.equals("0") && !sim.equals("null")) {
			List<Long> terminalIds = new ArrayList<Long>();
			terminalIds.add(Long.parseLong(sim));
			BasicDataQueryWebService service = (BasicDataQueryWebService) cloudDataRmiClientConfiguration
					.getBasicDataQueryWebService();
			byte[] data = service.getLastestLocationData(terminalIds, type);
			if (data != null) {
				LastestLocationDataRes gps = LastestLocationDataRes.parseFrom(data);
				List<TerminalLocationData> lastgps = gps.getDatasList();
				if (lastgps.size() != 0) {
					gpsdata = lastgps.get(0).getLocationData();
				}
			} else {
				logger.info("调用位置云轨迹接口返回为空，参数为：" + sim);
			}
		}
		return gpsdata;
	}

	@Override
	public HttpCommandResultWithData reportInfo(F9ReportInfoCommand command) throws Exception {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();
		String chassic = command.getChassic();
		if (chassic == null || chassic.isEmpty()) {
			result.setResultCode(507);
			result.setMessage("底盘号不能为空");
			return result;
		}
		if (chassic.trim().length() > 8 || chassic.trim().length() < 8) {
			result.setResultCode(507);
			result.setMessage("底盘号长度必须是八位数的字符串");
			return result;
		}
		return processScanInfo(command);
	}

	@SuppressWarnings("unchecked")
	private HttpCommandResultWithData processScanInfo(F9ReportInfoCommand command) throws Exception {
		HttpCommandResultWithData result = OpenApiLoginUtil.getInstance().resultData();

		F9SimCodePojo pojo = (F9SimCodePojo) dao.sqlFindObject("queryScanInfo", command, F9SimCodePojo.class);
		if (pojo == null || pojo.getCommId() == null) {
			result.setResultCode(520);
			result.setMessage("系统中未发现您输入的VIN号对应的车辆");
			return result;
		}

		Long sim = pojo.getCommId().longValue();
		List<Long> terminalIds = new ArrayList<Long>();
		terminalIds.add(sim);
		LocationData gpsdata = getLastGpsByCloud(sim + "", 0);
		LocationData gpscandata = getLastGpsByCloud(sim + "", 1);
		if (gpsdata == null && gpscandata == null) {
			return result;
		}

		F9ReportInfoPojo lastGpsDate = new F9ReportInfoPojo();
		if (gpsdata != null) {// 终端状态
			lastGpsDate.setLatitude(gpsdata.getLatitude() * 0.000001);
			lastGpsDate.setLongitude(gpsdata.getLongitude() * 0.000001);
			lastGpsDate.setReceiveDate(DateUtil.timeStr(gpsdata.getReceiveDate()));
			lastGpsDate.setGpsDate(DateUtil.timeStr(gpsdata.getGpsDate()));
			lastGpsDate.setDirection(this.getDirection(gpsdata.getDirection()));
			lastGpsDate.setHeight(gpsdata.getHeight() + "");
			lastGpsDate.setMileage(OpenApiLoginUtil.getInstance().formatNumber(gpsdata.getMileage() / 1000, 1) + "");
			lastGpsDate.setSpeed(gpsdata.getSpeed());

			List<VehicleBreakdown> fault = gpsdata.getBreakdownAddition().getBreakdownList();
			if (pojo.getEngineType() == null || fault == null || fault.isEmpty()) {
				lastGpsDate.setVehicleBreakdown("无");
			}
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < fault.size(); i++) {
				VehicleBreakdown t = fault.get(i);
				list.add(t.getBreakdownSPNValue() + "_" + t.getBreakdownFMIValue());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataType", pojo.getEngineType());
			List<HyBasicdataPojo> basicPojo = dao.sqlFind("queryHyBasicByDataType", map, HyBasicdataPojo.class);
			String st1 = "";
			for (String entry : list) {
				String str = "";
				for (HyBasicdataPojo en : basicPojo) {
					if (entry.equals(en.getDataCode())) {
						str += en.getDataValue() + ";";
					}
				}
				st1 += str.equals("") ? "未知;" : str;
			}
			if (st1.equals("")) {
				st1 = "无";
			}
			lastGpsDate.setVehicleBreakdown(st1);
		}
		if (gpscandata != null) {// 车辆状态
			String fireStatus = (gpsdata.getStatus() & 1) > 0 ? "ACC开" : "ACC关";
			lastGpsDate.setAccStatus(fireStatus);

			VehicleStatusAddition statusAddition = gpsdata.getStatusAddition();
			if (statusAddition != null) {
				List<VehicleStatusData> VehicleStatusDataList = statusAddition.getStatusList();
				if (VehicleStatusDataList != null) {
					for (VehicleStatusData data : VehicleStatusDataList) {
						switch (data.getTypes().getNumber()) {
						case StatusType.mileage_VALUE:// 整车里程
							long mileage_VALUE = data.getStatusValue();
							lastGpsDate.setCanmileage(
									OpenApiLoginUtil.getInstance().formatNumber(mileage_VALUE / 100, 1) + "");// 里程
							break;
						case StatusType.totalFuelConsumption_VALUE:// 总油耗
							long totalFuelConsumption_VALUE = data.getStatusValue();
							lastGpsDate.setTotalFuelConsumption(
									OpenApiLoginUtil.getInstance().formatNumber(totalFuelConsumption_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.oilValue_VALUE:// 车辆当前油量（百分比）
							double oilPercent = OpenApiLoginUtil.getInstance().formatNumber(data.getStatusValue() / 100,
									1);
							lastGpsDate.setOilValue(oilPercent + "");
							break;
						case StatusType.tripDistance_VALUE:// 日里程
							long tripDistance_VALUE = data.getStatusValue();
							lastGpsDate.setTripDistance(String
									.valueOf(OpenApiLoginUtil.getInstance().formatNumber(tripDistance_VALUE / 100, 1)));
							break;
						case StatusType.rotation_VALUE:// 发动机转速
							long rotation_VALUE = data.getStatusValue();
							lastGpsDate.setRotation(String
									.valueOf(OpenApiLoginUtil.getInstance().formatNumber(rotation_VALUE / 100, 1)));
							break;
						case StatusType.cumulativeTurningNumber_VALUE:// 发动机累计转速
							long cumulativeTurningNumber_VALUE = data.getStatusValue();
							lastGpsDate.setCumulativeTurningNumber(String.valueOf(OpenApiLoginUtil.getInstance()
									.formatNumber(cumulativeTurningNumber_VALUE / 100, 1)));
							break;
						case StatusType.cumulativeRunningTime_VALUE:// 发动机累计运行时间（秒）
							long cumulativeRunningTime_VALUE = new Long(data.getStatusValue());
							Double time = cumulativeRunningTime_VALUE * 0.01;
							String timeStr = DateUtils.convert1(time.longValue());
							lastGpsDate.setCumulativeRunningTime(timeStr);
							break;
						case StatusType.coolingWaterTem_VALUE:// 冷却液液位（百分比）,改为改为
																// coolingWaterTem
																// 0x14 发动机冷却水温度
							long coolingWaterTem_VALUE = data.getStatusValue();
							lastGpsDate.setCoolingWaterTem(String.valueOf(
									OpenApiLoginUtil.getInstance().formatNumber(coolingWaterTem_VALUE / 100, 1)));
							break;
						case StatusType.fuelConsumptionRate_VALUE:// 发动机燃油消耗率（毫升/小时）
							long fuelConsumptionRate_VALUE = data.getStatusValue();
							lastGpsDate.setFuelConsumptionRate(OpenApiLoginUtil.getInstance()
									.formatNumber(fuelConsumptionRate_VALUE / 100 / 1000, 1) + "");
							break;
						case StatusType.averageFuelConsumption_VALUE:// 平均燃油消耗率（千米/升）
							long averageFuelConsumption_VALUE = data.getStatusValue();
							lastGpsDate.setAverageFuelConsumption(
									OpenApiLoginUtil.getInstance().formatNumber(averageFuelConsumption_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.realTimeOilConsumption_VALUE:// 瞬时油耗率（千米/升）实时油耗
							long realTimeOilConsumption_VALUE = data.getStatusValue();
							lastGpsDate.setEngineInstantaneousFuelRate(
									OpenApiLoginUtil.getInstance().formatNumber(realTimeOilConsumption_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.engineTorMode_VALUE:// 转矩控制模式
							int engineTorMode_VALUE = new Long(data.getStatusValue() / 100).intValue();
							switch (engineTorMode_VALUE) {
							case 0:
								lastGpsDate.setEngineTorMode("低怠速调节器/无请求");
								break;
							case 1:
								lastGpsDate.setEngineTorMode("加速踏板");
								break;
							case 2:
								lastGpsDate.setEngineTorMode("巡航控制");
								break;
							case 3:
								lastGpsDate.setEngineTorMode("PTO调节器");
								break;
							case 4:
								lastGpsDate.setEngineTorMode("车速调节器");
								break;
							case 5:
								lastGpsDate.setEngineTorMode("ASR控制");
								break;
							case 6:
								lastGpsDate.setEngineTorMode("变速器控制");
								break;
							case 7:
								lastGpsDate.setEngineTorMode("ABS控制");
								break;
							case 8:
								lastGpsDate.setEngineTorMode("转矩限定");
								break;
							case 9:
								lastGpsDate.setEngineTorMode("高速调节器");
								break;
							case 10:
								lastGpsDate.setEngineTorMode("制动系统");
								break;
							case 11:
								lastGpsDate.setEngineTorMode("遥控加速器");
								break;
							}
							break;
						case StatusType.driverEnginePercentTor_VALUE:// 驾驶员需求发动机转矩百分比
							long driverEnginePercentTor_VALUE = data.getStatusValue();
							lastGpsDate.setDriverEnginePercentTor(
									OpenApiLoginUtil.getInstance().formatNumber(driverEnginePercentTor_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.actualEnginePercentTor_VALUE:// 实际发动机转矩百分比
							long actualEnginePercentTor_VALUE = data.getStatusValue();
							lastGpsDate.setActualEnginePercentTor(
									OpenApiLoginUtil.getInstance().formatNumber(actualEnginePercentTor_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.accPedalLowIdleSwitch_VALUE:// 加速踏板低怠速开关
							int accPedalLowIdleSwitch_VALUE = new Long(data.getStatusValue()).intValue();
							if (accPedalLowIdleSwitch_VALUE == 0) {
								lastGpsDate.setAccPedalLowIdleSwitch("不处于低怠速模式");
							} else {
								lastGpsDate.setAccPedalLowIdleSwitch("处于低怠速模式");
							}
							break;
						case StatusType.accPedalKickdownSwitch_VALUE:// 加速踏板Kickdown开关
							int accPedalKickdownSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (accPedalKickdownSwitch_VALUE == 0) {
								lastGpsDate.setAccPedalKickdownSwitch("被动");
							} else if (accPedalKickdownSwitch_VALUE == 1) {
								lastGpsDate.setAccPedalKickdownSwitch("主动");
							}
							break;
						case StatusType.accPedalPos_VALUE:// 加速踏板开度
							long accPedalPos_VALUE = data.getStatusValue();
							lastGpsDate.setAccPedalPos(
									OpenApiLoginUtil.getInstance().formatNumber(accPedalPos_VALUE / 100, 1) + "");
							break;
						case StatusType.percentLoadAtCurrentSpd_VALUE:// 当前速度下，负载百分比
							long percentLoadAtCurrentSpd_VALUE = data.getStatusValue();
							lastGpsDate.setPercentLoadAtCurrentSpd(
									OpenApiLoginUtil.getInstance().formatNumber(percentLoadAtCurrentSpd_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.nominalFrictionPercentTrq_VALUE:// 名义摩擦力矩百分比
							long nominalFrictionPercentTrq_VALUE = data.getStatusValue();
							lastGpsDate.setNominalFrictionPercentTrq(OpenApiLoginUtil.getInstance()
									.formatNumber(nominalFrictionPercentTrq_VALUE / 100, 1) + "");
							break;
						case StatusType.parkingBrakeSwitch_VALUE:// 驻车制动器开关
							int parkingBrakeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (parkingBrakeSwitch_VALUE == 0) {
								lastGpsDate.setParkingBrakeSwitch("未操作");
							} else if (parkingBrakeSwitch_VALUE == 1) {
								lastGpsDate.setParkingBrakeSwitch("操作");
							}
							break;
						case StatusType.wheelBasedVehicleSpd_VALUE:// 车轮车速
							Long wheelBasedVehicleSpd_VALUE = data.getStatusValue();
							lastGpsDate.setWheelBasedVehicleSpd(
									OpenApiLoginUtil.getInstance().formatNumber(wheelBasedVehicleSpd_VALUE / 100, 1)
											+ "");
							break;
						case StatusType.cruiseCtrlActive_VALUE:// 巡航控制开关状态
							int cruiseCtrlActive_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlActive_VALUE == 0) {
								lastGpsDate.setCruiseCtrlActive("未激活");
							} else {
								lastGpsDate.setCruiseCtrlActive("激活");
							}
							break;
						case StatusType.brakeSwitch_VALUE:// 制动开关
							int brakeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (brakeSwitch_VALUE == 0) {
								lastGpsDate.setBrakeSwitch("未操作");
							} else {
								lastGpsDate.setBrakeSwitch("操作");
							}
							break;
						case StatusType.clutchSwitch_VALUE:// 离合器开关
							int clutchSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (clutchSwitch_VALUE == 0) {
								lastGpsDate.setClutchSwitch("未分离");
							} else {
								lastGpsDate.setClutchSwitch("分离");
							}
							break;
						case StatusType.cruiseCtrlSetSwitch_VALUE:// 巡航控制设置开关
							int cruiseCtrlSetSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlSetSwitch_VALUE == 0) {
								lastGpsDate.setCruiseCtrlSetSwitch("无按钮按下");
							} else {
								lastGpsDate.setCruiseCtrlSetSwitch("退出巡航");
							}
							break;
						case StatusType.cruiseCtrlCoastSwitch_VALUE:// 巡航控制减速开关
							int cruiseCtrlCoastSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlCoastSwitch_VALUE == 0) {
								lastGpsDate.setCruiseCtrlCoastSwitch("未按下");
							} else {
								lastGpsDate.setCruiseCtrlCoastSwitch("按下");
							}
							break;
						case StatusType.cruiseCtrlResumeSwitch_VALUE:// 巡航控制恢复开关
							int cruiseCtrlResumeSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlResumeSwitch_VALUE == 0) {
								lastGpsDate.setCruiseCtrlResumeSwitch("未按下");
							} else {
								lastGpsDate.setCruiseCtrlResumeSwitch("按下");
							}
							break;
						case StatusType.cruiseCtrlAccSwitch_VALUE:// 巡航控制加速开关
							int cruiseCtrlAccSwitch_VALUE = new Long(data.getStatusValue() / 100).intValue();
							if (cruiseCtrlAccSwitch_VALUE == 0) {
								lastGpsDate.setCruiseCtrlAccSwitch("未按下");
							} else {
								lastGpsDate.setCruiseCtrlAccSwitch("按下");
							}
							break;
						case StatusType.cruiseCtrlSetSpd_VALUE:// 巡航控制设置速度
							long cruiseCtrlSetSpd_VALUE = data.getStatusValue();
							lastGpsDate.setCruiseCtrlSetSpd(
									OpenApiLoginUtil.getInstance().formatNumber(cruiseCtrlSetSpd_VALUE / 100, 1) + "");
							break;
						case StatusType.cruiseCtrlStates_VALUE:// 巡航控制状态
							int cruiseCtrlStates_VALUE = new Long(data.getStatusValue() / 100).intValue();
							switch (cruiseCtrlStates_VALUE) {
							case 0:
								lastGpsDate.setCruiseCtrlStates("关闭/禁止");
								break;
							case 1:
								lastGpsDate.setCruiseCtrlStates("保持");
								break;
							case 2:
								lastGpsDate.setCruiseCtrlStates("加速");
								break;
							case 3:
								lastGpsDate.setCruiseCtrlStates("减速/滑行");
								break;
							case 4:
								lastGpsDate.setCruiseCtrlStates("恢复");
								break;
							case 5:
								lastGpsDate.setCruiseCtrlStates("设置");
								break;
							case 6:
								lastGpsDate.setCruiseCtrlStates("加速踏板取代");
								break;
							}
							break;
						case StatusType.outPutRoateSpeed_VALUE:// 输出轴速度
							long outPutRoateSpeed_VALUE = data.getStatusValue();
							lastGpsDate.setOutPutRoateSpeed(
									OpenApiLoginUtil.getInstance().formatNumber(outPutRoateSpeed_VALUE / 100, 1) + "");
							break;
						default:
							break;
						}
					}
				}
			}
		}
		result.setResultCode(200);
		result.setMessage("success");
		result.setData(lastGpsDate);

		return result;
	}

	/**
	 * 获得方向
	 * 
	 * @param direction
	 * @return
	 */
	private String getDirection(int direction) {
		String st = "";
		if (direction == 0) {
			st = Direction.NORTH.getValue();
		} else if (0 < direction && direction < 90) {
			st = Direction.NORTHEAST.getValue();
		} else if (direction == 90) {
			st = Direction.EAST.getValue();
		} else if (90 < direction && direction < 180) {
			st = Direction.SOUTHEAST.getValue();
		} else if (direction == 180) {
			st = Direction.SOUTH.getValue();
		} else if (180 < direction && direction < 270) {
			st = Direction.SOUTHWEST.getValue();
		} else if (direction == 270) {
			st = Direction.WEST.getValue();
		} else if (270 < direction && direction < 359) {
			st = Direction.NORTHWEST.getValue();
		} else {
			st = String.valueOf(direction);
		}
		return st;
	}

}

enum Direction {

	NORTH("北"), SOUTH("南"), EAST("东"), WEST("西"), NORTHEAST("东北"), NORTHWEST("西北"), SOUTHEAST("东南"), SOUTHWEST("西南");
	private String value;

	public String getValue() {
		return value;
	}

	Direction(String value) {
		this.value = value;
	}
}
