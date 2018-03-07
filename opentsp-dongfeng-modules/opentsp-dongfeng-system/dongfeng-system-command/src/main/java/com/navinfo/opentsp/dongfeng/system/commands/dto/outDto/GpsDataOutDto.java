package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

public class GpsDataOutDto {
	
	private Integer flag;
	
	private String gps;
	
	private String gpstime;
	
	private String location;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getGpstime() {
		return gpstime;
	}

	public void setGpstime(String gpstime) {
		this.gpstime = gpstime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
