package com.navinfo.opentsp.dongfeng.monitor.dto.car;
/**
 * 地图打点
 *
 * @wenya
 * @create 2017-03-09 16:03
 **/
public class QueryCarLocDto {
    //车辆主键
    private Long id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //车辆在线状态
    private Integer carStauts;
    //经度
    private Double lng;
    //纬度
    private Double lat;
    //上报时间
    private String gpstime;
    //方向
    private Integer direction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getGpstime() {
        return gpstime;
    }

    public void setGpstime(String gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
