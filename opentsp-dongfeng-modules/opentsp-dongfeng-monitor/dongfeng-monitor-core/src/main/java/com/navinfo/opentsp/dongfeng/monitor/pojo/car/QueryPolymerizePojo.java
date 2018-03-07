package com.navinfo.opentsp.dongfeng.monitor.pojo.car;

import java.math.BigInteger;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/23
 */
public class QueryPolymerizePojo {
    //车辆主键
    private BigInteger id;
    //底盘号
    private String chassisNum;
    //车牌号
    private String plateNum;
    //通信号
    private String commId;
    //车辆在线状态
    private Integer carStauts;
    //经度
    private Double lng;
    //纬度
    private Double lat;
    //上报时间
    private Long gpstime;
    //方向
    private Integer direction;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
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

    public Long getGpstime() {
        return gpstime;
    }

    public void setGpstime(Long gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
