package com.navinfo.opentsp.dongfeng.report.pojo.general;

/**
 * 异常油耗数据
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-29
 **/
public class StealOilPojo {
    /**
     * 终端标识
     */
    private Long vid;
    /**
     * 事件开始时间
     */
    private Long beginDate;
    /**
     * 事件开始点纬度
     */
    private Long beginLatitude;
    /**
     * 事件开始点经度
     */
    private Long endLatitude;
    /**
     * 事件开始点纬度
     */
    private Long endLongitude;
    /**
     * 事件开始点经度
     */
    private Long beginLongitude;
    /**
     * 事件结束时间
     */
    private Long endDate;
    /**
     * 事件开始点详细位置
     */
    private String detailPosition;
    /**
     * 事件变化前油的数量（L）
     */
    private Float beginStealOil;
    /**
     * 变化后的油数量（L）
     */
    private Float endStealOil;
    /**
     * 加油减油差值（L）
     */
    private Float oilingDvalue;
    /**
     * 开始GPS速度
     */
    private Long beginGpsSpeed;
    /**
     * 结束GPS速度
     */
    private Long endGpsSpeed;
    /**
     * 1加油 2 减油
     */
    private Integer oilType;
    /**
     * 开始里程
     */
    private Long beginMileage;
    /**
     * 结束里程
     */
    private Long endMileage;
    /**
     * 持续时长
     */
    private Long changeTime;

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getBeginLatitude() {
        return beginLatitude;
    }

    public void setBeginLatitude(Long beginLatitude) {
        this.beginLatitude = beginLatitude;
    }

    public Long getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Long endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Long getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Long endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Long getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(Long beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getDetailPosition() {
        return detailPosition;
    }

    public void setDetailPosition(String detailPosition) {
        this.detailPosition = detailPosition;
    }

    public Float getBeginStealOil() {
        return beginStealOil;
    }

    public void setBeginStealOil(Float beginStealOil) {
        this.beginStealOil = beginStealOil;
    }

    public Float getEndStealOil() {
        return endStealOil;
    }

    public void setEndStealOil(Float endStealOil) {
        this.endStealOil = endStealOil;
    }

    public Float getOilingDvalue() {
        return oilingDvalue;
    }

    public void setOilingDvalue(Float oilingDvalue) {
        this.oilingDvalue = oilingDvalue;
    }

    public Long getBeginGpsSpeed() {
        return beginGpsSpeed;
    }

    public void setBeginGpsSpeed(Long beginGpsSpeed) {
        this.beginGpsSpeed = beginGpsSpeed;
    }

    public Long getEndGpsSpeed() {
        return endGpsSpeed;
    }

    public void setEndGpsSpeed(Long endGpsSpeed) {
        this.endGpsSpeed = endGpsSpeed;
    }

    public Integer getOilType() {
        return oilType;
    }

    public void setOilType(Integer oilType) {
        this.oilType = oilType;
    }

    public Long getBeginMileage() {
        return beginMileage;
    }

    public void setBeginMileage(Long beginMileage) {
        this.beginMileage = beginMileage;
    }

    public Long getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(Long endMileage) {
        this.endMileage = endMileage;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }
}
