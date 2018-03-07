package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-29
 * @modify
 * @copyright Navi Tsp
 */
public enum TcpCommandEnum {
    TERMINAL_SETTING(8103, "8103终端设置指令"),
    DISPATCH_MESSAGE(2151, "8300文本信息下发"),//短信调度
    VOICE_MONITORING(2152, "语音监控"),
    VOICE_MONITORING_LISTENING(21520, "语音监控单向监听操作"),
    VOICE_MONITORING_CALLING(21521, "语音监控普通通话操作"),
    POSITION_SETTING(2253, "位置数据采集汇报设置"),
    CAN_SETTING(2267, "Can数据采集汇报设置"),
    DRIVING_BEHAVIOR(2270, "违规驾驶行为阀值设置"),
    TERMINAL_INIT(2273, "终端初始化参数设置"),
    REAL_TIME_DATA_COLLECT(2274, "实时数据采集汇报设置"),
    CAN_DATA_CYCLE_UPLOAD_SETTING(2275, "特殊数据采集汇报设置"),
    TERMINAL_UPGRADE(2405, "终端升级设置"),
    OUT_REGION_LIMIT_SPEED(2317, "设置监控区域限速"),
    DEL_OUT_REGION_LIMIT_SPEED(2318, "取消监控区域限速"),
    MESSAGE_BROADCAST_IN_AREA(2500, "区域信息播报设置"),
    OVERTIME_PARKING_IN_AREA(2502, "区域滞留超时"),
    VEHICLE_PASS_IN_AREA(2505, "区域车次统计"),
    IN_OR_OUT_AREA_NOTIFY_SETTING(2507, "进出区域通知设置"),
    IN_OR_OUT_AREA_NOTIFY_SETTING_DEL(2508, "进出区域通知设置删除");
    private String command;
    private String msg;

    TcpCommandEnum(int command, String msg) {
        this.command = command + "";
        this.msg = msg;
    }

    public String getCommand() {
        return command;
    }

    public String getMsg() {
        return msg;
    }
}
