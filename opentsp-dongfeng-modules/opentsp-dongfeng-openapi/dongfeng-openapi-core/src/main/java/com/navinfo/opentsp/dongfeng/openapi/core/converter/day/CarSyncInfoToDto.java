package com.navinfo.opentsp.dongfeng.openapi.core.converter.day;

import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarSyncInfoPoJo;
import com.navinfo.opentsp.dongfeng.openapi.dto.day.CarSyncInfoDto;

import java.util.ArrayList;
import java.util.List;

public class CarSyncInfoToDto {
	public static List<CarSyncInfoDto> convert(List<CarSyncInfoPoJo> datas) {
		List<CarSyncInfoDto> dtos = new ArrayList<CarSyncInfoDto>();
		for (CarSyncInfoPoJo pojo : datas) {
			CarSyncInfoDto dto = new CarSyncInfoDto();
			dto.setCarId(pojo.getCarId() != null ? pojo.getCarId().longValue() : null);
			dto.setStdSalDate(pojo.getStdSalDate());
			dto.setAakSalDate(pojo.getAakSalDate());
			dto.setSyncDate(pojo.getSyncDate());
			dto.setCarTypeValue(pojo.getCarTypeValue());
			dto.setBusinessName(pojo.getBusinessName());
			dto.setDelflag(pojo.getDelflag());
			dto.setSim(pojo.getSim());
			
			dtos.add(dto);
		}
		return dtos;
	}
}
