package com.navinfo.opentsp.dongfeng.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created by yinsihua on 2017/2/20.
 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定 此处使用AES-128-CBC加密模式，key需要为16位。
 */
public class AESUtil {

    /**
     * AES加密算法
     * @param sSrc
     * @param sKey
     * @param offset
     * @return
     */
    public static String Encrypt(String sSrc, String sKey, String offset) {
        try {
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
            IvParameterSpec iv = new IvParameterSpec(offset.getBytes("UTF-8"));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

            return Base64.encodeBase64String(encrypted);// 此处使用BAES64做转码功能，同时能起到2次加密的作用。
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密算法
     * @param sSrc
     * @param sKey
     * @param offset
     * @return
     */
    public static String Decrypt(String sSrc, String sKey, String offset) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(offset.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用bAES64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "UTF-8");
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main (String[] args) {
        Long times = new Date().getTime();
        System.out.println(times);
        String temp = AESUtil.Encrypt(String.valueOf(times), "1234567890abcdef", "1234567890abcdef");
        System.out.println(AESUtil.Encrypt(String.valueOf(times), "1234567890abcdef", "1234567890abcdef"));
        System.out.println(AESUtil.Decrypt(temp, "1234567890abcdef", "1234567890abcdef"));
    }
}
