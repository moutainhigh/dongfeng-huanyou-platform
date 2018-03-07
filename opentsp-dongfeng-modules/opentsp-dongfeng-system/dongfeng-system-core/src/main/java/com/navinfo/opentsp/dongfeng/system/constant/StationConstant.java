package com.navinfo.opentsp.dongfeng.system.constant;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class StationConstant {

    public enum StationBasicDataTypeEnum {
        SERVICE_TYPE(58, "服务类型"),
        SPARE_PARTS(42, "备件"),
        SERVICE_CONTENT(41, "服务内容"),
        PROMOTE_TOOL(59, "提升工具");

        private int code;
        private String msg;

        StationBasicDataTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
