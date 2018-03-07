package com.navinfo.opentsp.dongfeng.openapi.core.converter.param;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarParamPoJo;
import com.navinfo.opentsp.dongfeng.openapi.dto.param.CarParamDto;

import java.util.ArrayList;
import java.util.List;

public class CarParamToDto {
	public static List<CarParamDto> convert(List<CarParamPoJo> datas) {
		List<CarParamDto> dtos = new ArrayList<CarParamDto>();
		for (CarParamPoJo pojo : datas) {
			CarParamDto dto = new CarParamDto();
			dto.setId(pojo.getId() != null ? pojo.getId().longValue() : null);
			dto.setLcxm(pojo.getLcxm());
			dto.setCxm(pojo.getCxm());
			dto.setSjxh(pojo.getSjxh());
			dto.setGg(pojo.getGg());
			dto.setCx(pojo.getCx());
			dto.setJss(pojo.getJss());
			dto.setFdj(pojo.getFdj());
			dto.setBsx(pojo.getBsx());
			dto.setLhq(pojo.getLhq());
			dto.setBjsj(DateUtil.formatTime(pojo.getBjsj()));
			dto.setQz(pojo.getQz());
			dto.setHq(pojo.getHq());
			dto.setCj(pojo.getCj());
			dto.setCxg(pojo.getCxg());
			dto.setColorCode(pojo.getColorCode());
			dto.setJgl(pojo.getJgl());
			dto.setEdgl(pojo.getEdgl());
			dto.setColor(pojo.getColor());
			dto.setZj(pojo.getZj());
			dto.setFa(pojo.getFa());
			dto.setLt(pojo.getLt());
			dto.setQt(pojo.getQt());
			
			dtos.add(dto);
		}
		return dtos;
	}
}
