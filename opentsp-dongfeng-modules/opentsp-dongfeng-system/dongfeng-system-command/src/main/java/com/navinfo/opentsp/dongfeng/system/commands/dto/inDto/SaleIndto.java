package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

public class SaleIndto {

	/**
	 * STD销售状态
	 */
	private Integer saleStatus;
	
	/**
	 * STD销售日期
	 */
	private String saleDate;
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
	
	/**
	 * AAK销售状态-经销商到用户，0为已销售
	 */
	private Integer mbSalesStatus;
	
	/**
	 * AAK销售日期
	 */
	private String mbSalesDate;

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
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

	public Integer getMbSalesStatus() {
		return mbSalesStatus;
	}

	public void setMbSalesStatus(Integer mbSalesStatus) {
		this.mbSalesStatus = mbSalesStatus;
	}

	public String getMbSalesDate() {
		return mbSalesDate;
	}

	public void setMbSalesDate(String mbSalesDate) {
		this.mbSalesDate = mbSalesDate;
	}
	
}
