package com.navinfo.opentsp.dongfeng.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class OperateLogConstants {
    /**
     * 操作结果
     */
    public enum OperateResultEnum {
        SUCCESS(0, "操作成功"),
        FAILED(1, "操作失败");
        private int code;
        private String msg;

        OperateResultEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }


        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static OperateResultEnum valueOf(int code) {
            switch (code) {
                case 0:
                    return FAILED;
                case 1:
                    return SUCCESS;
                default:
                    return FAILED;
            }
        }
    }

    /**
     * 操作类型
     */
    public enum OperateTypeEnum {
        ADD(1, "新增"),
        DELETE(2, "删除"),
        UPDATE(3, "修改"),
        UNKNOWN(99, "未知操作");

        private int value;
        private String msg;

        OperateTypeEnum(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        static Map<Integer, OperateTypeEnum> map = new HashMap<>();

        static {
            for (OperateTypeEnum operateTypeEnum : OperateTypeEnum.values()) {
                map.put(operateTypeEnum.getValue(), operateTypeEnum);
            }
        }

        public int getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }

        public static OperateTypeEnum valueOf(int value) {
            OperateTypeEnum obj = map.get(value);
            return obj == null ? UNKNOWN : obj;
        }

    }

    /**
     * 操作日志备注前缀枚举
     */
    public enum OperateContentPrefixEnum {
        TERMINAL("终端ID："),
        VEHICLE("底盘号："),
        STATION("服务站编码："),
        DEALER("经销商编码："),
        USER("用户名称："),
        BUSINESS("客户证件号："),
        TERMINAL_MODEL("终端型号："),
        DISTRICT("销售区域名称："),
        ROLE("角色名称："),
        PASSWORD("修改登录密码");

        private String value;

        OperateContentPrefixEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
