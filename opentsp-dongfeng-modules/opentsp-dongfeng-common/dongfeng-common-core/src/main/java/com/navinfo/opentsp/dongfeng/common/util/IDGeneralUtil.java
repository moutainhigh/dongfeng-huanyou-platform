package com.navinfo.opentsp.dongfeng.common.util;


import java.util.UUID;

/**
 * ID生成工厂
 */

public final class IDGeneralUtil {

    private static volatile long id = 0;

    /**
     * ID类型枚举
     */
    public enum IDTypeEnum {
        SerialNumber,//流水号
        UniqueMark//唯一码
    }


    /**
     * 生成一个ID
     *
     * @param idType
     * @return
     */
    public static Object builderID(IDTypeEnum idType) {
        switch (idType) {
            case SerialNumber:
                return nextSerialNumber();
            case UniqueMark:
                return generalUniqueMark();
            default:
                return null;
        }
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    private static Object nextSerialNumber() {
        String temp = "0000";
        String hex = Long.toHexString(id++);
        if (id >= 65535L)
            id = 0;
        return (temp.substring(0, 4 - hex.length()) + hex).toUpperCase();
    }

    /**
     * 生成一个唯一码
     *
     * @return
     */
    private static Object generalUniqueMark() {
        return UUID.randomUUID().toString();
    }
}
