package com.navinfo.opentsp.dongfeng.system.pojo;

import com.navinfo.opentsp.dongfeng.system.entity.SecdteamEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */

public class SDealerPojo {
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
	private Integer enableRadius;
	/**
	 * 锁车半径(KM)
	 */
	private Integer lockRadius;
	/**
	 * 父节点id
	 */
	private BigInteger parentId;

	private Integer tType;

	private Integer govCodePrvc;

	private Integer govCodeCity;

	private String tLinkMan;

	private String tLinkTle;

	private BigInteger teamLat;

	private BigInteger teamLon;

	private String companyFax;

	private String companyCode;

	private String teamPicture;

	private List<SecdteamEntity> secdTeam;

	private String hySecdTeamList;

	private String tDate;

	private String tAccountName;
	// 同步TBOSS 经销商同步状态 0 同步成功 其它 同步失败 1:新增 2:删除 5：修改
	private Integer syncStatus;
	// 同步标签系统 经销商同步标签系统同步状态 200 同步成功 其它 同步失败 0:新增 1：修改 2:删除
	private Integer syncSignStatus;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public Integer getEnableRadius() {
		return enableRadius;
	}

	public void setEnableRadius(Integer enableRadius) {
		this.enableRadius = enableRadius;
	}

	public Integer getLockRadius() {
		return lockRadius;
	}

	public void setLockRadius(Integer lockRadius) {
		this.lockRadius = lockRadius;
	}

	public BigInteger getParentId() {
		return parentId;
	}

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public Integer gettType() {
		return tType;
	}

	public void settType(Integer tType) {
		this.tType = tType;
	}

	public Integer getGovCodePrvc() {
		return govCodePrvc;
	}

	public void setGovCodePrvc(Integer govCodePrvc) {
		this.govCodePrvc = govCodePrvc;
	}

	public Integer getGovCodeCity() {
		return govCodeCity;
	}

	public void setGovCodeCity(Integer govCodeCity) {
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

	public BigInteger getTeamLat() {
		return teamLat;
	}

	public void setTeamLat(BigInteger teamLat) {
		this.teamLat = teamLat;
	}

	public BigInteger getTeamLon() {
		return teamLon;
	}

	public void setTeamLon(BigInteger teamLon) {
		this.teamLon = teamLon;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
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

	public List<SecdteamEntity> getSecdTeam() {
		return secdTeam;
	}

	public void setSecdTeam(List<SecdteamEntity> secdTeam) {
		this.secdTeam = secdTeam;
	}

	public String getHySecdTeamList() {
		return hySecdTeamList;
	}

	public void setHySecdTeamList(String hySecdTeamList) {
		this.hySecdTeamList = hySecdTeamList;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

	public String gettAccountName() {
		return tAccountName;
	}

	public void settAccountName(String tAccountName) {
		this.tAccountName = tAccountName;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Integer getSyncSignStatus() {
		return syncSignStatus;
	}

	public void setSyncSignStatus(Integer syncSignStatus) {
		this.syncSignStatus = syncSignStatus;
	}

}