package com.navinfo.opentsp.dongfeng.monitor.pojo.station;

import java.math.BigInteger;

/**
 * 查询服务站详情POJO
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */

public class QueryStationDetailPojo {

    private BigInteger id;//服务站ID

    private String stationName;//服务站名称

    private String adderss;//地址

    private BigInteger longitude;//经度

    private BigInteger latitude;//纬度

    private Integer radius;//占地半径

    private String phone;//电话

    private String serviceManager;//联系人

    private String serviceContent;//服务内容

    private String partsContent;//配件明细

    private Integer starLever;//星级

    private String picture;//图片地址

    private Integer serviceRadius;//服务半径

    private BigInteger commentCounts;//用户评论总数

    private BigInteger starLeverCounts;//服务总星级

    private String stationIds;//用户服务站权限ID

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

    public String getAdderss() {
        return adderss;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
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

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getPartsContent() {
        return partsContent;
    }

    public void setPartsContent(String partsContent) {
        this.partsContent = partsContent;
    }

    public Integer getStarLever() {
        return starLever;
    }

    public void setStarLever(Integer starLever) {
        this.starLever = starLever;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(Integer serviceRadius) {
        this.serviceRadius = serviceRadius;
    }

    public BigInteger getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(BigInteger commentCounts) {
        this.commentCounts = commentCounts;
    }

    public BigInteger getStarLeverCounts() {
        return starLeverCounts;
    }

    public void setStarLeverCounts(BigInteger starLeverCounts) {
        this.starLeverCounts = starLeverCounts;
    }

    public String getStationIds() {
        return stationIds;
    }

    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }
}