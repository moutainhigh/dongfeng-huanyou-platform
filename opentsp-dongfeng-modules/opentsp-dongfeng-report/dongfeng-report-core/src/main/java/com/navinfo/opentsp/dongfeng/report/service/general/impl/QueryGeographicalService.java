package com.navinfo.opentsp.dongfeng.report.service.general.impl;

import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import com.navinfo.opentsp.dongfeng.report.converter.general.GeographicalEntityToDto;
import com.navinfo.opentsp.dongfeng.report.dto.general.GeographicalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 查询所在地理位置的描述信息服务
 * <p/>
 * Created by jiangcm on 2016/4/14.
 */
@Service
public class QueryGeographicalService
{
	/**
	 * QueryGeographical Service LOG
	 */
	protected static final Logger logger = LoggerFactory.getLogger(QueryGeographicalService.class);
	/**
	 * 逆地理编码服务URL
	 */
	@Value("${geographical_url}")
	private String url;

	/**
	 * 查询所在地理位置的描述信息
	 *
	 * @param lat 纬度
	 * @param lon 经度
	 * @return GeographicalDto 所在地理位置的描述信息
	 */
	public GeographicalDto queryGeographical(String lat, String lon)
	{
		logger.info("====================================查询所在地理位置的描述信息开始==================================");
		GeographicalDto geographicalDto = new GeographicalDto();
		try
		{
			//========================================================================
			// 设置请求的URL
			//========================================================================
			String latLon = "lat=" + lat + "&" + "lon=" + lon;
			String validateStr = HttpUtil.get(url + latLon, "", "");
			// 获取返回值
			Map<String, Object> validateMap = JsonUtil.toMap(validateStr);
			if (!validateMap.isEmpty())
			{
				// DTO转换
				geographicalDto = GeographicalEntityToDto.mapToDto(validateMap);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("queryGeographical fail", e.getMessage(), e);
		}
		logger.info("====================================查询所在地理位置的描述信息结束==================================");
		return geographicalDto;
	}

	/**
	 * 逆地理
	 *
	 * @param lat,lon 纬度、经度组合
	 * @return 地点名称
	 */
	public String getPosition(String lat, String lon)
	{
		// 获取所在地理位置
		GeographicalDto geographicalDto = queryGeographical(lat, lon);
		String position = "";
		String city = geographicalDto.getCity();
		if (city != null)
		{
			position += city;
		}
		String dist = geographicalDto.getDist();
		if (dist != null)
		{
			position += dist;
		}
		String area = geographicalDto.getArea();
		if (area != null)
		{
			position += area;
		}
		String town = geographicalDto.getTown();
		if (town != null)
		{
			position += town;
		}
		String village = geographicalDto.getVillage();
		if (village != null)
		{
			position += village;
		}
		return position;
	}

	/**
	 * 逆地理
	 *
	 * @return 地点名称
	 */
	public String getPosition(GeographicalDto geographicalDto)
	{
		String position = "";
		String city = geographicalDto.getCity();
		if (city != null)
		{
			position += city;
		}
		String dist = geographicalDto.getDist();
		if (dist != null)
		{
			position += dist;
		}
		String area = geographicalDto.getArea();
		if (area != null)
		{
			position += area;
		}
		String town = geographicalDto.getTown();
		if (town != null)
		{
			position += town;
		}
		String village = geographicalDto.getVillage();
		if (village != null)
		{
			position += village;
		}
		return position;
	}
}