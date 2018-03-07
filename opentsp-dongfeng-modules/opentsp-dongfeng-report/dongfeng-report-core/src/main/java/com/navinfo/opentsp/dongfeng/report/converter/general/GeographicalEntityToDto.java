package com.navinfo.opentsp.dongfeng.report.converter.general;


import com.navinfo.opentsp.dongfeng.report.dto.general.GeographicalDto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 所在地理位置的描述信息数据DTO转换
 */
public class GeographicalEntityToDto
{
	/**
	 * 单条数据装换
	 *
	 * @param validateMap 所在地理位置的描述信息
	 * @return 所在地理位置的描述信息数据DTO
	 */
	public static GeographicalDto mapToDto(Map<String, Object> validateMap)
	{
		GeographicalDto dto = new GeographicalDto();
//		// 所在城市Code
//		if (validateMap.get("cityCode") != null)
//		{
//			dto.setCityCode((String) validateMap.get("cityCode"));
//		}
		// 所在城市
		LinkedHashMap simInfo = (LinkedHashMap) validateMap.get("city");
		if (simInfo.get("value") != null)
		{
			dto.setCity(simInfo.get("value").toString());
		}
		if (simInfo.get("code") != null)
		{
			dto.setCityCode(simInfo.get("code").toString());
		}
		// 所在区县
		simInfo = (LinkedHashMap) validateMap.get("dist");
		if (simInfo.get("value") != null)
		{
			dto.setDist(simInfo.get("value").toString());
		}
		// 所在区域
		simInfo = (LinkedHashMap) validateMap.get("area");
		if (simInfo.get("value") != null)
		{
			dto.setArea(simInfo.get("value").toString());
		}
		// 所在乡镇
		simInfo = (LinkedHashMap) validateMap.get("town");
		if (simInfo.get("value") != null)
		{
			dto.setTown(simInfo.get("value").toString());
		}
		// 所在村
		simInfo = (LinkedHashMap) validateMap.get("village");
		if (simInfo.get("value") != null)
		{
			dto.setVillage(simInfo.get("value").toString());
		}
		return dto;
	}
}
