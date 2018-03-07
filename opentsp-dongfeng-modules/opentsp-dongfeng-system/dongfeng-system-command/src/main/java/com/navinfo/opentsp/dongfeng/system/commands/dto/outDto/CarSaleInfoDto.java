package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class CarSaleInfoDto {
	
	/**
	 * 车牌号
	 */
	private String chp;
	
	/**
	 * 车辆颜色
	 */
	private Integer color;
	
	/**
	 * AAK销售日期
	 */
	private BigInteger aakSaleTime;
	
	/**
	 * 销售发票号
	 */
	private String invoiceNumber;
	
	/**
	 * 经销商ID
	 */
	private BigInteger dealerId;
	
	/**
	 * 经销商名称
	 */
	private String dealerName;
	
	/**
	 * 经销商地址
	 */
	private String dealerAddress;

	public String getChp() {
		return chp;
	}

	public void setChp(String chp) {
		this.chp = chp;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public BigInteger getAakSaleTime() {
		return aakSaleTime;
	}

	public void setAakSaleTime(BigInteger aakSaleTime) {
		this.aakSaleTime = aakSaleTime;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigInteger getDealerId() {
		return dealerId;
	}

	public void setDealerId(BigInteger dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}
}
