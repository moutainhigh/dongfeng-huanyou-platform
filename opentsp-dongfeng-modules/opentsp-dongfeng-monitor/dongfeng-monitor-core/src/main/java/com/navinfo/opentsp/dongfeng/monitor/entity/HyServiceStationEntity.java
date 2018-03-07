package com.navinfo.opentsp.dongfeng.monitor.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/13
 */
@Entity
@Table(name = "hy_service_station")
public class HyServiceStationEntity {
    private BigInteger id;
    private String stationName;
    private String nameForshort;
    private String address;
    private BigInteger longitude;
    private BigInteger latitude;
    private Integer radius;
    private Integer povince;
    private Integer city;
    private String serviceManager;
    private String phone;
    private String fax;
    private String messages;
    private BigInteger teamId;
    private BigInteger strandedMaxTime;
    private Integer starLevel;
    private String picture;
    private Integer serviceType;
    private String serviceContent;
    private String partsContent;
    private Integer serviceRadius;
    private String serviceCode;
    private String toolContent;
    private String stationType;
    private BigInteger creatDate;
    private BigInteger accountId;
    private String fixedTelephone;
    private Integer delFlag;
    private BigInteger isCore;

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "station_name", nullable = true, length = 200)
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "name_forshort", nullable = true, length = 100)
    public String getNameForshort() {
        return nameForshort;
    }

    public void setNameForshort(String nameForshort) {
        this.nameForshort = nameForshort;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "longitude", nullable = true, columnDefinition = "bigint")
    public BigInteger getLongitude() {
        return longitude;
    }

    public void setLongitude(BigInteger longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, columnDefinition = "bigint")
    public BigInteger getLatitude() {
        return latitude;
    }

    public void setLatitude(BigInteger latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "radius", nullable = true)
    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    @Basic
    @Column(name = "povince", nullable = true)
    public Integer getPovince() {
        return povince;
    }

    public void setPovince(Integer povince) {
        this.povince = povince;
    }

    @Basic
    @Column(name = "city", nullable = true)
    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    @Basic
    @Column(name = "service_manager", nullable = true, length = 50)
    public String getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(String serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "fax", nullable = true, length = 50)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "messages", nullable = true, length = 200)
    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Basic
    @Column(name = "team_id", nullable = true, columnDefinition = "bigint")
    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    @Basic
    @Column(name = "stranded_max_time", nullable = true, columnDefinition = "bigint")
    public BigInteger getStrandedMaxTime() {
        return strandedMaxTime;
    }

    public void setStrandedMaxTime(BigInteger strandedMaxTime) {
        this.strandedMaxTime = strandedMaxTime;
    }

    @Basic
    @Column(name = "star_level", nullable = true)
    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    @Basic
    @Column(name = "picture", nullable = true, length = 500)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "SERVICE_Type", nullable = true)
    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "SERVICE_CONTENT", nullable = true, length = 400)
    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    @Basic
    @Column(name = "PARTS_CONTENT", nullable = true, length = 400)
    public String getPartsContent() {
        return partsContent;
    }

    public void setPartsContent(String partsContent) {
        this.partsContent = partsContent;
    }

    @Basic
    @Column(name = "service_radius", nullable = true)
    public Integer getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(Integer serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

    @Basic
    @Column(name = "service_code", nullable = true, length = 100)
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Basic
    @Column(name = "tool_content", nullable = true, length = 400)
    public String getToolContent() {
        return toolContent;
    }

    public void setToolContent(String toolContent) {
        this.toolContent = toolContent;
    }

    @Basic
    @Column(name = "station_type", nullable = true, length = 50)
    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    @Basic
    @Column(name = "creat_Date", nullable = true, columnDefinition = "bigint")
    public BigInteger getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(BigInteger creatDate) {
        this.creatDate = creatDate;
    }

    @Basic
    @Column(name = "account_id", nullable = true, columnDefinition = "bigint")
    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "fixed_telephone", nullable = true, length = 50)
    public String getFixedTelephone() {
        return fixedTelephone;
    }

    public void setFixedTelephone(String fixedTelephone) {
        this.fixedTelephone = fixedTelephone;
    }

    @Basic
    @Column(name = "del_flag", nullable = false)
    public Integer getDelFlag() {
        return delFlag;
    }


    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "is_core", nullable = false, columnDefinition = "bigint")
    public BigInteger getIsCore() {
        return isCore;
    }

    public void setIsCore(BigInteger isCore) {
        this.isCore = isCore;
    }
}
