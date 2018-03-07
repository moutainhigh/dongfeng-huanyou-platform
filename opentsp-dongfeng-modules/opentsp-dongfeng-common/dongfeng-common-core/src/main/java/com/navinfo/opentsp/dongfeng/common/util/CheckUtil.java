package com.navinfo.opentsp.dongfeng.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yinsihua on 2016/3/17.
 */
public class CheckUtil {

    // ICCID长度
    private static final int ICCID_LENGTH = 19;

    // 国家地区编码
    private static final String NATION_CODE = "8986";

    // 中国移动号段列表
    private static final String CMCC_SECTION = "0123456789ABCDE";

    // SIM卡供应商代码
    private static final String CMCC_PROVIDER = "0123456789A";


    /**
     * ICCID的校验
     *
     * @param iccid
     * @param operator
     * @return
     */
    public static boolean checkIccid(String iccid, String operator) {
        // 如果ICCID长度校验
        if (iccid.length() != ICCID_LENGTH) return false;
        // 国家地区校验
        if (!NATION_CODE.equals(iccid.substring(0, 4))) return false;
        // 运营商编号
        String ope = iccid.substring(4, 6);
        // 运营商
        switch (operator) {
            // 中国移动
            case "1":
                // 运营商编号
                if (!"00".equals(ope)) return false;
                // 号段
                if (!CMCC_SECTION.contains(iccid.substring(6, 7))) return false;
                // 用户号码第4位
                if (!isNumber(iccid.substring(7, 8))) return false;
                // 省份编码
                String province = iccid.substring(8, 10);
                if (!isNumber(province) || ("01".compareTo(province) > 0 || "31".compareTo(province) < 0))
                    return false;
                // 年号的后2位
                if (!isNumber(iccid.substring(10, 12))) return false;
                // SIM卡供应商
                if (!CMCC_PROVIDER.contains(iccid.substring(12, 13))) return false;
                break;
            // 中国联通
            case "2":
                // 运营商编号
                if (!"01".equals(ope)) return false;
                // 年号的后2位
                if (!isNumber(iccid.substring(6, 8))) return false;
                // 用户号码的第3位
                if (!isNumber(iccid.substring(8, 9))) return false;
                // 本地区号
                if (!isNumber(iccid.substring(11, 13))) return false;
                break;
            // 中国电信
            case "3":
                // 运营商编号
                if (!"06".equals(ope)) return false;
                // 保留位
                if (!"0".equals(iccid.substring(6, 7))) return false;
                // 年号的后2位
                if (!isNumber(iccid.substring(7, 10))) return false;
                // 本地区号
                if (!isNumber(iccid.substring(10, 12))) return false;
                break;
        }
        return true;
    }

    /**
     * 数字校验
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        // 非负整数
        Pattern pat = Pattern.compile("^[1-9]\\d*|0$");
        Matcher mat = pat.matcher(str);

        return mat.matches();
    }
    /**
     * 字符串是否包含非数字
     *
     * @param str
     * @return
     */
    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
