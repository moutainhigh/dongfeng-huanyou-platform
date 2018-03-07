package com.navinfo.opentsp.dongfeng.common.util.tcp;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

public class Convert
{
    static Logger logger = LoggerFactory.getLogger(Convert.class);
    
    private final static byte[] hex = "0123456789ABCDEF".getBytes();
    
    private static int parse(char c)
    {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
    
    /**
     * 字节数组转换到十六进制字符串
     *
     * @param bytes {@link Byte}字节数组
     * @return {@link String} 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes)
    {
        byte[] buff = new byte[2 * bytes.length];
        for (int i = 0, length = bytes.length; i < length; i++)
        {
            buff[2 * i] = hex[(bytes[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[bytes[i] & 0x0f];
        }
        return new String(buff);
    }
    
    /**
     * 十六进制字符串转换到字节数组
     *
     * @param hexstr {@link String} 十六进制字符串
     * @return {@link Byte}[]字节数组
     */
    public static byte[] hexStringToBytes(String hexstr)
    {
        // 判空
        if (StringUtil.isEmpty(hexstr))
        {
            return null;
        }
        
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0, length = b.length; i < length; i++)
        {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte)((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    
    /**
     * 将十六进制转化为二进制代码,同时高位补0,补<code>offset</code>个<br>
     * 例：参数：00，结果：00000000
     *
     * @param strHex String 十六进制
     * @param offset int 补0个数
     * @return String
     */
    public static String hex2Binary(String strHex, int offset)
    {
        if (offset != 0)
        {
            int i = Integer.parseInt(strHex, 16);
            strHex = Integer.toBinaryString(i);
            if (strHex.length() * 4 == offset)
            {
                return strHex;
            }
            int l = offset - strHex.length();
            char[] c = new char[offset];
            for (int j = 0; j < l; j++)
            {
                c[j] = '0';
            }
            char[] c2 = strHex.toCharArray();
            for (int j = 0; j < c2.length; j++)
            {
                c[j + l] = c2[j];
            }
            strHex = String.valueOf(c);
        }
        return strHex;
    }
    
    /**
     * 十六进制转GBK <br>
     * mixHex2str
     *
     * @param bytes String 十六进制字符串
     * @return String
     */
    public static String hexToGbk(String bytes)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        String opr = null;
        for (int i = 0; i < bytes.length(); i += 2)
        {
            if (bytes.charAt(i) == '0' && bytes.charAt(i + 1) == '0')
                break;
            baos.write(("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(bytes.charAt(i + 1))));
        }
        try
        {
            opr = new String(baos.toByteArray(), "GBK");
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("十六进制转GBK", e);
        }
        if (bytes.charAt(0) == '0' && bytes.charAt(1) == '0')
            opr = "";
        return opr;
    }
    
    /**
     * 字符串转GBK <br>
     * mixStr2Hex
     *
     * @param str
     * @return
     */
    public static String strToGbk(String str)
    {
        byte[] bytes = null;
        try
        {
            bytes = str.getBytes("GBK");
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("字符串转GBK异常", e);
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf0) >> 4));
            sb.append("0123456789ABCDEF".charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }
    
    /**
     * 十六进制转UTF8
     *
     * @param bytes String 十六进制字符串
     * @return String
     */
    public static String hexToUtf8(String bytes)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        String opr = null;
        for (int i = 0; i < bytes.length(); i += 2)
        {
            if (bytes.charAt(i) == '0' && bytes.charAt(i + 1) == '0')
                break;
            baos.write(("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(bytes.charAt(i + 1))));
        }
        try
        {
            opr = new String(baos.toByteArray(), "utf-8");// ISO-8859-1 utf-8
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("十六进制转UTF8异常", e);
        }
        if (bytes.charAt(0) == '0' && bytes.charAt(1) == '0')
            opr = "";
        return opr;
    }
    
    public static byte[] str2Bcd(String asc)
    {
        int len = asc.length();
        int mod = len % 2;
        
        if (mod != 0)
        {
            asc = "0" + asc;
            len = asc.length();
        }
        
        byte abt[] = new byte[len];
        if (len >= 2)
        {
            len = len / 2;
        }
        
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        
        for (int p = 0, asc_lenght = asc.length() / 2; p < asc_lenght; p++)
        {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9'))
            {
                j = abt[2 * p] - '0';
            }
            else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z'))
            {
                j = abt[2 * p] - 'a' + 0x0a;
            }
            else
            {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9'))
            {
                k = abt[2 * p + 1] - '0';
            }
            else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z'))
            {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            }
            else
            {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            
            int a = (j << 4) + k;
            byte b = (byte)a;
            bbt[p] = b;
        }
        return bbt;
    }
    
    /**
     * 填充0
     *
     * @param text {@link String} 需要补0的字符
     * @param length {@link Integer} 补0后的长度
     * @return {@link String}
     */
    public static String fillZero(String text, int length)
    {
        StringBuilder builder = new StringBuilder(length);
        if (text == null)
        {
            for (int i = 0; i < length; i++)
                builder.append("0");
        }
        else
        {
            for (int i = text.length(); i < length; i++)
            {
                builder.append("0");
            }
            builder.append(text);
        }
        return builder.toString();
    }
    
    /**
     * 填充0
     *
     * @param text {@link String} 需要补0的字符
     * @param length {@link Integer} 补0后的长度
     * @param direction {@link Boolean} 补0方向,true：左补,false：右补
     * @return {@link String}
     */
    public static String fillZero(String text, int length, boolean direction)
    {
        StringBuilder builder = new StringBuilder(length);
        if (direction)
        {
            if (text == null)
            {
                for (int i = 0; i < length; i++)
                    builder.append("0");
            }
            else
            {
                for (int i = text.length(); i < length; i++)
                {
                    builder.append("0");
                }
                builder.append(text);
            }
        }
        else
        {
            builder.append(text);
            if (text == null)
            {
                for (int i = 0; i < length; i++)
                    builder.append("0");
            }
            else
            {
                for (int i = text.length(); i < length; i++)
                {
                    builder.append("0");
                }
            }
        }
        return builder.toString();
    }
    
    /**
     * 填充0
     *
     * @param text {@link Long} 需要补0的数字
     * @param length {@link Integer} 补0后的长度
     * @return {@link String}
     */
    public static String fillZero(long number, int length)
    {
        String text = String.valueOf(number);
        return fillZero(text, length);
    }
    
    /**
     * 整形字符串转十六进制
     *
     * @param value {@link String} 整形字符串
     * @param length 补0后长度
     * @return {@link String}
     */
    public static String longStrToHexString(String value, int length)
    {
        long l = Long.parseLong(value);
        String hex = Long.toHexString(l).toUpperCase();
        return fillZero(hex, length);
    }
    
    /**
     * 十进制转十六进制
     *
     * @param number int 十进制
     * @param x int 位数
     * @return
     */
    public static String decimalToHexadecimal(long number, int x)
    {
        String hex = Long.toHexString(number).toUpperCase();
        return fillZero(hex, x);
    }
    
    /**
     * long to int
     */
    public static int long2int(Long L)
    {
        return L.intValue();
    }
    
    /**
     * 字节数组转整形,暂时只支持2个字节或者4个字节的转换
     * 
     * @param bytes {@link Byte} 字节数组
     * @return {@link Integer}
     */
    public static int bytesToInt(byte[] bytes)
    {
        long[] cc = new long[bytes.length];
        if (bytes.length == 2)
        {
            if (bytes[1] < 0)
            {
                cc[1] = (long)(bytes[1] & 0x7f) + 0x80;
            }
            else
            {
                cc[1] = bytes[1];
            }
            if (bytes[0] < 0)
            {
                cc[0] = (long)((bytes[0] & 0x7f) << 8) + 0x8000;
            }
            else
            {
                cc[0] = bytes[0] << 8;
            }
            int res = (int)(cc[1] + cc[0]);
            return res;
        }
        else if (bytes.length == 4)
        {
            if (bytes[3] < 0)
            {
                cc[3] = (long)(bytes[3] & 0x7f) + 0x80;
            }
            else
            {
                cc[3] = bytes[3];
            }
            if (bytes[2] < 0)
            {
                cc[2] = (long)((bytes[2] & 0x7f) << 8) + 0x8000;
            }
            else
            {
                cc[2] = bytes[2] << 8;
            }
            if (bytes[1] < 0)
            {
                cc[1] = (long)((bytes[1] & 0x7f) << 16) + 0x800000;
            }
            else
            {
                cc[1] = bytes[1] << 16;
            }
            if (bytes[0] < 0)
            {
                cc[0] = (long)((bytes[0] & 0x7f) << 24) + 0x80000000;
            }
            else
            {
                cc[0] = bytes[0] << 24;
            }
            int res = (int)(cc[3] + cc[2] + cc[1] + cc[0]);
            return res;
        }
        return 0;
    }
    
    /**
     * 整形转字节
     * 
     * @param number
     * @param size 转换后字节数,暂只支持2字节或者4字节
     * @return
     */
    public static byte[] intTobytes(int number, int size)
    {
        byte[] targets = new byte[size];
        if (size == 2)
        {
            targets[1] = (byte)(number & 0xff);// 最低位
            targets[0] = (byte)((number >> 8) & 0xff);// 次低位
            return targets;
        }
        else if (size == 4)
        {
            targets[3] = (byte)(number & 0xff);// 最低位
            targets[2] = (byte)((number >> 8) & 0xff);// 次低位
            targets[1] = (byte)((number >> 16) & 0xff);// 次高位
            targets[0] = (byte)(number >>> 24);// 最高位,无符号右移。
            return targets;
        }
        return null;
    }
    
    // ==========================================================
    /**
     * 数组拼接
     *
     * @param begin
     * @param end
     * @return
     */
    public final static byte[] arraycopy(final byte[] begin, final byte[] end)
    {
        if (begin == null)
            return end;
        if (end == null)
            return begin;
        int length = begin.length + end.length;
        byte[] bytes = new byte[length];
        System.arraycopy(begin, 0, bytes, 0, begin.length);
        System.arraycopy(end, 0, bytes, begin.length, end.length);
        return bytes;
    }
    
    /**
     * byte数组截取,注意：length必须是小于bytes的长度,当length<0,则从startIndex开始一直截取到最后
     *
     * @author lin.jian
     * @DataTime 2011-3-30 上午10:44:45
     *
     * @param bytes byte 需要截取的byte数组
     * @param startIndex int 开始截取的下标
     * @param length int 截取的总长度
     *
     * @return byte[] 截取后新byte数组
     */
    public static final byte[] getNewBytes(final byte[] bytes, final int startIndex, final int length)
    {
        if (startIndex == 0 && bytes.length == length)
        {
            return bytes;
        }
        else if (length == -1 || bytes.length - startIndex < length)
        {
            byte[] rbytes = new byte[bytes.length - startIndex];
            for (int i = 0; i < rbytes.length; i++)
            {
                rbytes[i] = bytes[startIndex + i];
            }
            return rbytes;
        }
        else
        {
            byte[] rbytes = new byte[length];
            for (int i = 0; i < rbytes.length; i++)
            {
                rbytes[i] = bytes[startIndex + i];
            }
            return rbytes;
        }
    }
    
    /**
     * 整形转字节
     * 
     * @param number
     * @param size
     * 
     * @return
     */
    public static byte[] longTobytes(long number, int size)
    {
        byte[] b = new byte[size];
        for (int i = 0; i < size; i++)
        {
            b[i] = (byte)(number >> 8 * (size - i - 1) & 0xFF);
        }
        return b;
    }
    
    public static int byte2Int(byte[] bytes, int size)
    {
        int intValue = 0;
        for (int i = 0; i < bytes.length; i++)
        {
            intValue += (bytes[i] & 0xFF) << (8 * (size - i - 1));
        }
        return intValue;
    }
    
}
