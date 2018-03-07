package com.navinfo.opentsp.dongfeng.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by liht on 2017/3/13.
 */
@Entity
@Table(name = "hy_team")
public class HyTeamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false, name = "T_ID", columnDefinition="bigint")
    private BigInteger tId;

    @Column(nullable = false, name = "DISTRICT")
    private int district;

    @Column(nullable = true, name = "T_NAME")
    private String tName;

    @Column(nullable = true, name = "T_TYPE")
    private int tType;

    @Column(nullable = true, name = "T_DATE", columnDefinition="bigint")
    private BigInteger tDate;

    @Column(nullable = true, name = "T_ACCOUNT_NAME")
    private String tAccountName;

    @Column(nullable = true, name = "PARENT_ID", columnDefinition="bigint")
    private BigInteger parentId;

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

    @Column(nullable = true, name = "COMPANY_ADDRESS")
    private String companyAddress;

    @Column(nullable = true, name = "COMPANY_CODE")
    private String companyCode;

    @Column(nullable = true, name = "COMPANY_TEL")
    private String companyTel;

    @Column(nullable = true, name = "COMPANY_FAX")
    private String companyFax;

    @Column(nullable = true, name = "COMPANY_LINKMAN")
    private String companyLinkMan;

    @Column(nullable = true, name = "COMPANY_LINKTEL")
    private String companyLinkTel;

    @Column(nullable = true, name = "LICENCE_NUMBER")
    private String licenceNumber;

    @Column(nullable = true, name = "DEL_FLAG")
    private int delFlag;

    @Column(nullable = true, name = "DEALER_CODE")
    private String dealerCode;

    @Column(nullable = true, name = "manage_brand")
    private String manageBrand;

    @Column(nullable = true, name = "team_lat", columnDefinition="bigint")
    private BigInteger teamLat;

    @Column(nullable = true, name = "team_lon", columnDefinition="bigint")
    private BigInteger teamLon;

    @Column(nullable = true, name = "enable_radius")
    private int enableRadius;

    @Column(nullable = true, name = "lock_radius")
    private int lockRadius;

    @Column(nullable = true, name = "secd_name_latlon", columnDefinition="TEXT", length = 65535)
    private String secdNameLatlon;

    @Column(nullable = true, name = "team_picture")
    private String teamPicture;

    @Column(nullable = true, name = "land_area_radius")
    private int land_area_radius;

    @Column(nullable = true, name = "province")
    private Integer province;

    @Column(nullable = true, name = "city")
    private Integer city;

    public BigInteger gettId() {
        return tId;
    }

    public void settId(BigInteger tId) {
        this.tId = tId;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettType() {
        return tType;
    }

    public void settType(int tType) {
        this.tType = tType;
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

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyLinkMan() {
        return companyLinkMan;
    }

    public void setCompanyLinkMan(String companyLinkMan) {
        this.companyLinkMan = companyLinkMan;
    }

    public String getCompanyLinkTel() {
        return companyLinkTel;
    }

    public void setCompanyLinkTel(String companyLinkTel) {
        this.companyLinkTel = companyLinkTel;
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

    public int getEnableRadius() {
        return enableRadius;
    }

    public void setEnableRadius(int enableRadius) {
        this.enableRadius = enableRadius;
    }

    public int getLockRadius() {
        return lockRadius;
    }

    public void setLockRadius(int lockRadius) {
        this.lockRadius = lockRadius;
    }

    public String getSecdNameLatlon() {
        return secdNameLatlon;
    }

    public void setSecdNameLatlon(String secdNameLatlon) {
        this.secdNameLatlon = secdNameLatlon;
    }

    public String getTeamPicture() {
        return teamPicture;
    }

    public void setTeamPicture(String teamPicture) {
        this.teamPicture = teamPicture;
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
}
