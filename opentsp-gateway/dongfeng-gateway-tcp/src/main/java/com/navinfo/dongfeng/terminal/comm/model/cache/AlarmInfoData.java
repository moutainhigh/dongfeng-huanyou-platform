package com.navinfo.dongfeng.terminal.comm.model.cache;
/**
 * 报警提醒对象
 *
 * @wenya
 * @create 2017-03-16 10:06
 **/
public class AlarmInfoData {
    private String sim;         //通信号
    private Long alarmDate;     //报警时间
    private String alarmDateStr;     //报警时间str
    private Long alarmtimes;    //报警持续时间
    private double latitudes;   //报警纬度
    private double longitudes;  //报警经度
    private Integer alarmType;      //报警类型(1:出区域限速 2:滞留超时3:一体机拆出报警4:车辆锁车报警5:ID不匹配报警)
    private String alarmTypeStr;
    private String oldTerminalid; //旧终端id
    private String newTerminalid; //新终端id
    private Integer alarmOverType; //报警结束标志 （0：未结束  1：结束）
    private Double distance;//距离
    private String lockCarReason; //锁车原因
    private String lockCar;// 解锁车

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Long getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Long alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmDateStr() {
        return alarmDateStr;
    }

    public void setAlarmDateStr(String alarmDateStr) {
        this.alarmDateStr = alarmDateStr;
    }

    public Long getAlarmtimes() {
        return alarmtimes;
    }

    public void setAlarmtimes(Long alarmtimes) {
        this.alarmtimes = alarmtimes;
    }

    public double getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(double latitudes) {
        this.latitudes = latitudes;
    }

    public double getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(double longitudes) {
        this.longitudes = longitudes;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTypeStr() {
        return alarmTypeStr;
    }

    public void setAlarmTypeStr(String alarmTypeStr) {
        this.alarmTypeStr = alarmTypeStr;
    }

    public String getOldTerminalid() {
        return oldTerminalid;
    }

    public void setOldTerminalid(String oldTerminalid) {
        this.oldTerminalid = oldTerminalid;
    }

    public String getNewTerminalid() {
        return newTerminalid;
    }

    public void setNewTerminalid(String newTerminalid) {
        this.newTerminalid = newTerminalid;
    }

    public Integer getAlarmOverType() {
        return alarmOverType;
    }

    public void setAlarmOverType(Integer alarmOverType) {
        this.alarmOverType = alarmOverType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getLockCarReason() {
        return lockCarReason;
    }

    public void setLockCarReason(String lockCarReason) {
        this.lockCarReason = lockCarReason;
    }

    public String getLockCar() {
        return lockCar;
    }

    public void setLockCar(String lockCar) {
        this.lockCar = lockCar;
    }
}
