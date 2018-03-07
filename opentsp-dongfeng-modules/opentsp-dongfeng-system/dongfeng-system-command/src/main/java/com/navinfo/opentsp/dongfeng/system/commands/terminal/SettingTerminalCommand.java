package com.navinfo.opentsp.dongfeng.system.commands.terminal;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.*;

/**
 * 终端设置命令
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class SettingTerminalCommand extends BaseCommand<HttpCommandResultWithData> {
    private String tid;//终端自增主键ID
    private String terminalId;//终端ID
    @Converter(target = "gpsParamBean")
    private String gpsParam;// 位置数据采集汇报设置
    private GpsParam gpsParamBean;    // 位置数据采集汇报设置对象
    @Converter(target = "canParamBean")
    private String canParam;// can数据采集汇报设置
    private CanParam canParamBean;
    @Converter(target = "initParamBean")
    private String initParam;// 终端初始化参数设置
    private InitParam initParamBean;
    @Converter(target = "alarmParamBean")
    private String alarmParam;// 违规驾驶行为阀值设置
    private AlarmParam alarmParamBean;
    @Converter(target = "upgradeParamBean")
    private String upgradeParam; // 终端升级
    private UpgradeParam upgradeParamBean;
    @Converter(target = "realTimeParamBean")
    private String realTimeDataParam; // 实时数据采集汇报
    private RealTimeParam realTimeParamBean;
    @Converter(target = "specialDataParamBean")
    private String SpecialDataParam; // 特殊数据采集汇报
    private SpecialDataParam specialDataParamBean;
    private String menuSet;// 终端设置菜单选中实例值
    private String driveSet;// 驾驶分析选中实例值
    private String operateIp;//操作人IP

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getGpsParam() {
        return gpsParam;
    }

    public void setGpsParam(String gpsParam) {
        this.gpsParam = gpsParam;
    }

    public String getCanParam() {
        return canParam;
    }

    public void setCanParam(String canParam) {
        this.canParam = canParam;
    }

    public String getInitParam() {
        return initParam;
    }

    public void setInitParam(String initParam) {
        this.initParam = initParam;
    }

    public String getAlarmParam() {
        return alarmParam;
    }

    public void setAlarmParam(String alarmParam) {
        this.alarmParam = alarmParam;
    }

    public String getUpgradeParam() {
        return upgradeParam;
    }

    public void setUpgradeParam(String upgradeParam) {
        this.upgradeParam = upgradeParam;
    }

    public String getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(String menuSet) {
        this.menuSet = menuSet;
    }

    public String getDriveSet() {
        return driveSet;
    }

    public void setDriveSet(String driveSet) {
        this.driveSet = driveSet;
    }

    public GpsParam getGpsParamBean() {
        return gpsParamBean;
    }

    public void setGpsParamBean(GpsParam gpsParamBean) {
        this.gpsParamBean = gpsParamBean;
    }

    public CanParam getCanParamBean() {
        return canParamBean;
    }

    public void setCanParamBean(CanParam canParamBean) {
        this.canParamBean = canParamBean;
    }

    public InitParam getInitParamBean() {
        return initParamBean;
    }

    public void setInitParamBean(InitParam initParamBean) {
        this.initParamBean = initParamBean;
    }

    public AlarmParam getAlarmParamBean() {
        return alarmParamBean;
    }

    public void setAlarmParamBean(AlarmParam alarmParamBean) {
        this.alarmParamBean = alarmParamBean;
    }

    public UpgradeParam getUpgradeParamBean() {
        return upgradeParamBean;
    }

    public void setUpgradeParamBean(UpgradeParam upgradeParamBean) {
        this.upgradeParamBean = upgradeParamBean;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }


    public RealTimeParam getRealTimeParamBean() {
        return realTimeParamBean;
    }

    public void setRealTimeParamBean(RealTimeParam realTimeParamBean) {
        this.realTimeParamBean = realTimeParamBean;
    }


    public SpecialDataParam getSpecialDataParamBean() {
        return specialDataParamBean;
    }

    public void setSpecialDataParamBean(SpecialDataParam specialDataParamBean) {
        this.specialDataParamBean = specialDataParamBean;
    }

    public String getRealTimeDataParam() {
        return realTimeDataParam;
    }

    public void setRealTimeDataParam(String realTimeDataParam) {
        this.realTimeDataParam = realTimeDataParam;
    }

    public String getSpecialDataParam() {
        return SpecialDataParam;
    }

    public void setSpecialDataParam(String specialDataParam) {
        SpecialDataParam = specialDataParam;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}