package com.navinfo.dongfeng.terminal.comm.model.caremployee;

public class CarEmployee implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long car_id;//车辆id
	private Long e_id;//司机id

	public Long getCar_id() {
		return car_id;
	}
	public void setCar_id(Long car_id) {
		this.car_id = car_id;
	}
	public Long getE_id() {
		return e_id;
	}
	public void setE_id(Long e_id) {
		this.e_id = e_id;
	}
	
	
	
     
}
