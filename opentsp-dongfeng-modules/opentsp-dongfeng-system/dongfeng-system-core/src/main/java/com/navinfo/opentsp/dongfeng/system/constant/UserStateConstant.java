package com.navinfo.opentsp.dongfeng.system.constant;



/**
 * Created by yaocy on 2017/03/08.
 */
public class UserStateConstant {

    public static final int NOT_EXPIRED = 0; // 未过期
    public static final int EXPIRED = 1; // 已过期
    public static final String UN_LOCK = "0"; // 已过期

    public enum Expired {
        NOT_EXPIRED_TYPE(NOT_EXPIRED, "正常"),EXPIRED_TYPE(EXPIRED, "已过期"), UNKNOWN(99, "未知类型");
        private int index;
        private String value;

        private Expired(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }

        public static int getIndex(String value) {
            for (Expired type : Expired.values()) {
                if (type.value.equals(value)) {
                    return type.index;
                }
            }
            return UNKNOWN.index;
        }

        public static String getValue(int indexValue) {
            for (Expired type : Expired.values()) {
                if (type.index == indexValue) {
                    return type.value;
                }
            }
            return UNKNOWN.value;
        }
    }

    public enum Locked {
        NOT_LOCKED_TYPE(0, "正常"), LOCKED_TYPE(1, "已锁定"), UNKNOWN(99, "未知类型");
        private int index;
        private String value;

        private Locked(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }

        public static int getIndex(String value) {
            for (Locked type : Locked.values()) {
                if (type.value.equals(value)) {
                    return type.index;
                }
            }
            return UNKNOWN.index;
        }

        public static String getValue(int indexValue) {
            for (Locked type : Locked.values()) {
                if (type.index == indexValue) {
                    return type.value;
                }
            }
            return UNKNOWN.value;
        }
    }
}
