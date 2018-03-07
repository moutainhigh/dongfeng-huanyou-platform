package com.navinfo.opentsp.dongfeng.system.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 终端常量
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-15
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalConstant {
    /**
     * 终端操作枚举
     */
    public enum TerminalOperateEnum {
        ADD("新建终端"),
        DELETE("删除终端"),
        QUERY("查询终端详情"),
        UPDATE("修改终端"),
        SEARCH("搜索终端"),
        SETTING("设置终端"),
        IMPORT("批量导入终端");
        private String operateName;

        TerminalOperateEnum(String operateName) {
            this.operateName = operateName;
        }

        public String getOperateName() {
            return operateName;
        }
    }

    /**
     * 终端状态枚举
     */
    public enum TerminalStatusEnum {
        ONLINE(1, "在线行驶"),
        ONLINE_STOP(2, "在线停车"),
        ONLINE_NO_LOCATION(3, "在线未定位"),
        OFFLINE(4, "离线行驶"),
        OFFLINE_STOP(5, "离线停车"),
        OFFLINE_NO_LOCATION(6, "离线未定位"),
        UNKNOWN(7, "未知状态");

        private int code;
        private String msg;

        private static Map<Integer, TerminalStatusEnum> codes = new ConcurrentHashMap<>();

        static {
            for (TerminalStatusEnum statusEnum : TerminalStatusEnum.values()) {
                codes.put(statusEnum.getCode(), statusEnum);
            }
        }

        TerminalStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static TerminalStatusEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNKNOWN;
            }
        }

        public static boolean isOnline(int code) {
            TerminalStatusEnum temp = valueOf(code);
            switch (temp) {
                case ONLINE:
                case ONLINE_STOP:
                case ONLINE_NO_LOCATION:
                    return true;
                case OFFLINE:
                case OFFLINE_STOP:
                case OFFLINE_NO_LOCATION:
                    return false;
                default:
                    return false;
            }
        }
    }

    /**
     * 终端类型枚举
     */
    public enum TerminalTypeEnum {
        BEI_DOU_INTEGRATED_MACHINE(1, "北斗一体机"),
        FK_CONTROLLER(0, "FK控制器"),
        UNKNOWN(99, "未知终端类型");

        private int code;
        private String msg;

        private static Map<Integer, TerminalTypeEnum> codes = new ConcurrentHashMap<>();

        static {
            for (TerminalTypeEnum typeEnum : TerminalTypeEnum.values()) {
                codes.put(typeEnum.getCode(), typeEnum);
            }
        }

        TerminalTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static TerminalTypeEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNKNOWN;
            }
        }

    }

    /**
     * 页面勾选的需要下发的终端选项枚举
     */
    public enum TerminalSettingMenuEnum {
        POSITION_SETTING(1, "positionSetting", "位置汇报设置"),
        TERMINAL_UPGRADE(2, "terminalUpgrade", "终端升级设置"),
        CAN_SETTING(3, "canSetting", "can汇报设置"),
        DRIVING_BEHAVIOR(4, "drivingBehavior", "驾驶行为设置"),
        TERMINAL_INIT(5, "terminalInit", "终端初始化参数设置"),
        REAL_TIME(6, "realTime", "实时数据采集汇报设置"),
        SPECIAL_DATA(7, "specialData", "特殊数据采集汇报设置");
        private int code;
        private String menu;
        private String msg;

        private static Map<Integer, TerminalSettingMenuEnum> codes = new ConcurrentHashMap<>();

        static {
            for (TerminalSettingMenuEnum typeEnum : TerminalSettingMenuEnum.values()) {
                codes.put(typeEnum.getCode(), typeEnum);
            }
        }

        TerminalSettingMenuEnum(int code, String menu, String msg) {
            this.code = code;
            this.menu = menu;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMenu() {
            return menu;
        }

        public String getMsg() {
            return msg;
        }

        public static TerminalSettingMenuEnum valueOf(int code) {
            return codes.get(code);
        }
    }


    /**
     * 驾驶行为设置选项枚举
     */
    public enum DrivingBehaviorEnum {
        THRESHOLD_VALUE_RAPIDLY_SPEEDING_UP("1", "急加速阀值"),
        THRESHOLD_VALUE_RAPIDLY_SPEEDING_DOWN("2", "急减速阀值"),
        THRESHOLD_VALUE_LOW_OIL_VOLUME("3", "低油量阀值参数"),
        THRESHOLD_VALUE_COASTING_TIME_PARAM("4", "空挡滑行时间参数"),
        THRESHOLD_VALUE_STEP_ON_THE_ACCELERATOR("5", "猛踩油门阀值"),
        THRESHOLD_VALUE_LONG_TIME_BRAKE("6", "长时间刹车阀值"),
        THRESHOLD_VALUE_LONG_TIME_CLUTCH("7", "长时间离合阀值"),
        PARKING_STALL_IMMEDIATELY("8", "停车立即熄火"),
        ENGINE_COLD_START("9", "发动机冷启动"),
        LONG_IDLE("10", "过长怠速"),
        TIGHT_BEND_ANGLE("11", "急转弯设置");

        private String code;
        private String msg;

        DrivingBehaviorEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum SupportEngineEnum {
        WEI_CHAI(0, "潍柴"),
        YU_CHAI(1, "玉柴"),
        XI_CHAI(2, "锡柴"),
        HANG_FA(3, "杭发"),
        WO_NENG(4, "沃能"),
        QI_YAN(5, "汽研");
        private int code;
        private String msg;

        SupportEngineEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


}

