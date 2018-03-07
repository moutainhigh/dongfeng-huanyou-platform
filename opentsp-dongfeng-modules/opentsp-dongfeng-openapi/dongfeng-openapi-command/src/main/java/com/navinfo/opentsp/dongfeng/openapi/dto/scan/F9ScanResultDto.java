package com.navinfo.opentsp.dongfeng.openapi.dto.scan;

/**
 * 
 * @author xltianc.zh
 *
 */
public class F9ScanResultDto {

	private String tersim; // 终端sim卡
	private String tercode; // 终端ID
	private String tertype; // 终端类型
	private String ter; // 终端通信检测
	private String tertime; // 末次连接平台的时间
	private String gps; // GPS卫星天线检测
	private Double gpslongitude; // 末次有效经度
	private Double gpslatitude; // 末次有效纬度
	private String gpstime; // 末次有效位置的时间
	private String acc; // 车辆ACC线检测
	private String acctime; // 末次ACC为ON的时间
	private String can; // CAN信号检测
	private String cantime; // 末次有效CAN信号的时间

	public String getTersim() {
		return tersim;
	}

	public void setTersim(String tersim) {
		this.tersim = tersim;
	}

	public String getTercode() {
		return tercode;
	}

	public void setTercode(String tercode) {
		this.tercode = tercode;
	}

	public String getTertype() {
		return tertype;
	}

	public void setTertype(String tertype) {
		this.tertype = tertype;
	}

	public String getTer() {
		return ter;
	}

	public void setTer(String ter) {
		this.ter = ter;
	}

	public String getTertime() {
		return tertime;
	}

	public void setTertime(String tertime) {
		this.tertime = tertime;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public Double getGpslongitude() {
		return gpslongitude;
	}

	public void setGpslongitude(Double gpslongitude) {
		this.gpslongitude = gpslongitude;
	}

	public Double getGpslatitude() {
		return gpslatitude;
	}

	public void setGpslatitude(Double gpslatitude) {
		this.gpslatitude = gpslatitude;
	}

	public String getGpstime() {
		return gpstime;
	}

	public void setGpstime(String gpstime) {
		this.gpstime = gpstime;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getAcctime() {
		return acctime;
	}

	public void setAcctime(String acctime) {
		this.acctime = acctime;
	}

	public String getCan() {
		return can;
	}

	public void setCan(String can) {
		this.can = can;
	}

	public String getCantime() {
		return cantime;
	}

	public void setCantime(String cantime) {
		this.cantime = cantime;
	}

}
