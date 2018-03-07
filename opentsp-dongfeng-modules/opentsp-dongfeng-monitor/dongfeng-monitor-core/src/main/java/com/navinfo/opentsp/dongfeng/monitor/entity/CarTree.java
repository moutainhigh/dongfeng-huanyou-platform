package com.navinfo.opentsp.dongfeng.monitor.entity;


public class CarTree extends RootTree {

	/*行驶状态 :在线行驶(111)7，在线停车(011)3，在线不定位(001)1，不在线行驶(110)6,不在线停车(010)2，不在线不定位(000)0
	第一位表示在线状态（0：不在线，1：在线）
	第二位表示定位状态（0：不定位，1：定位）
	第三位表示行驶状态（0：停车，1：行驶）*/
	private int carStauts;
	//行驶方向
	private int carDirection;
	//纬度
	private double lat;
	//经度
	private double lng;
	//锁状态
	private int lockStauts;

	public CarTree() {
		this.setOpen(false);
		this.setIsParent(false);
		this.setChecked(false);
	}

	public int getCarStauts() {
		return carStauts;
	}

	public void setCarStauts(int carStauts) {
		this.carStauts = carStauts;
	}

	public int getCarDirection() {
		return carDirection;
	}

	public void setCarDirection(int carDirection) {
		this.carDirection = carDirection;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getLockStauts() {
		return lockStauts;
	}

	public void setLockStauts(int lockStauts) {
		this.lockStauts = lockStauts;
	}
}
