package com.navinfo.opentsp.dongfeng.monitor.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class RiskConstant {
    /**
     * 风险防控区域类型枚举
     */
    public enum RegionTypeEnum {
        RECTANGLE(1),//矩形
        POLYGON(2),//多边形
        CIRCLE(3),//圆形
        POLYLINE(4);//线形

        private int code;

        RegionTypeEnum(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }


    /**
     * 风险防控类型
     */
    public enum RiskTypeEnum {
        OUT_LIMIT_SPEED(1, "禁出限速"),
        UNSUPPORTED(99, "不支持的防控类型");
        private int value;
        private String msg;
        private static Map<Integer, RiskTypeEnum> codes = new ConcurrentHashMap<>();

        static {
            for (RiskTypeEnum typeEnum : RiskTypeEnum.values()) {
                codes.put(typeEnum.getValue(), typeEnum);
            }
        }

        RiskTypeEnum(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        public int getValue() {
            return value;
        }


        public String getMsg() {
            return msg;
        }


        public static RiskTypeEnum valueOf(int code) {
            if (codes.get(code) != null) {
                return codes.get(code);
            } else {
                return UNSUPPORTED;
            }
        }
    }
}
