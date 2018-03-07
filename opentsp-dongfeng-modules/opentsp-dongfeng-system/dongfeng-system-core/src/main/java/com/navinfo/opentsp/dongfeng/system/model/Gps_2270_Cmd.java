package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;

/**
 * 违规驾驶行为阀值设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2270_Cmd extends BaseTcpCommand {
    private static final long serialVersionUID = 845146088028651731L;
    private int bitField0;
    private int acceleration;
    private int deceleration;
    private long sharpTurning;
    private long lowOil;
    private long slideNeutral;
    private long rpmThreshold;
    private long throttle;
    private long disparitySpeed;
    private long brakePressure;
    private long longTimeBreaking;
    private long longTimeClutch;
    private long parkedTime;
    private long coldBootTime;
    private long idlingTime;

    public int getBitField0() {
        return bitField0;
    }

    public void setBitField0(int bitField0) {
        this.bitField0 = bitField0;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public int getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(int deceleration) {
        this.deceleration = deceleration;
    }

    public long getSharpTurning() {
        return sharpTurning;
    }

    public void setSharpTurning(long sharpTurning) {
        this.sharpTurning = sharpTurning;
    }

    public long getLowOil() {
        return lowOil;
    }

    public void setLowOil(long lowOil) {
        this.lowOil = lowOil;
    }

    public long getSlideNeutral() {
        return slideNeutral;
    }

    public void setSlideNeutral(long slideNeutral) {
        this.slideNeutral = slideNeutral;
    }

    public long getRpmThreshold() {
        return rpmThreshold;
    }

    public void setRpmThreshold(long rpmThreshold) {
        this.rpmThreshold = rpmThreshold;
    }

    public long getThrottle() {
        return throttle;
    }

    public void setThrottle(long throttle) {
        this.throttle = throttle;
    }

    public long getDisparitySpeed() {
        return disparitySpeed;
    }

    public void setDisparitySpeed(long disparitySpeed) {
        this.disparitySpeed = disparitySpeed;
    }

    public long getBrakePressure() {
        return brakePressure;
    }

    public void setBrakePressure(long brakePressure) {
        this.brakePressure = brakePressure;
    }

    public long getLongTimeBreaking() {
        return longTimeBreaking;
    }

    public void setLongTimeBreaking(long longTimeBreaking) {
        this.longTimeBreaking = longTimeBreaking;
    }

    public long getLongTimeClutch() {
        return longTimeClutch;
    }

    public void setLongTimeClutch(long longTimeClutch) {
        this.longTimeClutch = longTimeClutch;
    }

    public long getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(long parkedTime) {
        this.parkedTime = parkedTime;
    }

    public long getColdBootTime() {
        return coldBootTime;
    }

    public void setColdBootTime(long coldBootTime) {
        this.coldBootTime = coldBootTime;
    }

    public long getIdlingTime() {
        return idlingTime;
    }

    public void setIdlingTime(long idlingTime) {
        this.idlingTime = idlingTime;
    }
}
