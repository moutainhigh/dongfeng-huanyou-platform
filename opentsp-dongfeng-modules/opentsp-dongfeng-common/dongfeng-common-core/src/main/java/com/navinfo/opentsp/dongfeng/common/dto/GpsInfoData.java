package com.navinfo.opentsp.dongfeng.common.dto;

/**
 * 位置数据相关数据缓存
 *
 * @wenya
 * @create 2017-03-15 15:32
 **/
public class GpsInfoData
{
    private Integer carStatue;// 在线状态
    
    private Double tempMileage;// tip总里程
    
    private Double tempOil;// tip剩余油量
    
    private Double tempRunTime; // 运行时间
    
    private Double tempOilCons; // 总油耗
    
    private Integer lng;
    
    private Integer lat;
    
    private Long gpsDate; // 最新的GPS时间

    private double resGas;//剩余汽量
    
    public Integer getCarStatue()
    {
        if (carStatue == null)
        {
            return 0;
        }
        return carStatue;
    }
    
    public void setCarStatue(Integer carStatue)
    {
        this.carStatue = carStatue;
    }
    
    public Double getTempMileage()
    {
        if (tempMileage == null)
        {
            return 0d;
        }
        return tempMileage;
    }
    
    public void setTempMileage(Double tempMileage)
    {
        this.tempMileage = tempMileage;
    }
    
    public Double getTempOil()
    {
        return tempOil;
    }
    
    public void setTempOil(Double tempOil)
    {
        this.tempOil = tempOil;
    }
    
    public Double getTempOilCons()
    {
        return tempOilCons;
    }
    
    public void setTempOilCons(Double tempOilCons)
    {
        this.tempOilCons = tempOilCons;
    }
    
    public Double getTempRunTime()
    {
        return tempRunTime;
    }
    
    public void setTempRunTime(Double tempRunTime)
    {
        this.tempRunTime = tempRunTime;
    }
    
    public Integer getLng()
    {
        return lng;
    }
    
    public void setLng(Integer lng)
    {
        this.lng = lng;
    }
    
    public Integer getLat()
    {
        return lat;
    }
    
    public Long getGpsDate()
    {
        return gpsDate;
    }
    
    public void setGpsDate(Long gpsDate)
    {
        this.gpsDate = gpsDate;
    }
    
    public void setLat(Integer lat)
    {
        this.lat = lat;
    }

    public double getResGas() {
        return resGas;
    }

    public void setResGas(double resGas) {
        this.resGas = resGas;
    }
}
