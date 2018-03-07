package com.navinfo.opentsp.dongfeng.report.converter.general;

import com.navinfo.opentsp.dongfeng.report.dto.general.QueryFaultSummaryDto;
import com.navinfo.opentsp.dongfeng.report.pojo.general.QueryFaultSummaryPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FaultSummaryDto {

	public static List<QueryFaultSummaryDto> convert(List<QueryFaultSummaryPojo> list) {
		List<QueryFaultSummaryDto> dtos = new ArrayList<QueryFaultSummaryDto>();
		for (QueryFaultSummaryPojo pojo : list) {
			QueryFaultSummaryDto dto = new QueryFaultSummaryDto();
			dto.setId(pojo.getId().longValue());
			dto.setChassisNum(pojo.getChassisNum());
			dto.setPlateNum(pojo.getPlateNum());
			dto.setBdTerCode(pojo.getBdTerCode());
			dto.setFkTerCode(pojo.getFkTerCode());
			dto.settName(pojo.gettName());
			dto.setBusinessName(pojo.getBusinessName());
			dto.setCarModel(pojo.getCarModel());
			dto.setEngineNumber(pojo.getEngineNumber());
			dto.setEngineType(pojo.getEngineType());
			dto.setStructureNum(pojo.getStructureNum());
			dto.setBreakdownDis(pojo.getBreakdownDis());
			dto.setSpn(pojo.getSpn());
			dto.setFmi(pojo.getFmi());
			dto.setOccurDate(pojo.getOccurDate());
			dto.setDuration(pojo.getDuration());
			dto.setbLoction(pojo.getbLoction());
			dto.seteLoction(pojo.geteLoction());
			dtos.add(dto);
		}
		Collections.sort(dtos, new Comparator<QueryFaultSummaryDto>() {
			@Override
			public int compare(QueryFaultSummaryDto o1, QueryFaultSummaryDto o2) {
				return o2.getChassisNum().compareToIgnoreCase(o1.getChassisNum());
			}
		});
		return dtos;
	}

}
