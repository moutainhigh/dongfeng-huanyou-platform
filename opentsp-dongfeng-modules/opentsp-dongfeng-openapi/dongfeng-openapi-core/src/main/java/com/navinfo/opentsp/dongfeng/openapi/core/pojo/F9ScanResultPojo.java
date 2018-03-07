package com.navinfo.opentsp.dongfeng.openapi.core.pojo;

import java.math.BigInteger;

/**
 * 
 * @author xltianc.zh
 *
 */
public class F9ScanResultPojo {

	private String terSim; // 终端sim卡
	private String terCode; // 终端ID
	private String terType; // 终端类型
	private String ter; // 终端通信检测
	private String terTime; // 末次连接平台的时间
	private String gps; // GPS卫星天线检测
	private Double gpsLongitude; // 末次有效经度
	private Double gpsLatitude; // 末次有效纬度
	private String gpsTime; // 末次有效位置的时间
	private String acc; // 车辆ACC线检测
	private String accTime; // 末次ACC为ON的时间
	private String can; // CAN信号检测
	private String canTime; // 末次有效CAN信号的时间

	private BigInteger commId;
	private String engineType;

	public String getTerSim() {
		return terSim;
	}

	public String getTerCode() {
		return terCode;
	}

	public String getTerType() {
		return terType;
	}

	public String getTer() {
		return ter;
	}

	public String getTerTime() {
		return terTime;
	}

	public String getGps() {
		return gps;
	}

	public Double getGpsLongitude() {
		return gpsLongitude;
	}

	public Double getGpsLatitude() {
		return gpsLatitude;
	}

	public String getGpsTime() {
		return gpsTime;
	}

	public String getAcc() {
		return acc;
	}

	public String getAccTime() {
		return accTime;
	}

	public String getCan() {
		return can;
	}

	public String getCanTime() {
		return canTime;
	}

	public BigInteger getCommId() {
		return commId;
	}

	public void setTerSim(String terSim) {
		this.terSim = terSim;
	}

	public void setTerCode(String terCode) {
		this.terCode = terCode;
	}

	public void setTerType(String terType) {
		this.terType = terType;
	}

	public void setTer(String ter) {
		this.ter = ter;
	}

	public void setTerTime(String terTime) {
		this.terTime = terTime;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public void setGpsLongitude(Double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public void setGpsLatitude(Double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public void setAccTime(String accTime) {
		this.accTime = accTime;
	}

	public void setCan(String can) {
		this.can = can;
	}

	public void setCanTime(String canTime) {
		this.canTime = canTime;
	}

	public void setCommId(BigInteger commId) {
		this.commId = commId;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

}
