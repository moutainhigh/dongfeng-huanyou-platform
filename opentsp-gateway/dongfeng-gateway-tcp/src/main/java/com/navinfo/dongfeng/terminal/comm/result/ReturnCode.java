package com.navinfo.dongfeng.terminal.comm.result;

/**
 * 通用Http code 封装
 *
 * @author zhangy
 * @date 2016-03-02
 * @modify
 * @copyright Navi Tsp
 */
public enum ReturnCode implements InterfaceResultCode {

    OK(200, "OK"), NO_DATA(511, "NO DATA"), CLIENT_ERROR(507, "Client Error"), FORBIDDEN(510, "Forbidden"), SERVER_ERROR(
            506, "Server Error"), LOGIN_FAIL(509, "登录失败！"), USER_DNE(508, "User Does Not Exist"),

    // 通用业务信息
    DEL_FAIL(512, "删除失败"), DATA_EXIST(513, "数据已经存在"), DATA_NO_EXIST(514, "数据不存在"),

    /***************************************************
     * 业务错误提示 Begin
     ************************************************************************/

    // 业务返回信息，5位数，前4为代表cmd，后两位业务提编码
    CMD_0103_ERROR(010301, "下发指令0103异常"),
    CMD_2151_ERROR(215101, "下发指令2151异常"),
    CMD_2151_UNSUPPORTED_SHOW_TYPE(215102, "不支持的显示方式"),
    CMD_2152_ERROR(215201, "下发指令2152异常"),
    CMD_2253_POSITION_SETTING_ERROR(225301, "下发指令2253位置数据采集汇报设置异常"),
    CMD_2270_DRIVING_BEHAVIOR_ERROR(227001, "下发指令2270违规驾驶行为阀值设置异常"),
    CMD_2267_CAN_SETTING_ERROR(2267, "下发指令2267CAN设置异常"),
    CMD_2273_INIT_SETTING_ERROR(2273, "下发指令2273终端初始化参数设置异常"),
    CMD_2274_REAL_TIME_DATA_COLLECTION_ERROR(227401, "下发指令2274实时数据采集汇报设置异常"),
    CMD_2275_CAN_DATA_CYCLE_UPLOAD_SETTING_ERROR(227501, "下发指令2275特殊数据采集汇报设置异常"),
    CMD_2450_TERMINAL_UPGRADE_ERROR(245001, "下发指令2450终端升级设置异常"),
    CMD_2317_OUT_REGION_LIMIT_SPEED_ERROR(231701, "下发指令2317出区域限速异常"),
    CMD_2318_DEL_OUT_REGION_LIMIT_SPEED_ERROR(231801, "下发指令2318删除出区域限速异常"),
    CMD_2500_MESSAGE_BROADCAST_IN_AREA_ERROR(2500, "区域信息播报设置"),
    CMD_2502_OVERTIME_PARKING_IN_AREA_ERROR(2502, "区域滞留超时"),
    CMD_2505_VEHICLE_PASS_IN_AREA_ERROR(2505, "区域车次统计"),
    CMD_2507_IN_OR_OUT_AREA_NOTIFY_SETTING(2507, "进出区域通知设置"),
    CMD_2508_IN_OR_OUT_AREA_NOTIFY_SETTING_DEL(2508, "进出区域通知设置删除"),
    /*************************************************** 业务错误提示 End ************************************************************************/

    /**
     * used in cases when data was concurrently modified, for example when version value of passed data differ from
     * version value of stored data.
     */
    CONFLICT(409, "Conflict");

    private final int code;

    private String message;

    ReturnCode(int code) {
        this.code = code;
    }

    ReturnCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
