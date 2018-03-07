package com.navinfo.opentsp.dongfeng.report.dto.general;

import java.io.Serializable;

/**
 * 异常油耗数据
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-29
 */
public class StealOilDto implements Serializable, Comparable {
    private static final long serialVersionUID = -1539479320644134492L;
    /**
     * 终端标识
     */
    private Long commId;
    /**
     * 事件开始时间
     */
    private Long beginDate;
    /**
     * 事件开始点纬度
     */
    private Long beginLongitude;
    /**
     * 事件开始点经度
     */
    private Long beginLatitude;
    /**
     * 开始GPS速度
     */
    private Long beginGpsSpeed;
    /**
     * 事件变化前油的数量（L）
     */
    private Float beginOil;
    /**
     * 开始里程
     */
    private Long beginMileage;
    /**
     * 事件开始点详细位置
     */
    private String detailPosition;
    /**
     * 事件结束时间
     */
    private Long endDate;
    /**
     * 事件结束点纬度
     */
    private Long endLongitude;
    /**
     * 事件结束点经度
     */
    private Long endLatitude;
    /**
     * 结束GPS速度
     */
    private Long endGpsSpeed;
    /**
     * 变化后的油数量（L）
     */
    private Float endOil;
    /**
     * 结束里程
     */
    private Long endMileage;
    /**
     * 加油减油差值（L）
     */
    private Float oilingDvalue;
    /**
     * 1加油 2 减油
     */
    private Integer oilType;
    /**
     * 持续时长
     */
    private Long changeTime;

    public Long getCommId() {
        return commId;
    }

    public void setCommId(Long commId) {
        this.commId = commId;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(Long beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public Long getBeginLatitude() {
        return beginLatitude;
    }

    public void setBeginLatitude(Long beginLatitude) {
        this.beginLatitude = beginLatitude;
    }

    public Long getBeginGpsSpeed() {
        return beginGpsSpeed;
    }

    public void setBeginGpsSpeed(Long beginGpsSpeed) {
        this.beginGpsSpeed = beginGpsSpeed;
    }

    public Float getBeginOil() {
        return beginOil;
    }

    public void setBeginOil(Float beginOil) {
        this.beginOil = beginOil;
    }

    public Long getBeginMileage() {
        return beginMileage;
    }

    public void setBeginMileage(Long beginMileage) {
        this.beginMileage = beginMileage;
    }

    public String getDetailPosition() {
        return detailPosition;
    }

    public void setDetailPosition(String detailPosition) {
        this.detailPosition = detailPosition;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Long endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Long getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Long endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Long getEndGpsSpeed() {
        return endGpsSpeed;
    }

    public void setEndGpsSpeed(Long endGpsSpeed) {
        this.endGpsSpeed = endGpsSpeed;
    }

    public Float getEndOil() {
        return endOil;
    }

    public void setEndOil(Float endOil) {
        this.endOil = endOil;
    }

    public Long getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(Long endMileage) {
        this.endMileage = endMileage;
    }

    public Float getOilingDvalue() {
        return oilingDvalue;
    }

    public void setOilingDvalue(Float oilingDvalue) {
        this.oilingDvalue = oilingDvalue;
    }

    public Integer getOilType() {
        return oilType;
    }

    public void setOilType(Integer oilType) {
        this.oilType = oilType;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }

    @Override
    public int compareTo(Object o) {
        StealOilDto oilDto = (StealOilDto) o;
        return this.beginDate.compareTo(oilDto.getBeginDate());
    }

    @Override
    public String toString() {
        return "StealOilDto{" +
                "vid=" + commId +
                ", beginDate=" + beginDate +
                ", beginLongitude=" + beginLongitude +
                ", beginLatitude=" + beginLatitude +
                ", beginGpsSpeed=" + beginGpsSpeed +
                ", beginOil=" + beginOil +
                ", beginMileage=" + beginMileage +
                ", detailPosition='" + detailPosition + '\'' +
                ", endDate=" + endDate +
                ", endLongitude=" + endLongitude +
                ", endLatitude=" + endLatitude +
                ", endGpsSpeed=" + endGpsSpeed +
                ", endOil=" + endOil +
                ", endMileage=" + endMileage +
                ", oilingDvalue=" + oilingDvalue +
                ", oilType=" + oilType +
                ", changeTime=" + changeTime +
                '}';
    }
}
