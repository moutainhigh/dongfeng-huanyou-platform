package com.navinfo.opentsp.dongfeng.report.constant;

/**
 * 报表常量定义
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-05
 * @modify
 * @copyright Navi Tsp
 */
public class Constant {

    public enum OverTimeEnum {
        YES("是", Byte.valueOf("1")), NO("否", Byte.valueOf("0")), UNKNOWN("unknown", Byte.valueOf("9"));

        private String value;
        private byte index;

        private OverTimeEnum(String value, byte index) {
            this.value = value;
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public byte getIndex() {
            return index;
        }

        public static byte getIndex(String value) {
            for (OverTimeEnum overTime : OverTimeEnum.values()) {
                if (overTime.value.equals(value)) {
                    return overTime.index;
                }
            }
            return UNKNOWN.index;
        }

        public static String getValue(byte indexValue) {
            for (OverTimeEnum overTime : OverTimeEnum.values()) {
                if (overTime.index == indexValue) {
                    return overTime.value;
                }
            }
            return UNKNOWN.value;
        }
    }

    /**
     * 电动机枚举类型
     */
    public enum EngineTypeEnum {
        WN(51, "电机(沃能)"),
        QY(52, "电机(汽研)");
        private int code;
        private String msg;

        EngineTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        /**
         * 是否是电动机
         *
         * @param code
         * @return
         */
        @Deprecated
        public static boolean isElecEngine(String code) {
            if (code == null || code.length() == 0) {
                return false;
            }
            if (Integer.valueOf(code) == WN.getCode() || Integer.valueOf(code) == QY.getCode()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public enum ScanCodeStatus {
        UN_OFFLINE(0, "未下线空入"),
        OFFLINE(1, "下线空入");

        private int index;
        private String value;

        ScanCodeStatus(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum StationEnable {
        UnEnable(0, "停用"), Enable(1, "启用"), UNKNOWN(9, "unknown");

        private int index;
        private String value;

        private StationEnable(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public String getValue() {
            return value;
        }

        public static String getValue(int indexValue) {
            for (StationEnable enable : StationEnable.values()) {
                if (enable.index == indexValue) {
                    return enable.value;
                }
            }
            return UNKNOWN.value;
        }
    }

    public enum SyncStatus {
        UN_SYNC(0, "未同步"),
        SYNC(1, "已同步"),
        NO_SYNC(2, ""), // 非空入车辆和空入车辆非空入流程,无需同步
        SYNC_FAILED(3, "同步失败"),
        UNKNOWN(9, "unknown");

        private int index;
        private String value;

        SyncStatus(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String getValue(int indexValue) {
            for (SyncStatus status : SyncStatus.values()) {
                if (status.index == indexValue) {
                    return status.value;
                }
            }
            return UNKNOWN.value;
        }
    }

    public enum AuditStatus {
        PENDING(0, "待审核"),
        APPROVING(1, "通过"),
        REJECT(2, "拒绝"),
        UNKNOWN(9, "unknown");

        private int index;
        private String value;

        AuditStatus(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static AuditStatus valueOf(int indexValue) {
            for (AuditStatus status : AuditStatus.values()) {
                if (status.index == indexValue) {
                    return status;
                }
            }
            return UNKNOWN;
        }
    }

    public static final String DEFAULT_LONGITUDE = "0.0";

    public static final String DEFAULT_LATITUDE = "0.0";
}
