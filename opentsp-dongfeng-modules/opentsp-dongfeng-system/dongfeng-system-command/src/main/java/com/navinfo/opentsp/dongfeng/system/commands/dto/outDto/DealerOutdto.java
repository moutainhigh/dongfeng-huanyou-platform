package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import java.math.BigInteger;

public class DealerOutdto {

	/**
	 * 经销商ID
	 */
	private BigInteger id;

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
	private int enableRadius;

	/**
	 * 锁车半径(KM)
	 */
	private int lockRadius;
	/**
	 * 父节点id
	 */
	private Long parentId;

	private int tType;

	private int govCodePrvc;

	private int govCodeCity;

	private String tLinkMan;

	private String tLinkTle;

	private Long teamLat;

	private Long teamLon;

	private String tompanyFax;

	private String companyCode;

	private String teamPicture;
	/**
	 *
	 */
	private String hySecdTeamList;

	public String getHySecdTeamList() {
		return hySecdTeamList;
	}

	public void setHySecdTeamList(String hySecdTeamList) {

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

	public int getEnableRadius() {
		return enableRadius;
	}

	public void setEnableRadius(Integer enableRadius) {
		this.enableRadius = enableRadius/1000;
	}

	public int getLockRadius() {
		return lockRadius;
	}

	public void setLockRadius(Integer lockRadius) {
		this.lockRadius = lockRadius/1000;
	}

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

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int gettType() {
		return tType;
	}

	public void settType(int tType) {
		this.tType = tType;
	}

	public int getGovCodePrvc() {
		return govCodePrvc;
	}

	public void setGovCodePrvc(int govCodePrvc) {
		this.govCodePrvc = govCodePrvc;
	}

	public int getGovCodeCity() {
		return govCodeCity;
	}

	public void setGovCodeCity(int govCodeCity) {
		this.govCodeCity = govCodeCity;
	}

	public String gettLinkMan() {
		return tLinkMan;
	}

	public void settLinkMan(String tLinkMan) {
		this.tLinkMan = tLinkMan;
	}

	public String gettLinkTle() {
		return tLinkTle;
	}

	public void settLinkTle(String tLinkTle) {
		this.tLinkTle = tLinkTle;
	}

	public Long getTeamLat() {
		return teamLat;
	}

	public void setTeamLat(Long teamLat) {
		this.teamLat = teamLat;
	}

	public Long getTeamLon() {
		return teamLon;
	}

	public void setTeamLon(Long teamLon) {
		this.teamLon = teamLon;
	}

	public String getTompanyFax() {
		return tompanyFax;
	}

	public void setTompanyFax(String tompanyFax) {
		this.tompanyFax = tompanyFax;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getTeamPicture() {
		return teamPicture;
	}

	public void setTeamPicture(String teamPicture) {
		this.teamPicture = teamPicture;
	}
}
