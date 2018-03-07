package com.navinfo.opentsp.dongfeng.openapi.core.converter.scan;

import com.navinfo.opentsp.dongfeng.openapi.core.pojo.F9ScanResultPojo;
import com.navinfo.opentsp.dongfeng.openapi.dto.scan.F9ScanResultDto;

public class F4ScanResultToDto {

	public static F9ScanResultDto convert(F9ScanResultPojo pojo) {
		F9ScanResultDto dto = new F9ScanResultDto();
		dto.setTersim(pojo.getTerSim()); // 终端sim卡
		dto.setTercode(pojo.getTerCode()); // 终端ID
		dto.setTertype(pojo.getTerType()); // 终端类型
		dto.setTer(pojo.getTer()); // 终端通信检测
		dto.setTertime(pojo.getTerTime()); // 末次连接平台的时间
		dto.setGps(pojo.getGps()); // GPS卫星天线检测
		dto.setGpslongitude(pojo.getGpsLongitude()); // 末次有效经度
		dto.setGpslatitude(pojo.getGpsLatitude()); // 末次有效纬度
		dto.setGpstime(pojo.getGpsTime()); // 末次有效位置的时间
		dto.setAcc(pojo.getAcc()); // 车辆ACC线检测
		dto.setAcctime(pojo.getAccTime()); // 末次ACC为ON的时间
		dto.setCan(pojo.getCan()); // CAN信号检测
		dto.setCantime(pojo.getCanTime()); // 末次有效CAN信号的时间
		return dto;
	}
}
