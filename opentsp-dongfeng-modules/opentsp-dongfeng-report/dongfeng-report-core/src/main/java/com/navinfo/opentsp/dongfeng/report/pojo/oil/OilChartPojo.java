package com.navinfo.opentsp.dongfeng.report.pojo.oil;

import com.navinfo.opentsp.dongfeng.common.util.tcp.DoubleUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * 油量图表数据
 */
public class OilChartPojo implements Serializable {

    private static final long serialVersionUID = -2658305354130272872L;
    //坐标参考量
    private Double maxMileage = 0d;
    private Double minMileage = 0d;
    private Double maxSpeed = 411d;//默认411km/h：目前车速最大值
    private Double maxOilCapacity = 200d;//默认200L
    private Double maxOilCon = 0d;
    private Double minOilCon = 0d;
    private Double maxAverageOil = 100d;//最大百公里油耗
    private Double minAverageOil = 0d;//最小百公里油耗

    /*
     * 以下三种分别存储各自的百分形式（percent）和正常数据（data）
     */
    private Map<String, List<Double>> mileages;//里程km
    private Map<String, List<Double>> speeds;//速度km/h
    private Map<String, List<Double>> oilCapacities;//油量L
    private Map<String, List<Double>> oilCons;//总油耗L
    private Map<String, List<Double>> averageOil;//百公里油耗
    private List<Long> time;//时间

    /*
     * List形式
     */
    private List<Double> mileagePercent;//里程km
    private List<Double> speedPercent;//速度km/h
    private List<Double> oilCapacityPercent;//油量L
    private List<Double> oilConPercent;//总油耗L
    private List<Double> averageOilPercent;//百公里平均油耗L/100KM
    private List<String> timeStr;//时间

    public OilChartPojo() {
        time = new ArrayList<Long>();
        timeStr = new ArrayList<String>();
        oilCapacityPercent = new ArrayList<Double>();
        oilConPercent = new ArrayList<Double>();
        speedPercent = new ArrayList<Double>();
        mileagePercent = new ArrayList<Double>();
        mileages = new HashMap<String, List<Double>>();
        mileages.put("percent", new ArrayList<Double>());
        mileages.put("data", new ArrayList<Double>());
        speeds = new HashMap<String, List<Double>>();
        speeds.put("percent", new ArrayList<Double>());
        speeds.put("data", new ArrayList<Double>());
        oilCapacities = new HashMap<String, List<Double>>();
        oilCapacities.put("percent", new ArrayList<Double>());
        oilCapacities.put("data", new ArrayList<Double>());
        oilCons = new HashMap<String, List<Double>>();
        oilCons.put("percent", new ArrayList<Double>());
        oilCons.put("data", new ArrayList<Double>());
        averageOil = new HashMap<String, List<Double>>();
        averageOil.put("percent", new ArrayList<Double>());
        averageOil.put("data", new ArrayList<Double>());
    }

    public Map<String, List<Double>> getAverageOil() {
        return averageOil;
    }

    public void setAverageOil(Map<String, List<Double>> averageOil) {
        this.averageOil = averageOil;
    }

    public List<Double> getAverageOilPercent() {
        return averageOilPercent;
    }

    public void setAverageOilPercent(List<Double> averageOilPercent) {
        this.averageOilPercent = averageOilPercent;
    }

    public Map<String, List<Double>> getOilCons() {
        return oilCons;
    }

    public List<Double> getOilConPercent() {
        return oilConPercent;
    }

    public void setOilConPercent(List<Double> oilConPercent) {
        this.oilConPercent = oilConPercent;
    }

    public List<Double> getMileagePercent() {
        return mileagePercent;
    }

    private void setMileagePercent(List<Double> mileagePercent) {
        this.mileagePercent = mileagePercent;
    }

    public List<Double> getSpeedPercent() {
        return speedPercent;
    }

    private void setSpeedPercent(List<Double> speedPercent) {
        this.speedPercent = speedPercent;
    }

    public List<Double> getOilCapacityPercent() {
        return oilCapacityPercent;
    }

    private void setOilCapacityPercent(List<Double> oilCapacityPercent) {
        this.oilCapacityPercent = oilCapacityPercent;
    }

    private Map<String, List<Double>> getMileages() {
        return mileages;
    }

    private Map<String, List<Double>> getSpeeds() {
        return speeds;
    }

    private Map<String, List<Double>> getOilCapacities() {
        return oilCapacities;
    }

    public void addOneTime(Long time) {
        this.time.add(time);
    }

    public void addOneMileages(Double data) {
        this.mileages.get("data").add(data);
    }

    public void addOneSpeeds(Double data) {
        this.speeds.get("data").add(data);
    }

    public void addOneOilCapacities(Double percent) {
        this.oilCapacities.get("percent").add(percent);
    }

    public void addOneOilCons(Double percent) {
        this.oilCons.get("data").add(percent);
    }

    public void addOneAverageOil(Double percent) {
        this.averageOil.get("data").add(percent);
    }

    public void setMaxOilCapacity(Double maxOilCapacity) {
        this.maxOilCapacity = maxOilCapacity;
    }

