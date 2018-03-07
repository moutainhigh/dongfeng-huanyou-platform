package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station;

import com.navinfo.opentsp.dongfeng.common.validation.NotEqualZero;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 服务站基本信息
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class StationBasicInfoIndto {
    @NotNull(message = "服务站名称 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站名称 不能为空", groups = {GroupCommand.class})
    private String stationName;//服务站名称
    @NotNull(message = "服务站简称 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站简称 不能为空", groups = {GroupCommand.class})
    private String stationShortName;//服务站简称
    @NotNull(message = "服务站编码 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站编码 不能为空", groups = {GroupCommand.class})
    @Length(max = 20, message = "服务站编码 最大支持长度为20个字符", groups = {GroupCommand.class})
    private String stationCode;//服务站编码
    @NotNull(message = "服务站地址 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站地址 不能为空", groups = {GroupCommand.class})
    private String address;//服务站地址
    @NotNull(message = "服务站纬度 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站纬度 不能为NULL", groups = {GroupCommand.class})
    @NotEqualZero(message = "服务站纬度不能为0", groups = {GroupCommand.class})
    private String longitude;    //经度
    @NotNull(message = "服务站纬度 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务站纬度 不能为NULL", groups = {GroupCommand.class})
    @NotEqualZero(message = "服务站纬度不能为0", groups = {GroupCommand.class})
    private String latitude;    //纬度
    private String starLever;    //服务站星级
    @NotNull(message = "占地半径 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "占地半径 不能为NULL", groups = {GroupCommand.class})
    private String radius;//占地半径
    @NotNull(message = "服务半径 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务半径 不能为NULL", groups = {GroupCommand.class})
    private String serviceRadius;//服务半径
    @NotNull(message = "滞留超时阀值 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "滞留超时阀值 不能为NULL", groups = {GroupCommand.class})
    private String strandedMaxTime;//滞留超时阀值
    @NotNull(message = "服务热线 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务热线 不能为NULL", groups = {GroupCommand.class})
    private String telephone;    //服务热线
    @NotNull(message = "服务手机 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务手机 不能为NULL", groups = {GroupCommand.class})
    private String mobilePhone;    //服务手机
    private String serviceManager;    //服务经理
    @NotNull(message = "省编码 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "省编码 不能为NULL", groups = {GroupCommand.class})
    @NotEqualZero(message = "省编码不能为0", groups = {GroupCommand.class})
    private String province;//省编码
    @NotNull(message = "城市编码 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "城市编码 不能为NULL", groups = {GroupCommand.class})
    @NotEqualZero(message = "城市编码不能为0", groups = {GroupCommand.class})
    private String city;//城市编码
    private String picture;//图片
    @NotNull(message = "服务等级 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "服务等级 不能为NULL", groups = {GroupCommand.class})
    private String isCore;//是否是核心站点(服务等级：0-一般 1-核心 2-优先 3-不推荐)
    private String andNet;//并网（1：轻卡 2：锡柴 3：雍柴 4：大柴）
    private String remark;//备注
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStarLever() {
        return starLever;
    }

    public void setStarLever(String starLever) {
        this.starLever = starLever;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(String serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

    public String getStrandedMaxTime() {
        return strandedMaxTime;
    }

    public void setStrandedMaxTime(String strandedMaxTime) {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIsCore() {
        return isCore;
    }

    public void setIsCore(String isCore) {
        this.isCore = isCore;
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
