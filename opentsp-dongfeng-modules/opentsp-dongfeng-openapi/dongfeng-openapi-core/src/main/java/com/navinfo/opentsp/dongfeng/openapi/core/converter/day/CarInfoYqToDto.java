package com.navinfo.opentsp.dongfeng.openapi.core.converter.day;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.CarInfoYqPoJo;
import com.navinfo.opentsp.dongfeng.openapi.dto.day.CarInfoYqDto;

import java.util.ArrayList;
import java.util.List;

public class CarInfoYqToDto {
	public static List<CarInfoYqDto> convert(List<CarInfoYqPoJo> datas) {
		List<CarInfoYqDto> dtos = new ArrayList<CarInfoYqDto>();
		for (CarInfoYqPoJo pojo : datas) {
			CarInfoYqDto dto = new CarInfoYqDto();
			dto.setId(pojo.getId() != null ? pojo.getId().longValue() : null);
			dto.setCar_vin(pojo.getCar_vin());
			dto.setCar_model(pojo.getCar_model());
			dto.setTerminal_id(pojo.getTerminal_id() != null ? pojo.getTerminal_id().longValue() : null);
			dto.setAuto_terminal(pojo.getAuto_terminal() != null ? pojo.getAuto_terminal().longValue() : null);
			dto.setCreate_time(pojo.getCreate_time());
			dto.setUpdate_time(DateUtil.formatTime(pojo.getUpdate_time()));
			dto.setChassiss_num(pojo.getChassiss_num());
			dto.setCar_team_id(pojo.getCar_team_id() != null ? pojo.getCar_team_id().longValue() : null);
			dto.setOrder_number(pojo.getOrder_number());
			dto.setSales_status(pojo.getSales_status() != null ? pojo.getSales_status().longValue() : null);
			dto.setDel_flag(pojo.getDel_flag());
			dto.setInvoice_number(pojo.getInvoice_number());
			dto.setBusiness_code(pojo.getBusiness_code());
			dto.setSales_date(pojo.getSales_date());
			dto.setMb_sales_date(pojo.getMb_sales_date());
			dto.setMb_sales_status(pojo.getMb_sales_status() != null ? pojo.getMb_sales_status().intValue() : null);
			
			dtos.add(dto);
		}
		return dtos;
	}
}
