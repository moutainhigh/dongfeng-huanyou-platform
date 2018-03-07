package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 字典表（hy_basicdata）类型常量类
 *
 * @author fengwm
 * @version 1.0
 * @date 2017-04-10
 * @modify
 * @copyright Navi Tsp
 */

public class DictionaryConstant {

    /**
     * 字典表类型
     */
    public enum BaseDataType {
        CAR_TYPE(2, "车辆类型"),
        ENGINE_TYPE(40, "发动机类型"),
        SERVICE_CONTENT(41, "服务内容"),
        SERVICE_PARTS(42, "服务明细"),
        TERMINAL_SETTING_COMMAND(52, "终端设置指令"),
        TERMINAL_PARAM_SETTING(70, "终端参数设置");

        private Integer type;
        private String value;

        BaseDataType(Integer type, String value) {
            this.type = type;
            this.value = value;
        }

        public Integer getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}