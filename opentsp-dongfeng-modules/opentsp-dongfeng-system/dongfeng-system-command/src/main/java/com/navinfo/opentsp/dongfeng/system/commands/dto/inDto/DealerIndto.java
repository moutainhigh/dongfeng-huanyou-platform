package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.Length;

public class DealerIndto {
	/**
	 * 经销商ID
	 */
	private Long id;
	/**
	 * 经销商名称
	 */
	@Length(message = "经销商名称长度最多40个汉字", max = 40, groups = { GroupCommand.class })
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
	@Length(message = "经销商地址长度最多40个汉字", max = 40, groups = { GroupCommand.class })
	private String companyAddress;

	/**
	 * 公司联系人
	 */
	@Length(message = "联系人长度最多10个汉字", max = 10, groups = { GroupCommand.class })
	private String companyLinkman;

	/**
	 * 公司联系电话
	 */
	@Length(message = "联系电话长度最多30位", max = 30, groups = { GroupCommand.class })
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

	private Integer tType;

	private Integer govCodePrvc;

	private Integer govCodeCity;

	private String tLinkMan;

	private String tLinkTle;

	private Long teamLat;

	private Long teamLon;

	private String companyFax;

	private String companyCode;

	private String teamPicture;
	// @Converter(target = "hySecdTeamListBean", type = ParameterType.LIST)
	private String hySecdTeamList;
	// private ArrayList<SecdTeamIndto> hySecdTeamListBean;
	// 删除的二级网点
	private String delSecdTeam;

	/**
	 * 创建人
	 */
	private String tAccountName;
	private String tDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getEnableRadius() {
		return enableRadius;
	}

	public void setEnableRadius(String enableRadius) {
		this.enableRadius = enableRadius;
	}

	public String getLockRadius() {
		return lockRadius;
	}

	public void setLockRadius(String lockRadius) {
		this.lockRadius = lockRadius;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
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

	public String getHySecdTeamList() {
		return hySecdTeamList;
	}

	public void setHySecdTeamList(String hySecdTeamList) {
		this.hySecdTeamList = hySecdTeamList;
	}

	public String getDelSecdTeam() {
		return delSecdTeam;
	}

	public void setDelSecdTeam(String delSecdTeam) {
		this.delSecdTeam = delSecdTeam;
	}

	public String gettAccountName() {
		return tAccountName;
	}

	public void settAccountName(String tAccountName) {
		this.tAccountName = tAccountName;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

}
