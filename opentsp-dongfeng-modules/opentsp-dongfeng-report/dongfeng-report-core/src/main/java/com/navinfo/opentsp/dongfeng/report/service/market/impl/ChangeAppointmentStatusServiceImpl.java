package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.report.commands.market.ChangeAppointmentStatusCommand;
import com.navinfo.opentsp.dongfeng.report.pojo.car.HyAppointmentPojo;
import com.navinfo.opentsp.dongfeng.report.service.market.IChangeAppointmentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = { Exception.class })
public class ChangeAppointmentStatusServiceImpl extends BaseService implements IChangeAppointmentStatusService {

	protected static final Logger logger = LoggerFactory.getLogger(ChangeAppointmentStatusServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public CommonResult changeStatus(ChangeAppointmentStatusCommand command) {
		CommonResult result = new CommonResult();
		Integer appStatus = command.getAppointmentStatus();
		if (appStatus == null || appStatus <= 0) {
			result.fillResult(ReturnCode.CHANGE_STATUS_WRONG);
			return result;
		}
		// 根据条件获取对应预约信息，验证预约信息是否存在
		HyAppointmentPojo appList = (HyAppointmentPojo) dao.sqlFindObject("queryChangeStatus", command,
				HyAppointmentPojo.class);
		return updateStatus(command, appList);
	}

	/**
	 * 更改预约状态
	 * 
	 * @param command
	 * @param appList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CommonResult updateStatus(ChangeAppointmentStatusCommand command, HyAppointmentPojo appList) {
		CommonResult result = new CommonResult();
		if (appList == null) {
			result.fillResult(ReturnCode.CHANGE_INFO_NULL);
			logger.error("更新预约状态时预约记录不存在");
			return result;
		}
		if (!checkStatus(command.getAppointmentStatus(), appList)) {
			result.fillResult(ReturnCode.CHANGE_STATUS_ERROR);
			logger.error("状态转换时新状态不正确");
			return result;
		}
		try {
			dao.executeUpdate("updateChangeStatusMapping", command);
			// 预留//消息推送
			// sendApp(appStatus, map, appList);
		} catch (Exception e) {
			logger.error("更新预约记录异常");
			throw e;
		}
		return result;
	}

	/**
	 * APP 推送
	 * 
	 * @param appStatus
	 * @param sqlMap
	 */
//	@SuppressWarnings("unchecked")
//	private void sendApp(Integer appStatus, HashMap<String, Object> sqlMap, HyAppointmentPojo appList) {
//		HyServiceStation station = new HyServiceStation();
//		if (appList != null) {
//			List<HyServiceStationAppointment> stations = new SerivceStationManagerServiceImpl()
//					.queryAppointmentByCar(appList.getCarId(), appList.getStationId());
//			if (stations != null && stations.size() > 0) {
//				XingeApp push = XinAppBean.instance();
//				XingeApp pushIOS = XinAppBean.instanceOfIOS();
//				Message mess = new Message();
//				MessageIOS messios = new MessageIOS();
//				String aMessage = "您已完成本次服务，请对我们的服务评价";
//				switch (appStatus.intValue()) {
//				case 1:
//					aMessage = "您的预约已确认，欢迎到访并尊享五星服务";
//					break;
//				case 2:
//					aMessage = "您的预约已取消，继续服务请重新预约";
//					break;
//				case 3:
//					aMessage = "您的预约正在服务中";
//					break;
//				case 4:
//					aMessage = "您已完成本次服务，请对我们的服务评价";
//					break;
//				case 5:
//					aMessage = "您的预约已完成评价";
//					break;
//				case 6:
//					aMessage = "您的预约已过期，继续服务重新预约";
//					break;
//				default:
//					break;
//				}
//				// 完善 Message 消息
//				messios.setAlert(aMessage);// 调整
//				mess.setTitle("appointment");
//				mess.setType(2);
//				Map<String, Object> map2 = new HashMap<String, Object>();
//				map2.put("stationId", appList.getStationId());
//				map2.put("appointmentName", stations.get(0).getAppointmentName());
//				map2.put("appointmentDate", stations.get(0).getAppointmentTime());
//				map2.put("appointmentType", stations.get(0).getAppointmentType());
//				map2.put("appointmentId", stations.get(0).getAppointmentId());
//				map2.put("content", stations.get(0).getServiceContent());
//				map2.put("detail", stations.get(0).getPartsContent());
//				map2.put("common", stations.get(0).getAppointmentComment());
//				map2.put("stationName", stations.get(0).getStationName());
//				map2.put("serviceType", stations.get(0).getServiceType());
//				map2.put("phone", stations.get(0).getPhone());
//				map2.put("appointmentStatus", appStatus.intValue());
//				map2.put("serviceContent", transSPTContent(41, "服务", appList.getServiceContent()));
//				map2.put("partsContent", transAppoSPTContent(42, "配件", appList.getPartsContent()));
//				map2.put("toolContent", transAppoSPTContent(59, "提升工具", appList.getToolContent()));
//
//				mess.setCustom(map2);
//				messios.setCustom(map2);
//				// 车辆推送测试调整
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("carId", appList.getCarId());
//				HyCarAllPojo car = (HyCarAllPojo) dao.sqlFindObject("queryAllCar", map, HyCarAllPojo.class);
//				if (car != null) {
//					logger.error("预约状态发送到车辆" + car.getChassisNum() + ",状态：" + appStatus);
//					logger.error(push.pushSingleAccount(0, car.getChassisNum(), mess) + "预约状态发送到车辆"
//							+ car.getChassisNum() + ",状态：" + appStatus);
//
//					logger.error(pushIOS.pushSingleAccount(0, car.getChassisNum(), messios, 1) + "IOS预约状态发送到车辆"
//							+ car.getChassisNum() + ",状态：" + appStatus);
//				} else {
//					logger.error("预约状态发送到车辆：" + appList.getCarId() + ",车辆不存在" + ",状态：" + appStatus);
//				}
//			}
//		}
//	}

	/**
	 * 获取预约相关明细
	 * 
	 * @param id
	 * @param name
	 * @param content
	 * @return
	 * @throws RemoteException
	 */
//	@SuppressWarnings("unchecked")
//	private String transAppoSPTContent(int id, String name, String content) throws RemoteException {
//		Map<String, Object> sqlMap1 = new HashMap<String, Object>();
//		sqlMap1.put("dataType", id);
//		List<HyBasicdataPojo> basiccontent = dao.sqlFind("queryHyBasicMapping", sqlMap1, HyBasicdataPojo.class);
//		StringBuffer bf = new StringBuffer();
//		String[] check = (content == null || content.equals("")) ? null : content.split(",");
//		if (check != null && basiccontent != null && check.length > 0 && basiccontent.size() > 0) {
//			for (HyBasicdataPojo key : basiccontent) {
//				for (int i = 0; i < check.length;) {
//					if (check[i].equals(key.getDataCode())) {
//						bf.append(key.getDataValue() + "" + check[i + 1] + "个;");
//					}
//					i += 2;
//				}
//			}
//		}
//		return bf.toString();
//	}

	/**
	 * 获取预约相关明细
	 * 
	 * @param id
	 * @param name
	 * @param content
	 * @return
	 * @throws RemoteException
	 */
//	@SuppressWarnings("unchecked")
//	private String transSPTContent(int id, String name, String content) throws RemoteException {
//		Map<String, Object> sqlMap1 = new HashMap<String, Object>();
//		sqlMap1.put("dataType", id);
//		List<HyBasicdataPojo> basiccontent = dao.sqlFind("queryHyBasicMapping", sqlMap1, HyBasicdataPojo.class);
//		StringBuffer bf = new StringBuffer();
//		String[] check = (content == null || content.equals("")) ? null : content.split(",");
//		if (check != null && basiccontent != null && check.length > 0 && basiccontent.size() > 0) {
//			for (HyBasicdataPojo key : basiccontent) {
//				for (int i = 0; i < check.length;) {
//					if (check[i].equals(key.getDataCode())) {
//						bf.append(key.getDataValue() + ";");
//					}
//					i += 1;
//				}
//			}
//		}
//		return bf.toString();
//	}

	/**
	 * 检查状态
	 * 
	 * @param appStatus
	 * @param appList
	 * @return
	 */
	private boolean checkStatus(Integer appStatus, HyAppointmentPojo appList) {
		return (appStatus.intValue() == 1
				&& (appList.getAppointmentStatus() == null || appList.getAppointmentStatus().intValue() == 0)) // 已确认未服务
				|| (appStatus.intValue() == 2
						&& (appList.getAppointmentStatus() == null || appList.getAppointmentStatus().intValue() == 0))// 已取消
				|| (appStatus.intValue() == 3
						&& (appList.getAppointmentStatus() != null && appList.getAppointmentStatus().intValue() == 1))// 正在服务
				|| (appStatus.intValue() == 4
						&& (appList.getAppointmentStatus() != null && appList.getAppointmentStatus().intValue() == 3))// 完成服务待评价
				|| (appStatus.intValue() == 5
						&& (appList.getAppointmentStatus() != null && appList.getAppointmentStatus().intValue() == 4))// 完成评价
				|| (appStatus.intValue() == 6
						&& (appList.getAppointmentStatus() == null || appList.getAppointmentStatus().intValue() <= 2))// 已过期
		;
	}

}
