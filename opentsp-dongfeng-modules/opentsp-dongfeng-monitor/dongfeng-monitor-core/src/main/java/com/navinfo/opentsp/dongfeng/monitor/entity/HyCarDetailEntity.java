package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hy_car_detail")
public class HyCarDetailEntity {
	
	private Long id;
	
	/**
	 * 发动机号
	 */
	private String engineNumber;
	
	/**
	 * hy_car_detail
	 */
	private int saleStatus;
	
	/**
	 * 销售时间
	 */
	private Long saleDate;
	
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
	 * 发动机类型
	 */
	private String engineType;
	
	/**
	 * 发票号
	 */
	private String invoiceNumber;
	
	private String carOwner;

	@Id
	@Column(name = "CAR_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ENGINE_NUMBER")
	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	@Column(name = "SALES_STATUS")
	public int getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}

	@Column(name = "SALES_DATE")
	public Long getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Long saleDate) {
		this.saleDate = saleDate;
	}

	@Column(name = "CAR_AMOUNT")
	public Double getCarAmount() {
		return carAmount;
	}

	public void setCarAmount(Double carAmount) {
		this.carAmount = carAmount;
	}

	@Column(name = "LOAN_AMOUNT")
	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "SURPLUS")
	public Double getSurplus() {
		return surplus;
	}

	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}

	@Column(name = "ENGINE_TYPE")
	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	@Column(name = "invoice_number")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Column(name = "CAR_OWNER")
	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}
	
}
