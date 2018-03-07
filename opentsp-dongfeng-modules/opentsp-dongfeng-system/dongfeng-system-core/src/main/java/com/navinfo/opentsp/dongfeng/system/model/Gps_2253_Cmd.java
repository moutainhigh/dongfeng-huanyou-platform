package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 位置数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2253_Cmd extends BaseTcpCommand {
    private static final long serialVersionUID = 9056459605324421504L;
    private int bitField0;
    private int tactics;
    private int program;
    private int notLoginTime;
    private int sleepingTime;
    private int urgentTime;
    private int defaultTime;
    private int defaultDistance;
    private int notLoginDistance;
    private int sleepingDistance;
    private int urgentDistance;
    private int inflectionAngle;
    private int fenceRadius;
    private int illegalDrivingTime;

    public int getBitField0() {
        return bitField0;
    }

    public void setBitField0(int bitField0) {
        this.bitField0 = bitField0;
    }

    public int getTactics() {
        return tactics;
    }

    public void setTactics(int tactics) {
        this.tactics = tactics;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public int getNotLoginTime() {
        return notLoginTime;
    }

    public void setNotLoginTime(int notLoginTime) {
        this.notLoginTime = notLoginTime;
    }

    public int getSleepingTime() {
        return sleepingTime;
    }

    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public int getUrgentTime() {
        return urgentTime;
    }

    public void setUrgentTime(int urgentTime) {
        this.urgentTime = urgentTime;
    }

    public int getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(int defaultTime) {
        this.defaultTime = defaultTime;
    }

    public int getDefaultDistance() {
        return defaultDistance;
    }

    public void setDefaultDistance(int defaultDistance) {
        this.defaultDistance = defaultDistance;
    }

    public int getNotLoginDistance() {
        return notLoginDistance;
    }

    public void setNotLoginDistance(int notLoginDistance) {
        this.notLoginDistance = notLoginDistance;
    }

    public int getSleepingDistance() {
        return sleepingDistance;
    }

    public void setSleepingDistance(int sleepingDistance) {
        this.sleepingDistance = sleepingDistance;
    }

    public int getUrgentDistance() {
        return urgentDistance;
    }

    public void setUrgentDistance(int urgentDistance) {
        this.urgentDistance = urgentDistance;
    }

    public int getInflectionAngle() {
        return inflectionAngle;
    }

    public void setInflectionAngle(int inflectionAngle) {
        this.inflectionAngle = inflectionAngle;
    }

    public int getFenceRadius() {
        return fenceRadius;
    }

    public void setFenceRadius(int fenceRadius) {
        this.fenceRadius = fenceRadius;
    }

    public int getIllegalDrivingTime() {
        return illegalDrivingTime;
    }

    public void setIllegalDrivingTime(int illegalDrivingTime) {
        this.illegalDrivingTime = illegalDrivingTime;
    }
}
