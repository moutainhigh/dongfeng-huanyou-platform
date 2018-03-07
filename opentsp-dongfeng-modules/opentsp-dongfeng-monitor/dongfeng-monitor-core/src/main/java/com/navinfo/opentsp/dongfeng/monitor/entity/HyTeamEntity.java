package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/8
 */
@Entity
@Table(name = "hy_team")
public class HyTeamEntity {
    private BigInteger tId;
    private int district;
    private String tName;
    private Integer tType;
    private BigInteger tDate;
    private String tAccountName;
    private BigInteger parentId;
    private String tLinkman;
    private String tLinktel;
    private String tDesc;
    private String companyName;
    private String companyBusinessScope;
    private String companyAddress;
    private String companyCode;
    private String companyTel;
    private String companyFax;
    private String companyLinkman;
    private String companyLinktel;
    private String licenceNumber;
    private Integer delFlag;
    private String dealerCode;
    private String manageBrand;
    private BigInteger teamLat;
    private BigInteger teamLon;
    private Integer enableRadius;
    private Integer lockRadius;
    private String secdNameLatlon;
    private String teamPicture;
    private Integer landAreaRadius;

    @Id
    @Column(name = "T_ID", nullable = false, columnDefinition="bigint")
    public BigInteger gettId() {
        return tId;
    }

    public void settId(BigInteger tId) {
        this.tId = tId;
    }

    @Basic
    @Column(name = "DISTRICT", nullable = false)
    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    @Basic
    @Column(name = "T_NAME", nullable = true, length = 200)
    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    @Basic
    @Column(name = "T_TYPE", nullable = true)
    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    @Basic
    @Column(name = "T_DATE", nullable = true , columnDefinition="bigint")
    public BigInteger gettDate() {
        return tDate;
    }

    public void settDate(BigInteger tDate) {
        this.tDate = tDate;
    }

    @Basic
    @Column(name = "T_ACCOUNT_NAME", nullable = true, length = 50)
    public String gettAccountName() {
        return tAccountName;
    }

    public void settAccountName(String tAccountName) {
        this.tAccountName = tAccountName;
    }

    @Basic
    @Column(name = "PARENT_ID", nullable = true , columnDefinition="bigint")
    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "T_LINKMAN", nullable = true, length = 200)
    public String gettLinkman() {
        return tLinkman;
    }

    public void settLinkman(String tLinkman) {
        this.tLinkman = tLinkman;
    }

    @Basic
    @Column(name = "T_LINKTEL", nullable = true, length = 200)
    public String gettLinktel() {
        return tLinktel;
    }

    public void settLinktel(String tLinktel) {
        this.tLinktel = tLinktel;
    }

    @Basic
    @Column(name = "T_DESC", nullable = true, length = 2000)
    public String gettDesc() {
        return tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    @Basic
    @Column(name = "COMPANY_NAME", nullable = true, length = 200)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "COMPANY_BUSINESS_SCOPE", nullable = true, length = 200)
    public String getCompanyBusinessScope() {
        return companyBusinessScope;
    }

    public void setCompanyBusinessScope(String companyBusinessScope) {
        this.companyBusinessScope = companyBusinessScope;
    }

    @Basic
    @Column(name = "COMPANY_ADDRESS", nullable = true, length = 200)
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Basic
    @Column(name = "COMPANY_CODE", nullable = true, length = 50)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Basic
    @Column(name = "COMPANY_TEL", nullable = true, length = 50)
    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    @Basic
    @Column(name = "COMPANY_FAX", nullable = true, length = 50)
    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    @Basic
    @Column(name = "COMPANY_LINKMAN", nullable = true, length = 200)
    public String getCompanyLinkman() {
        return companyLinkman;
    }

    public void setCompanyLinkman(String companyLinkman) {
        this.companyLinkman = companyLinkman;
    }

    @Basic
    @Column(name = "COMPANY_LINKTEL", nullable = true, length = 50)
    public String getCompanyLinktel() {
        return companyLinktel;
    }

    public void setCompanyLinktel(String companyLinktel) {
        this.companyLinktel = companyLinktel;
    }

    @Basic
    @Column(name = "LICENCE_NUMBER", nullable = true, length = 200)
    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    @Basic
    @Column(name = "DEL_FLAG", nullable = true)
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "DEALER_CODE", nullable = true, length = 50)
    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    @Basic
    @Column(name = "manage_brand", nullable = true, length = 100)
    public String getManageBrand() {
        return manageBrand;
    }

    public void setManageBrand(String manageBrand) {
        this.manageBrand = manageBrand;
    }

    @Basic
    @Column(name = "team_lat", nullable = true, columnDefinition="bigint")
    public BigInteger getTeamLat() {
        return teamLat;
    }

    public void setTeamLat(BigInteger teamLat) {
        this.teamLat = teamLat;
    }

    @Basic
    @Column(name = "team_lon", nullable = true, columnDefinition="bigint")
    public BigInteger getTeamLon() {
        return teamLon;
    }

    public void setTeamLon(BigInteger teamLon) {
        this.teamLon = teamLon;
    }

    @Basic
    @Column(name = "enable_radius", nullable = true)
    public Integer getEnableRadius() {
        return enableRadius;
    }

    public void setEnableRadius(Integer enableRadius) {
        this.enableRadius = enableRadius;
    }

    @Basic
    @Column(name = "lock_radius", nullable = true)
    public Integer getLockRadius() {
        return lockRadius;
    }

    public void setLockRadius(Integer lockRadius) {
        this.lockRadius = lockRadius;
    }

    @Basic
    @Column(name = "secd_name_latlon", nullable = true, columnDefinition="text")
    public String getSecdNameLatlon() {
        return secdNameLatlon;
    }

    public void setSecdNameLatlon(String secdNameLatlon) {
        this.secdNameLatlon = secdNameLatlon;
    }

    @Basic
    @Column(name = "team_picture", nullable = true, length = 500)
    public String getTeamPicture() {
        return teamPicture;
    }

    public void setTeamPicture(String teamPicture) {
        this.teamPicture = teamPicture;
    }

    @Basic
    @Column(name = "land_area_radius", nullable = true)
    public Integer getLandAreaRadius() {
        return landAreaRadius;
    }

    public void setLandAreaRadius(Integer landAreaRadius) {
        this.landAreaRadius = landAreaRadius;
    }



}
