package com.navinfo.opentsp.dongfeng.monitor.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalLogConstant {
    /**
     * 终端指令状态枚举
     */
    public enum CommandStatusEnum {
        UNLIMITED(-4, "不限"),
        WAITING(-3, "等待未响应"),
        EXECUTING(-2, "正在执行"),
        OUT_DATE(-1, "已失效"),
        FAILURE(0, "失败"),
        SUCCESS(1, "成功"),
        MESSAGE_ERROR(2, "消息有误"),
        NOT_SUPPORT(3, "终端不支持"),
        ALARM_HANDLE(4, "报警处理确认"),
        OFFLINE(5, "终端离线"),
        NO_REGISTER(6, "终端未注册"),
        TYPE_ERROR(10, "操作类型无效"),
        LIMIT_ERROR(11, "限制类型无效"),
        VEHICLE_CLOSE(12, "车辆关机"),
        COMMUNICATE_ERROR(13, "总线通信异常"),
        UNKNOWN_ERROR(99, "未知状态");

        private int code;
        private String des;

        CommandStatusEnum(int code, String des) {
            this.code = code;
            this.des = des;
        }

        private static Map<Integer, CommandStatusEnum> codes = new ConcurrentHashMap<>();

        static {
            for (CommandStatusEnum statusEnum : CommandStatusEnum.values()) {
                codes.put(statusEnum.getCode(), statusEnum);
            }
        }

        public static CommandStatusEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNKNOWN_ERROR;
            }
        }

        public int getCode() {
            return code;
        }


        public String getDes() {
            return des;
        }

    }

    /**
     * 终端指令方向枚举
     */
    public enum CommandDirEnum {
        PLATFORM_TO_TERMINAL(0, "平台-终端"),
        TERMINAL_TO_PLATFORM(1, "终端-平台"),
        PLATFORM_TO_PLATFORM(2, "平台-平台"),
        UNKNOWN(3, "未知");
        private int code;
        private String msg;

        private static Map<Integer, CommandDirEnum> codes = new ConcurrentHashMap<>();

        static {
            for (CommandDirEnum dirEnum : CommandDirEnum.values()) {
                codes.put(dirEnum.getCode(), dirEnum);
            }
        }

        CommandDirEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static CommandDirEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNKNOWN;
            }
        }
    }

}
