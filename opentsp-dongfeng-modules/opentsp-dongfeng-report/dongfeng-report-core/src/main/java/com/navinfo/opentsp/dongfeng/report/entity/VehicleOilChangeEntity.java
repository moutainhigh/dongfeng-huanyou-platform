package com.navinfo.opentsp.dongfeng.report.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cj on 2017/9/30.
 */
public class VehicleOilChangeEntity {

    private long vid; //终端标识 索引
    private long beginDate; //事件开始时间
    private long beginLongitude; //开始经度
    private long beginLatitude; //开始纬度
    private long beginGpsSpeed; //开始速度
    private long beginStealOil; //事件变化开始油的数量（L），乘以100
    private long beginMileage; //开始里程

    private long endDate; //事件结束时间
    private long endLongitude; //结束经度
    private long endLatitude; //结束纬度
    private long endGpsSpeed; //结束速度
    private long endStealOil; //事件变化开始油的数量（L），乘以100
    private long endMileage; //结束里程

    private long oilingDvalue; //加油减油差值（L），乘以100
    private int oilType; //枚举类：1加油 2减油
    private String createTime; //数据创建时间yyyy-mm-dd hh:mm:ss
    private long changeTime;  //油量变化持续时长（秒）

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

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

    public long getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(long beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public long getBeginLatitude() {
        return beginLatitude;
    }

    public void setBeginLatitude(long beginLatitude) {
        this.beginLatitude = beginLatitude;
    }

    public long getBeginGpsSpeed() {
        return beginGpsSpeed;
    }

    public void setBeginGpsSpeed(long beginGpsSpeed) {
        this.beginGpsSpeed = beginGpsSpeed;
    }

    public long getBeginStealOil() {
        return beginStealOil;
    }

    public void setBeginStealOil(long beginStealOil) {
        this.beginStealOil = beginStealOil;
    }

    public long getBeginMileage() {
        return beginMileage;
    }

    public void setBeginMileage(long beginMileage) {
        this.beginMileage = beginMileage;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(long endLongitude) {
        this.endLongitude = endLongitude;
    }

    public long getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(long endLatitude) {
        this.endLatitude = endLatitude;
    }

    public long getEndGpsSpeed() {
        return endGpsSpeed;
    }

    public void setEndGpsSpeed(long endGpsSpeed) {
        this.endGpsSpeed = endGpsSpeed;
    }

    public long getEndStealOil() {
        return endStealOil;
    }

    public void setEndStealOil(long endStealOil) {
        this.endStealOil = endStealOil;
    }

    public long getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(long endMileage) {
        this.endMileage = endMileage;
    }

    public long getOilingDvalue() {
        return oilingDvalue;
    }

    public void setOilingDvalue(long oilingDvalue) {
        this.oilingDvalue = oilingDvalue;
    }

    public int getOilType() {
        return oilType;
    }

    public void setOilType(int oilType) {
        this.oilType = oilType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VehicleOilChangeEntity{" +
                "vid=" + vid +
                ", beginDate='" + beginDate + '\'' +
                ", beginLongitude=" + beginLongitude +
                ", beginLatitude=" + beginLatitude +
                ", beginGpsSpeed=" + beginGpsSpeed +
                ", beginStealOil=" + beginStealOil +
                ", beginMileage=" + beginMileage +
                ", endDate='" + endDate + '\'' +
                ", endLongitude=" + endLongitude +
                ", endLatitude=" + endLatitude +
                ", endGpsSpeed=" + endGpsSpeed +
                ", endStealOil=" + endStealOil +
                ", endMileage=" + endMileage +
                ", oilingDvalue=" + oilingDvalue +
                ", oilType=" + oilType +
                ", createTime=" + createTime +
                '}';
    }

    public static enum ChangeType {
        addOil(1, "加油"),
        reduceOil(2, "减油"),
        ;
        private static Map<Integer, ChangeType> changeTypeMap = new HashMap();
        static{
            for(ChangeType changeType : values()){
                changeTypeMap.put(changeType.code, changeType);
            }
        }

        public int code;
        public String desc;

        private ChangeType(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public ChangeType getDataTypeByCode(int code){
            return changeTypeMap.get(code);
        }
    }


}
