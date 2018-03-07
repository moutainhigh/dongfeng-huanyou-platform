package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 位置数据采集汇报参数设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class GpsParam extends BaseTerminalParam {
    private Integer reportStrategy;// 位置汇报策略 0:定时 1:定距 2：定时定距
    private Integer reportPlan; // 位置汇报方案 0:Acc状态 1:登陆和Acc状态
    private Integer defaultTime; // 缺省时间间隔
    private Integer sleepingTime;// 休眠时间间隔
    private Integer notLoginTime; // 未登录时间间隔
    private Integer urgentTime;// 紧急报警时间间隔
    private Integer defaultDistance; // 缺省距离间隔
    private Integer notLoginDistance;// 未登录距离间隔
    private Integer sleepingDistance; // 休眠距离间隔
    private Integer urgentDistance;// 紧急报警距离间隔
    private Integer inflectionAngle; // 拐点补偿设置(<180度)
    private Integer illegalDrivingTime; // 违规驾驶行为汇报间隔

    public Integer getReportStrategy() {
        return reportStrategy;
    }

    public void setReportStrategy(Integer reportStrategy) {
        this.reportStrategy = reportStrategy;
    }

    public Integer getReportPlan() {
        return reportPlan;
    }

    public void setReportPlan(Integer reportPlan) {
        this.reportPlan = reportPlan;
    }

    public Integer getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(Integer defaultTime) {
        this.defaultTime = defaultTime;
    }

    public Integer getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(Integer sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public Integer getNotLoginTime() {
        return notLoginTime;
    }

    public void setNotLoginTime(Integer notLoginTime) {
        this.notLoginTime = notLoginTime;
    }

    public Integer getUrgentTime() {
        return urgentTime;
    }

    public void setUrgentTime(Integer urgentTime) {
        this.urgentTime = urgentTime;
    }

    public Integer getDefaultDistance() {
        return defaultDistance;
    }

    public void setDefaultDistance(Integer defaultDistance) {
        this.defaultDistance = defaultDistance;
    }

    public Integer getNotLoginDistance() {
        return notLoginDistance;
    }

    public void setNotLoginDistance(Integer notLoginDistance) {
        this.notLoginDistance = notLoginDistance;
    }

    public Integer getSleepingDistance() {
        return sleepingDistance;
    }

    public void setSleepingDistance(Integer sleepingDistance) {
        this.sleepingDistance = sleepingDistance;
    }

    public Integer getUrgentDistance() {
        return urgentDistance;
    }

    public void setUrgentDistance(Integer urgentDistance) {
        this.urgentDistance = urgentDistance;
    }

    public Integer getInflectionAngle() {
        return inflectionAngle;
    }

    public void setInflectionAngle(Integer inflectionAngle) {
        this.inflectionAngle = inflectionAngle;
    }

    public Integer getIllegalDrivingTime() {
        return illegalDrivingTime;
    }

    public void setIllegalDrivingTime(Integer illegalDrivingTime) {
        this.illegalDrivingTime = illegalDrivingTime;
    }
}
