package com.navinfo.opentsp.dongfeng.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Collections;
import java.util.List;

/**
 * Created by yinsihua on 2016/10/31.
 * 中文工具类
 */
public class ChineseUtil {

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        String output = "";
        try {
            if (StringUtil.isNotEmpty(inputString)) {

                char[] input = inputString.trim().toCharArray();
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output += temp[0];
                    } else
                        output += java.lang.Character.toString(input[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return output;
    }
    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        if (StringUtil.isNotEmpty(chinese)) {
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                        if (temp != null) {
                            pybf.append(temp[0].charAt(0));
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim().toLowerCase();
    }
    /**
     * 获取汉字串拼音，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getFullSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        if (StringUtil.isNotEmpty(chinese)){

            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
        }
        return pybf.toString();
    }

    public static String getNameFistSpell(String name) {
        StringBuilder result = new StringBuilder();
        if (StringUtil.isNotEmpty(name)) {
            result.append(getFirstSpell(name).substring(0,1));
        }
        return result.toString();
    }

    public static <T> List<T> sortByName(List<T> list) {
        if (list == null || list.size() == 0) {
            return list;
        }
        Collections.sort(list, new ChineseComparator<T>());
        return list;
    }

    public static void main(String[] args) {
        System.out.println(ChineseUtil.getFirstSpell("Ya"));
        System.out.println(ChineseUtil.getPingYin("yin"));
        System.out.println(ChineseUtil.getFullSpell("yin"));
        System.out.println(ChineseUtil.getNameFistSpell("yin"));
    }
}
