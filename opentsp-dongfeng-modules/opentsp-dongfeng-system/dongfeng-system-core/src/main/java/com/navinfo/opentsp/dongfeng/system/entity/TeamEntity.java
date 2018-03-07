package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Rex on 2017/3/13.
 */
@Entity
@Table(name = "hy_team")
public class TeamEntity implements Serializable {
    /**
     * 经销商ID
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false, name = "T_ID", columnDefinition="bigint")
    private BigInteger id;

    /**
     * 分区服务编码
     */
    @Column(nullable = false, name = "DISTRICT")
    private Integer district;

    /**
     * 经销商名称
     */
    @Column(nullable = true, name = "T_NAME")
    private String tname;

    @Column(nullable = true, name = "T_TYPE")
    private int ttype;

    @Column(nullable = true, name = "T_DATE", columnDefinition="bigint")
    private BigInteger tDate;

    @Column(nullable = true, name = "T_ACCOUNT_NAME")
    private String tAccountName;

    @Column(nullable = true, name = "T_LINKMAN")
    private String tLinkMan;

    @Column(nullable = true, name = "T_LINKTEL")
    private String tLinkTel;

    @Column(nullable = true, name = "T_DESC")
    private String tDesc;

    @Column(nullable = true, name = "COMPANY_NAME")
    private String companyName;

    @Column(nullable = true, name = "COMPANY_BUSINESS_SCOPE")
    private String companyBusinessScope;

    @Column(nullable = true, name = "COMPANY_TEL")
    private String companyTel;

    @Column(nullable = true, name = "LICENCE_NUMBER")
    private String licenceNumber;

    @Column(nullable = true, name = "DEL_FLAG")
    private int delFlag;

    @Column(nullable = true, name = "land_area_radius")
    private int land_area_radius;
    /**
     * 经销商编码
     */
    @Column(nullable = true, name = "DEALER_CODE")
    private String dealerCode;

    /**
     * 经营品牌
     */
    @Column(nullable = true, name = "manage_brand")
    private String manageBrand;

    /**
     * 所属区域
     */
    //@Column(nullable = true, name = "manage_brand")
    //private String pname;


    /**
     * 公司地址
     */
    @Column(nullable = true, name = "COMPANY_ADDRESS")
    private String companyAddress;

    /**
     * 公司联系人
     */
    @Column(nullable = true, name = "COMPANY_LINKMAN")
    private String companyLinkman;

    /**
     * 公司联系电话
     */
    @Column(nullable = true, name = "COMPANY_LINKTEL")
    private String companyLinktel;

    /**
     * 激活半径(KM)
     */
    @Column(nullable = true, name = "enable_radius")
    private Integer enableRadius;

    /**
     * 锁车半径(KM)
     */
    @Column(nullable = true, name = "lock_radius")
    private Integer lockRadius;

    /**
     * 父节点ID
     */
    @Column(nullable = true, name = "PARENT_ID")
    private Long parentId;

    /**
     * 经度
     */
    @Column(nullable = true, name = "team_lon")
    private Long teamLon;
    /**
     * 纬度
     */
    @Column(nullable = true, name = "team_lat")
    private Long teamLat;

    /**
     * 二级网点经纬度
     */
    @Column(nullable = true, name = "secd_name_latlon",columnDefinition="TEXT", length = 65535)
    private String secdNameLatLon;

    /**
     * 传真
     */
    @Column(nullable = true, name = "COMPANY_FAX")
    private String companyFax;

    /**
     * 邮编
     */
    @Column(nullable = true, name = "COMPANY_CODE")
    private String companyCode;

    /**
     * 经销商图片
     */
    @Column(nullable = true, name = "team_picture")
    private String teamPicture;

    @Column(nullable = true, name = "province")
    private Integer province;

    @Column(nullable = true, name = "city")
    private Integer city;
    @Column(nullable = true, name = "update_time", columnDefinition="bigint")
    private BigInteger updateTime;
    @Column(nullable = true, name = "SYNC_STATUS")
    private Integer syncStatus;
    @Column(nullable = true, name = "SYNC_SIGN_STATUS")
    private Integer syncSignStatus;
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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

    public Long getTeamLon() {
        return teamLon;
    }

    public void setTeamLon(Long teamLon) {
        this.teamLon = teamLon;
    }

    public Long getTeamLat() {
        return teamLat;
    }

    public void setTeamLat(Long teamLat) {
        this.teamLat = teamLat;
    }

    public String getSecdNameLatLon() {
        return secdNameLatLon;
    }

    public void setSecdNameLatLon(String secdNameLatLon) {
        this.secdNameLatLon = secdNameLatLon;
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

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public int getTtype() {
        return ttype;
    }

    public void setTtype(int ttype) {
        this.ttype = ttype;
    }

    public BigInteger gettDate() {
        return tDate;
    }

    public void settDate(BigInteger tDate) {
        this.tDate = tDate;
    }

    public String gettAccountName() {
        return tAccountName;
    }

    public void settAccountName(String tAccountName) {
        this.tAccountName = tAccountName;
    }

    public String gettLinkMan() {
        return tLinkMan;
    }

    public void settLinkMan(String tLinkMan) {
        this.tLinkMan = tLinkMan;
    }

    public String gettLinkTel() {
        return tLinkTel;
    }

    public void settLinkTel(String tLinkTel) {
        this.tLinkTel = tLinkTel;
    }

    public String gettDesc() {
        return tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBusinessScope() {
        return companyBusinessScope;
    }

    public void setCompanyBusinessScope(String companyBusinessScope) {
        this.companyBusinessScope = companyBusinessScope;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getLand_area_radius() {
        return land_area_radius;
    }

    public void setLand_area_radius(int land_area_radius) {
        this.land_area_radius = land_area_radius;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public BigInteger getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(BigInteger updateTime) {
        this.updateTime = updateTime;
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
