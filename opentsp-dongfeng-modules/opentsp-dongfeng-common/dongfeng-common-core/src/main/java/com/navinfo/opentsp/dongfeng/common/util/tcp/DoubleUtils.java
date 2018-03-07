package com.navinfo.opentsp.dongfeng.common.util.tcp;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-31
 * @modify
 * @copyright Navi Tsp
 */
public class DoubleUtils {
    //取小数点后一位
    public static String getDoubleData(Double data) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
        try {
            String d = data == null ? "0" : df.format(data) + "";//整车里程（Km）
            return d;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "0";
    }

    //取小数点后一位
    public static double getDoubleValueData(Double data) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
        try {
            String d = data == null ? "0" : df.format(data) + "";//整车里程（Km）
            return Double.valueOf(d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //取小数点后6位
    public static double getDoubleData6(double data) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.000000");
        try {
            String d = df.format(data);
            return Double.valueOf(d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
