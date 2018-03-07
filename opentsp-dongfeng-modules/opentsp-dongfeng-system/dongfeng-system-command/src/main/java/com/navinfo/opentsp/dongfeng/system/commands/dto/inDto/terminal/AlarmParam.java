package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal;

/**
 * 违规驾驶行为报警阀值设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class AlarmParam extends BaseTerminalParam {
    private Integer acceleration; // 急加速阀值，单位m/s
    private Integer deceleration; // 急减速阀值，单位m/s
    private Integer lowOil; // 低油量阀值参数
    private Integer slideNeutral; // 空挡滑行时间参数 单位为秒（S）
    private Integer throttle;// 猛踩油门阀值
    private Integer longTimeBreaking;// 长时间刹车阀值（>s）
    private Integer longTimeClutch; // 长时间离合时间阀值 单位为秒（s）
    private Integer parkedTime;// 停车立即熄火 单位为秒（s）
    private Integer coldBootTime;// 发动机冷启动 单位为秒（s）
    private Integer idlingTime;// 过长怠速 单位为秒（s）
    private Integer tightBendAngle;

    public Integer getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    public Integer getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(Integer deceleration) {
        this.deceleration = deceleration;
    }

    public Integer getLowOil() {
        return lowOil;
    }

    public void setLowOil(Integer lowOil) {
        this.lowOil = lowOil;
    }

    public Integer getSlideNeutral() {
        return slideNeutral;
    }

    public void setSlideNeutral(Integer slideNeutral) {
        this.slideNeutral = slideNeutral;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public void setThrottle(Integer throttle) {
        this.throttle = throttle;
    }

    public Integer getLongTimeBreaking() {
        return longTimeBreaking;
    }

    public void setLongTimeBreaking(Integer longTimeBreaking) {
        this.longTimeBreaking = longTimeBreaking;
    }

    public Integer getLongTimeClutch() {
        return longTimeClutch;
    }

    public void setLongTimeClutch(Integer longTimeClutch) {
        this.longTimeClutch = longTimeClutch;
    }

    public Integer getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(Integer parkedTime) {
        this.parkedTime = parkedTime;
    }

    public Integer getColdBootTime() {
        return coldBootTime;
    }

    public void setColdBootTime(Integer coldBootTime) {
        this.coldBootTime = coldBootTime;
    }

    public Integer getIdlingTime() {
        return idlingTime;
    }

    public void setIdlingTime(Integer idlingTime) {
        this.idlingTime = idlingTime;
    }

    public Integer getTightBendAngle() {
        return tightBendAngle;
    }

    public void setTightBendAngle(Integer tightBendAngle) {
        this.tightBendAngle = tightBendAngle;
    }
}
