package com.navinfo.opentsp.dongfeng.system.pojo;

import java.math.BigInteger;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-24
 * @modify
 * @copyright Navi Tsp
 */
public class StationPojo {
    private BigInteger id;
    private String stationName;//服务站名称
    private String stationShortName;//服务站简称
    private String stationCode;//服务站编码
    private String address;//服务站地址
    private BigInteger longitude;    //经度
    private BigInteger latitude;    //纬度
    private Integer starLever;    //服务站星级
    private Integer radius;//占地半径
    private Integer serviceRadius;//服务半径
    private BigInteger strandedMaxTime;//滞留超时阀值
    private String telephone;    //服务热线
    private String mobilePhone;    //服务手机
    private String serviceManager;    //服务经理
    private Integer serviceType;//服务类型
    private String serviceContent;//服务内容
    private String partsContent;//备件内容
    private String toolContent;//提升工具
    private BigInteger accountId;//创建人ID
    private String accountName;//创建人
    private BigInteger createDate;//创建时间
    private Integer province;//省编码
    private Integer city;//城市编码
    private String picture;//图片
    private BigInteger isCore;//是否是核心站
    private Integer switchStatus;//启用停用状态
    private String switchStatusStr;//启用停用状态
    private Integer tBossSyncStatus;//tboss同步状态
    private Integer tagSyncStatus;//标签库同步状态
    private String createDateStr;//创建时间字符串
    private String radiusStr;//占地半径(单位：km)
    private String serviceRadiusStr;//服务半径(单位：km)
    private String strandedMaxTimeStr;//滞留超时阀值(单位：小时分钟)
    private String andNet;//并网（1：轻卡 2：锡柴 3：雍柴 4：大柴）
    private String remark;//备注
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationShortName() {
        return stationShortName;
    }

    public void setStationShortName(String stationShortName) {
        this.stationShortName = stationShortName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getLongitude() {
        return longitude;
    }

    public void setLongitude(BigInteger longitude) {
        this.longitude = longitude;
    }

    public BigInteger getLatitude() {
        return latitude;
    }

    public void setLatitude(BigInteger latitude) {
        this.latitude = latitude;
    }

    public Integer getStarLever() {
        return starLever;
    }

    public void setStarLever(Integer starLever) {
        this.starLever = starLever;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(Integer serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

    public BigInteger getStrandedMaxTime() {
        return strandedMaxTime;
    }

    public void setStrandedMaxTime(BigInteger strandedMaxTime) {
        this.strandedMaxTime = strandedMaxTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(String serviceManager) {
        this.serviceManager = serviceManager;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getPartsContent() {
        return partsContent;
    }

    public void setPartsContent(String partsContent) {
        this.partsContent = partsContent;
    }

    public String getToolContent() {
        return toolContent;
    }

    public void setToolContent(String toolContent) {
        this.toolContent = toolContent;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigInteger getCreateDate() {
        return createDate;
    }

    public void setCreateDate(BigInteger createDate) {
        this.createDate = createDate;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigInteger getIsCore() {
        return isCore;
    }

    public void setIsCore(BigInteger isCore) {
        this.isCore = isCore;
    }

    public Integer getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getSwitchStatusStr() {
        return switchStatusStr;
    }

    public void setSwitchStatusStr(String switchStatusStr) {
        this.switchStatusStr = switchStatusStr;
    }

    public Integer gettBossSyncStatus() {
        return tBossSyncStatus;
    }

    public void settBossSyncStatus(Integer tBossSyncStatus) {
        this.tBossSyncStatus = tBossSyncStatus;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getRadiusStr() {
        return radiusStr;
    }

    public void setRadiusStr(String radiusStr) {
        this.radiusStr = radiusStr;
    }

    public String getServiceRadiusStr() {
        return serviceRadiusStr;
    }

    public void setServiceRadiusStr(String serviceRadiusStr) {
        this.serviceRadiusStr = serviceRadiusStr;
    }

    public String getStrandedMaxTimeStr() {
        return strandedMaxTimeStr;
    }

    public void setStrandedMaxTimeStr(String strandedMaxTimeStr) {
        this.strandedMaxTimeStr = strandedMaxTimeStr;
    }

    public Integer getTagSyncStatus() {
        return tagSyncStatus;
    }

    public void setTagSyncStatus(Integer tagSyncStatus) {
        this.tagSyncStatus = tagSyncStatus;
    }

    public String getAndNet() {
        return andNet;
    }

    public void setAndNet(String andNet) {
        this.andNet = andNet;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