    public Double getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(Double maxMileage) {
        this.maxMileage = maxMileage;
    }

    public Double getMinMileage() {
        return minMileage;
    }

    public void setMinMileage(Double minMileage) {
        this.minMileage = minMileage;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getMaxOilCapacity() {
        return maxOilCapacity;
    }

    public Double getMaxOilCon() {
        return maxOilCon;
    }

    public void setMaxOilCon(Double maxOilCon) {
        this.maxOilCon = maxOilCon;
    }

    public List<String> getTimeStr() {
        return this.timeStr;
    }

    public Double getMinOilCon() {
        return minOilCon;
    }

    public void setMinOilCon(Double minOilCon) {
        this.minOilCon = minOilCon;
    }

    public Double getMinAverageOil() {
        return minAverageOil;
    }

    public void setMinAverageOil(Double minAverageOil) {
        this.minAverageOil = minAverageOil;
    }

    public Double getMaxAverageOil() {
        return maxAverageOil;
    }

    public void setMaxAverageOil(Double maxAverageOil) {
        this.maxAverageOil = maxAverageOil;
    }

    public void fullFillList() {
        //时间
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        for (Long t : time) {
            String str = df.format(new Date(t));
            timeStr.add(str);
        }

        //速度
        fullFill(0D, this.maxSpeed, this.getSpeeds().get("percent"), this.getSpeeds().get("data"));
//		this.setSpeedPercent(this.getSpeeds().get("percent"));
        this.setSpeedPercent(this.getSpeeds().get("data"));

        //油量
        if (this.maxOilCapacity != null && this.maxOilCapacity.doubleValue() != 0.0d) {
            fullFill(0D, this.maxOilCapacity, this.getOilCapacities().get("percent"), this.getOilCapacities().get("data"));
            this.setOilCapacityPercent(this.getOilCapacities().get("data"));
        } else {
            this.setOilCapacityPercent(this.getOilCapacities().get("percent"));
            this.maxOilCapacity = 0.0d;
        }
//		this.setOilCapacityPercent(this.getOilCapacities().get("percent"));

        //里程
        for (Double temp : this.getMileages().get("data")) {
            if (temp != null && temp.intValue() != 0) {
                if (this.minMileage.intValue() == 0) {
                    this.minMileage = temp;
                }
                if (this.minMileage > temp) {
                    this.minMileage = temp;
                }
                if (this.maxMileage < temp) {
                    this.maxMileage = temp;
                }
            }
        }
        fullFill(this.minMileage, this.maxMileage, this.getMileages().get("percent"), this.getMileages().get("data"));
//		this.setMileagePercent(this.getMileages().get("percent"));
        this.setMileagePercent(this.getMileages().get("data"));

        //总消耗d
        for (Double temp : this.getOilCons().get("data")) {
            if (temp != null && temp.intValue() != 0) {
                if (this.minOilCon.intValue() == 0) {
                    this.minOilCon = temp;
                }
                if (this.minOilCon > temp) {
                    this.minOilCon = temp;
                }
                if (this.maxOilCon < temp) {
                    this.maxOilCon = temp;
                }
            }
        }
        fullFill(this.minOilCon, this.maxOilCon, this.getOilCons().get("percent"), this.getOilCons().get("data"));
        this.setOilConPercent(this.getOilCons().get("data"));
        //百公里油耗
//		if (this.getAverageOil().get("data").size() == 0) {
//			this.minAverageOil = 0.0;
//			this.maxAverageOil = 0.0;
//		}
        fullFill(this.minAverageOil, this.maxAverageOil, this.getAverageOil().get("percent"), this.getAverageOil().get("data"));
        this.setAverageOilPercent(this.getAverageOil().get("data"));
    }

    private void fullFill(Double min, Double max, List<Double> percentList, List<Double> dataList) {
        if (percentList.size() == 0 && dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Double data = dataList.get(i);
                if (data == null) {
                    percentList.add(0.0D);
                    dataList.set(i, 0.0D);
                } else {
                    dataList.set(i, Double.parseDouble(DoubleUtils.getDoubleData(data)));
                    Double dd = (data - min) / (max - min);
                    if (data == min || max == min || dd.isInfinite() || dd.isNaN()) {
                        percentList.add(0.0D);
                    } else {
                        Double d = Double.parseDouble(DoubleUtils.getDoubleData((data - min) / (max - min)));
                        if (d.isInfinite() || d.isNaN()) {
                            percentList.add(0.0D);
                        } else {
                            percentList.add(d);
                        }
                    }

                }
            }
        } else if (dataList.size() == 0 && percentList.size() > 0) {
            for (int i = 0; i < percentList.size(); i++) {
                Double percent = percentList.get(i);
                if (percent == null) {
                    dataList.add(0.0D);
                    percentList.set(i, 0.0D);
                } else {
                    dataList.add(Double.parseDouble(DoubleUtils.getDoubleData(percent * (max - min) + min)));
                }
            }
        } else if (dataList.size() > 0 && percentList.size() > 0 && dataList.size() == percentList.size()) {
        } else {
            dataList.clear();
            percentList.clear();
        }
    }

}
