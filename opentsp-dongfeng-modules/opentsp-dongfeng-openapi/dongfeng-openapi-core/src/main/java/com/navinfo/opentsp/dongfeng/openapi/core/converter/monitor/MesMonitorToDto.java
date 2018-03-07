package com.navinfo.opentsp.dongfeng.openapi.core.converter.monitor;

import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.MesMonitorPojo;
import com.navinfo.opentsp.dongfeng.openapi.dto.monitor.MesMonitorDto;

import java.util.ArrayList;
import java.util.List;

public class MesMonitorToDto {

	public static List<MesMonitorDto> convert(List<MesMonitorPojo> pageInfo) {
		List<MesMonitorDto> monitorDtos = new ArrayList<MesMonitorDto>();
		for (MesMonitorPojo mesMonitorPojo : pageInfo) {
			MesMonitorDto monitorDto = new MesMonitorDto();
			monitorDto.setLogDate(DateUtil.formatTime(mesMonitorPojo.getLogDate()));
			monitorDto.setTransName(mesMonitorPojo.getTransName());
			monitorDto.setStepName(mesMonitorPojo.getStepName());
			monitorDto.setLinesRead(mesMonitorPojo.getLinesRead());
			monitorDto.setLinesWritten(mesMonitorPojo.getLinesWritten());
			monitorDto.setLinesUpdated(mesMonitorPojo.getLinesUpdated());
			monitorDto.setLinesOutput(mesMonitorPojo.getLinesOutput());
			monitorDto.setErrors(mesMonitorPojo.getErrors());

			monitorDtos.add(monitorDto);
		}
		return monitorDtos;
	}

}
