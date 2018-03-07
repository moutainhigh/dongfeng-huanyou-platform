package com.navinfo.dongfeng.terminal.comm.model.cache;
/**
 * 位置数据相关数据缓存
 *
 * @wenya
 * @create 2017-03-15 15:32
 **/
public class GpsInfoData {
    private Integer carStatue;//在线状态
    private Double tempMileage;//tip总里程
    private Double tempOil;//tip剩余油量

    public Integer getCarStatue() {
        return carStatue;
    }

    public void setCarStatue(Integer carStatue) {
        this.carStatue = carStatue;
    }

    public Double getTempMileage() {
        return tempMileage;
    }

    public void setTempMileage(Double tempMileage) {
        this.tempMileage = tempMileage;
    }

    public Double getTempOil() {
        return tempOil;
    }

    public void setTempOil(Double tempOil) {
        this.tempOil = tempOil;
    }
}
