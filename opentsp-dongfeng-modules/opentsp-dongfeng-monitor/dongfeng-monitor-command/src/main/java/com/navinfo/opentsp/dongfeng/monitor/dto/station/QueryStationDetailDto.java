package com.navinfo.opentsp.dongfeng.monitor.dto.station;

/**
 * 查询服务站详情DTO
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */

public class QueryStationDetailDto {

    //服务站名称
    private String stationName;
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
    //服务内容
    private String serviceContent;
    //配件内容
    private String partsContent;
    //图片地址
    private String picture;
    //radius 占地半径
    private Integer radius;
    //serviceRadius 服务半径
    private Integer serviceRadius;
    //联系电话
    private String phone;
    //联系人
    private String serviceManager;
    //服务站星级
    private Integer starLever;
    //用户评论总数
    private Integer commentCounts;
    //服务总星级(用户评价)
    private Integer starLeverCounts;
    //服务站地址
    private String adderss;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public Integer getStarLever() {
        return starLever;
    }

    public void setStarLever(Integer starLever) {
        this.starLever = starLever;
    }

    public Integer getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Integer commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Integer getStarLeverCounts() {
        return starLeverCounts;
    }

    public void setStarLeverCounts(Integer starLeverCounts) {
        this.starLeverCounts = starLeverCounts;
    }

    public String getAdderss() {
        return adderss;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }
}