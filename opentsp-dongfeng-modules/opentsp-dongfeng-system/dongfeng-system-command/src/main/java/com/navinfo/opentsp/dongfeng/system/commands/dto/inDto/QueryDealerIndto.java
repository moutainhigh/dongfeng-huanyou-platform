package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import java.util.List;

public class QueryDealerIndto {
	
	/**
	 * 经销商ID
	 */

    //终端型号
	private java.math.BigInteger id;

	/**
	 * 经销商名称
	 */
	private String tname;
	
	/**
	 * 经销商编码
	 */
	private String dealerCode;
	
	/**
	 * 经营品牌
	 */
	private String manageBrand;
	
	/**
	 * 所属区域
	 */
	private String pname;


	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 公司联系人
	 */
	private String companyLinkman;

	/**
	 * 公司联系电话
	 */
	private String companyLinktel;

	/**
	 * 激活半径(KM)
	 */
	private String enableRadius;

	/**
	 * 锁车半径(KM)
	 */
	private String lockRadius;

	/**
	 * 父节点id
	 */
	private Long parentId;
	
	/**
	 * 销售状态
	 */
	private int saleStatus;
	
	/**
	 * 销售时间
	 */
	private long saleDate;

	/**
	 * 购车总金额
	 */
	private double carAmount;
	
	/**
	 * 贷款总金额
	 */
	private double loanAmount;
	
	/**
	 * 剩余未还款
	 */
	private double surplus;
	
	/**
	 * 发票号
	 */
	private String invoiceNumber;
	/**
	 *
	 */
	private List<SecdStationIndto> hySecdTeamList;

	public List<SecdStationIndto> getHySecdTeamList() {
		return hySecdTeamList;
	}

	public void setHySecdTeamList(List<SecdStationIndto> hySecdTeamList) {
		this.hySecdTeamList = hySecdTeamList;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyLinkman() {
		return companyLinkman;
	}

	public void setCompanyLinkman(String companyLinkman) {
		this.companyLinkman = companyLinkman;
	}

	public String getCompanyLinktel() {
		return companyLinktel;
	}

	public void setCompanyLinktel(String companyLinktel) {
		this.companyLinktel = companyLinktel;
	}

	public String getEnableRadius() {
		return enableRadius;
	}

//	public void setEnableRadius(Integer enableRadius) {
//		this.enableRadius = enableRadius/1000+"";
//	}

	public String getLockRadius() {
		return lockRadius;
	}

//	public void setLockRadius(Integer lockRadius) {
//		this.lockRadius = lockRadius/1000+"";
//	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getManageBrand() {
		return manageBrand;
	}

	public void setManageBrand(String manageBrand) {
		this.manageBrand = manageBrand;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public java.math.BigInteger getId() {
		return id;
	}

	public void setId(java.math.BigInteger id) {
		this.id = id;
	}

	public int getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}

	public long getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(long saleDate) {
		this.saleDate = saleDate;
	}

	public double getCarAmount() {
		return carAmount;
	}

	public void setCarAmount(double carAmount) {
		this.carAmount = carAmount;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setEnableRadius(String enableRadius) {
		
		if (null != enableRadius && !"".equals(enableRadius.trim())) {
			this.enableRadius = Integer.valueOf(enableRadius)/1000+"";
		}
//		this.enableRadius = enableRadius;
	}

	public void setLockRadius(String lockRadius) {
		
		if (null != lockRadius && !"".equals(lockRadius.trim())) {
			this.lockRadius = Integer.valueOf(lockRadius)/1000+"";
		}
//		this.lockRadius = lockRadius;
	}
	
}
