package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.gps;

import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Gps_3528_InOrOutAreaNotify extends Command {
	Logger logger = LoggerFactory.getLogger(Gps_3528_InOrOutAreaNotify.class);

	@Override
	public Object processor(Packet packet) {
		try {
//			String sim = packet.getUniqueMark().substring(1);
//			InOrOutAreaNotify inOrOutAreaNotify = InOrOutAreaNotify.parseFrom(packet.getContentForBytes());
//			long areaIdentify = inOrOutAreaNotify.getAreaIdentify();// areaIdentify 区域标识
//			int status = inOrOutAreaNotify.getStatus();// status 1：进；2：出
//			long gpsDate = inOrOutAreaNotify.getGpsDate();// gpsDate 当前gps时间
//			int lat = inOrOutAreaNotify.getLat();// lat 纬度
//			int lon = inOrOutAreaNotify.getLon();// lon 经度
//
//			logger.error("sim: " + sim + " 3528：区域标识：" + areaIdentify + " 进出状态：" + (status == 1 ? "进" : "出") + " 当前时间："
//					+ gpsDate + " 纬度：" + lat + " 经度：" + lon);
//			SecondStationForApp secondStationForApp = new SecondStationForApp();
//			secondStationForApp.setSim(Long.valueOf(sim));
//			secondStationForApp.setStatus(status);
//			if (sim != null && areaIdentify / 10000000 == 95) {
//				secondStationForApp.setAeraId(areaIdentify % 10000000);
//				secondStationForApp.setAeraType(12);// 服务站二级网点
//			}
//			if (sim != null && areaIdentify < 100000000) {
//				secondStationForApp.setAeraId(areaIdentify);
//				secondStationForApp.setAeraType(11);// 服务站一级网点
//			}
//			// 数据入库
//			SecondForAppService secondForAppService = (SecondForAppService) SpringContextUtil
//					.getBean("secondForAppServiceImpl");
//			if (status == 1) {// 进站
//				secondStationForApp.setEnterTime(gpsDate > 0 ? gpsDate : System.currentTimeMillis() / 1000);
//				secondForAppService.insertSecondInfoWithCar(secondStationForApp);
//
//			} else if (status == 2) {// 出站
//				// 出区域更新时间
//				secondStationForApp.setOutTime(gpsDate > 0 ? gpsDate : System.currentTimeMillis() / 1000);
//				secondForAppService.insertSecondInfoWithCar(secondStationForApp);
//			}
		} catch (Exception e) {
            logger.error(e.getMessage(),e);
		}
		return null;
	}

}
