package com.navinfo.opentsp.dongfeng.report.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyue on 2017/7/11.
 * 加油减油返回
 */
public class OilingStealOilDataBean implements Serializable {
    private long vid;//终端号
    private long beginDate;//事件开始时间
    private long beginLatitude;//事件开始点纬度
    private long beginLongitude;//事件开始点经度
    private long endLatitude;//事件结束点纬度
    private long endLongitude;//事件结束点经度
    private long endDate;//事件结束时间
    private String detailPosition;//事件开始点详细位置
    private float beginStealOil;//事件变化前油的数量（L）
    private float endStealOil;//变化后的油数量（L）
    private float oilingDvalue;//加油减油差值（L）
    private long beginGpsSpeed;//开始GPS速度
    private long endGpsSpeed;//结束GPS速度
    private long beginMileage;//开始里程
    private long endMileage;//结束里程
    private int oilType;//加油减油
    private long changeTime;  //油量变化持续时长（秒）
    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getBeginLatitude() {
        return beginLatitude;
    }

    public void setBeginLatitude(long beginLatitude) {
        this.beginLatitude = beginLatitude;
    }

    public long getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(long beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public long getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(long endLatitude) {
        this.endLatitude = endLatitude;
    }

    public long getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(long endLongitude) {
        this.endLongitude = endLongitude;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getDetailPosition() {
        return detailPosition;
    }

    public void setDetailPosition(String detailPosition) {
        this.detailPosition = detailPosition;
    }

    public float getBeginStealOil() {
        return beginStealOil;
    }

    public void setBeginStealOil(float beginStealOil) {
        this.beginStealOil = beginStealOil;
    }

    public float getEndStealOil() {
        return endStealOil;
    }

    public void setEndStealOil(float endStealOil) {
        this.endStealOil = endStealOil;
    }

    public float getOilingDvalue() {
        return oilingDvalue;
    }

    public void setOilingDvalue(float oilingDvalue) {
        this.oilingDvalue = oilingDvalue;
    }

    public long getBeginGpsSpeed() {
        return beginGpsSpeed;
    }

    public void setBeginGpsSpeed(long beginGpsSpeed) {
        this.beginGpsSpeed = beginGpsSpeed;
    }

    public long getEndGpsSpeed() {
        return endGpsSpeed;
    }

    public void setEndGpsSpeed(long endGpsSpeed) {
        this.endGpsSpeed = endGpsSpeed;
    }

    public int getOilType() {
        return oilType;
    }

    public void setOilType(int oilType) {
        this.oilType = oilType;
    }

    public long getBeginMileage() {
        return beginMileage;
    }

    public void setBeginMileage(long beginMileage) {
        this.beginMileage = beginMileage;
    }

    public long getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(long endMileage) {
        this.endMileage = endMileage;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }
}
