package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 经销商Tip框
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/5/26
 */
public class QueryDealerTipPojo {
    //经销商主键
    private BigInteger id;
    //名称
    private String name;
    //经度
    private BigDecimal lng;
    //纬度
    private BigDecimal lat;
    //经销商编码
    private String dealerCode;
    //经营品牌
    private String manageBrand;
    //所在销售区域
    private String district;
    //联系人
    private String companyLinkman;
    //联系电话
    private String companyLinktel;
    //联系传真
    private String companyFax;
    //图片地址
    private String teamPicture;
    //激活半径
    private Integer enableRadius;
    //锁车半径
    private Integer lockRadius;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getTeamPicture() {
        return teamPicture;
    }

    public void setTeamPicture(String teamPicture) {
        this.teamPicture = teamPicture;
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
}
