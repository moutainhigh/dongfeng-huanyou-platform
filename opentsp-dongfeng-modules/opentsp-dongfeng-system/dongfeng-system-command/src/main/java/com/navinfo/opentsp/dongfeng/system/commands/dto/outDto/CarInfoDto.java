package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarInfoDto {
	
	/**
	 * 车辆ID
	 */
	private BigInteger id;
	
	/**
	 * 图片路径
	 */
	private String imgPath;
	
	/**
	 * 车辆基本信息
	 */
	private CarBaseInfoDto carBaseInfo;
	
	/**
	 * 车辆销售信息
	 */
	private CarSaleInfoDto carSaleInfo;
	
	/**
	 * 车辆终端信息
	 */
	private CarTerminalInfoDto terminalInfo;
	
	/**
	 * 客户基本资料
	 */
	private CarBusinessInfoDto businessInfo;
	
	/**
	 * 安装单位信息
	 */
	private CarAssembleInfoDto assembleInfo;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public CarBaseInfoDto getCarBaseInfo() {
		return carBaseInfo;
	}

	public void setCarBaseInfo(CarBaseInfoDto carBaseInfo) {
		this.carBaseInfo = carBaseInfo;
	}

	public CarSaleInfoDto getCarSaleInfo() {
		return carSaleInfo;
	}

	public void setCarSaleInfo(CarSaleInfoDto carSaleInfo) {
		this.carSaleInfo = carSaleInfo;
	}

	public CarTerminalInfoDto getTerminalInfo() {
		return terminalInfo;
	}

	public void setTerminalInfo(CarTerminalInfoDto terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public CarBusinessInfoDto getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(CarBusinessInfoDto businessInfo) {
		this.businessInfo = businessInfo;
	}

	public CarAssembleInfoDto getAssembleInfo() {
		return assembleInfo;
	}

	public void setAssembleInfo(CarAssembleInfoDto assembleInfo) {
		this.assembleInfo = assembleInfo;
	}
	
}
