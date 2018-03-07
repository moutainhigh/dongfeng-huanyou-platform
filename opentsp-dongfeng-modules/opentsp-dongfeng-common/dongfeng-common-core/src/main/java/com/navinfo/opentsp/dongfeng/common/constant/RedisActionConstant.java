package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/8/16
 */
public class RedisActionConstant {
    // redis操作动作
    public final static String ACTION = "action";
    public final static String KEY1 = "key1";
    public final static String KEY2 = "key2";
    public final static String VALUE = "value";

    public enum RedisActionType {

        HSET(1,"设置"),

        HDEL(2,"删除"),

        UNKNOWN(99,"未知");

        private int value;
        private String name;

        RedisActionType(int index, String name) {
            this.value = index;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static RedisActionType valueOf(int value)
        {
            for (RedisActionType redisActionType : RedisActionType.values()){
                if(redisActionType.value == value){
                    return redisActionType;
                }
            }
            return UNKNOWN;
        }
    }
}
