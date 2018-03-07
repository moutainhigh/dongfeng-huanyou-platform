package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hy_car_detail")
public class CarDetailEntity {
	
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
	
	/**
	 * 图片完整路径
	 */
	private String imgPath;
	
	/**
	 * AAK销售状态-经销商到用户，0为已销售,1为未售
	 */
	private Integer mbSalesStatus;
	
	/**
	 * AAK销售日期-经销商到用户
	 */
	private Long mbSalesDate;

	/**
	 * 发动机
	 */
	private String engineTypeRear;
	
	/**
	 * 发动机额定功率
	 */
	private String edgl;
	
	/**
	 * 最大功率
	 */
	private String zdgl;
	
	/**
	 * 整备质量
	 */
	private String zbzl;
	
	/**
	 * 总质量
	 */
	private String zzl;
	
	/**
	 * 燃料类型
	 */
	private Integer fuel = 1;

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

	@Column(name="img_path")
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Column(name="MB_SALES_STATUS")
	public Integer getMbSalesStatus() {
		return mbSalesStatus;
	}

	public void setMbSalesStatus(Integer mbSalesStatus) {
		this.mbSalesStatus = mbSalesStatus;
	}

	@Column(name="MB_SALES_DATE")
	public Long getMbSalesDate() {
		return mbSalesDate;
	}

	public void setMbSalesDate(Long mbSalesDate) {
		this.mbSalesDate = mbSalesDate;
	}
	
	@Column(name="ENGINE_TYPE_REAR")
	public String getEngineTypeRear() {
		return engineTypeRear;
	}

	public void setEngineTypeRear(String engineTypeRear) {
		this.engineTypeRear = engineTypeRear;
	}

	@Column(name="RATED_POWER")
	public String getEdgl() {
		return edgl;
	}

	public void setEdgl(String edgl) {
		this.edgl = edgl;
	}

	@Column(name="MAXIMUM_POWER")
	public String getZdgl() {
		return zdgl;
	}

	public void setZdgl(String zdgl) {
		this.zdgl = zdgl;
	}

	@Column(name="CURB_WEIGHT")
	public String getZbzl() {
		return zbzl;
	}

	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}

	@Column(name="TOTAL_MASS")
	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}

	@Column(name="fuel")
	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}
	
	
}
