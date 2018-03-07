package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.io.Serializable;

/**
 * 所在地理位置的描述信息
 * <p/>
 * Created by jiangcm on 2016/4/14
 */
public class GeographicalDto implements Serializable
{
	// 所在城市Code
	private String cityCode;
	// 所在城市
	private String city;
	// 所在区县
	private String dist;
	// 所在区域
	private String area;
	// 所在乡镇
	private String town;
	// 所在村
	private String village;

	public String getCityCode()
	{
		return cityCode;
	}

	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getDist()
	{
		return dist;
	}

	public void setDist(String dist)
	{
		this.dist = dist;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getTown()
	{
		return town;
	}

	public void setTown(String town)
	{
		this.town = town;
	}

	public String getVillage()
	{
		return village;
	}

	public void setVillage(String village)
	{
		this.village = village;
	}

	@Override
	public String toString()
	{
		return "GeographicalDto{" +
				"cityCode='" + cityCode + '\'' +
				", city='" + city + '\'' +
				", dist='" + dist + '\'' +
				", area='" + area + '\'' +
				", town='" + town + '\'' +
				", village='" + village + '\'' +
				'}';
	}
}
