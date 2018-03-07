package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

public class SaleOutdto {

	/**
	 * 销售状态
	 */
	private int saleStatus;
	
	private String saleStatusDescription;
	
	/**
	 * 销售时间
	 */
	private Long saleDate;
	
	/**
	 * 销售时间字符串表达形式
	 */
	private String salesDateStr;
	/**
	 * 购车总金额
	 */
	private Double carAmount;
	
	/**
	 * 贷款总金额
	 */
	private Double loanAmount;
	
	/**
	 * 剩余未还款
	 */
	private Double surplus;

	/**
	 * 发票号
	 */
	private String invoiceNumber;
	
	private Long payType;

	public int getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Long getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Long saleDate) {
		this.saleDate = saleDate;
	}

	public Double getCarAmount() {
		return carAmount;
	}

	public void setCarAmount(Double carAmount) {
		this.carAmount = carAmount;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getSurplus() {
		return surplus;
	}

	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public String getSaleStatusDescription() {
		return saleStatusDescription;
	}

	public void setSaleStatusDescription(String saleStatusDescription) {
		this.saleStatusDescription = saleStatusDescription;
	}

	public String getSalesDateStr() {
		return salesDateStr;
	}

	public void setSalesDateStr(String salesDateStr) {
		this.salesDateStr = salesDateStr;
	}
	
}
