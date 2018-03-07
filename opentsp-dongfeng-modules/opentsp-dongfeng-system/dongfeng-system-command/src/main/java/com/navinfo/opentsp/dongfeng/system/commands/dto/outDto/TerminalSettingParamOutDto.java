package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.*;

import java.util.List;

/**
 * 终端设置数据
 *
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-19
 **/
public class TerminalSettingParamOutDto {
    /**
     * 终端设置目录
     */
    private List<TerminalSettingMenuOutdto> menu;
    /**
     * 位置数据采集汇报设置
     */
    private GpsParam gpsParam;
    /**
     * can数据采集汇报设置
     */
    private CanParam canParam;
    /**
     * 终端初始化参数设置
     */
    private InitParam initParam;
    /**
     * 违规驾驶行为阀值设置
     */
    // TODO: 2017/10/20  还需要考虑复选框问题
    private AlarmParam alarmParam;
    /**
     * 终端升级
     */
    private UpgradeParam upgradeParam;
    /***
     * 实时数据采集汇报设置
     */
    private RealTimeParam realTimeDataParam;
    /**
     * 特殊数据采集汇报设置
     */
    private SpecialDataParamOutDto specialDataParam;

    public List<TerminalSettingMenuOutdto> getMenu() {
        return menu;
    }

    public void setMenu(List<TerminalSettingMenuOutdto> menu) {
        this.menu = menu;
    }

    public GpsParam getGpsParam() {
        return gpsParam;
    }

    public void setGpsParam(GpsParam gpsParam) {
        this.gpsParam = gpsParam;
    }

    public CanParam getCanParam() {
        return canParam;
    }

    public void setCanParam(CanParam canParam) {
        this.canParam = canParam;
    }

    public InitParam getInitParam() {
        return initParam;
    }

    public void setInitParam(InitParam initParam) {
        this.initParam = initParam;
    }

    public AlarmParam getAlarmParam() {
        return alarmParam;
    }

    public void setAlarmParam(AlarmParam alarmParam) {
        this.alarmParam = alarmParam;
    }

    public UpgradeParam getUpgradeParam() {
        return upgradeParam;
    }

    public void setUpgradeParam(UpgradeParam upgradeParam) {
        this.upgradeParam = upgradeParam;
    }

    public RealTimeParam getRealTimeDataParam() {
        return realTimeDataParam;
    }

    public void setRealTimeDataParam(RealTimeParam realTimeDataParam) {
        this.realTimeDataParam = realTimeDataParam;
    }

    public SpecialDataParamOutDto getSpecialDataParam() {
        return specialDataParam;
    }

    public void setSpecialDataParam(SpecialDataParamOutDto specialDataParam) {
        this.specialDataParam = specialDataParam;
    }
}
