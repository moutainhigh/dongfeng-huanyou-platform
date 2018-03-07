package com.navinfo.opentsp.dongfeng.common.result;

/**
 * 聚合输出结果
 * Created by songzb on 2016/7/6.
 */
public class PolymerizeResult {

    // 纬度坐标
    private Double latitude;
    // 经度坐标
    private Double longitude;
    // 聚合数量
    private Integer count;
    //聚合值为1时返回车牌号
    private String carNo;
    //车辆状态。0：离线；1：停止；2：行驶中
    private Integer travelStatus;

    private Integer direction;

    private String carId;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }
}
