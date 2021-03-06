package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.SQLException;

/**
 * 转义工具类
 */
public class EscapeUtil
{
    
    private static boolean usingAnsiMode = false;
    
    private static CharsetEncoder charsetEncoder = Charset.forName(("Utf-8")).newEncoder();;
    
    /**
     * sql转义字符串
     * 
     * @param x
     * @return
     * @throws SQLException
     */
    public static String getString(String x)
        throws SQLException
    {
        String parameterAsString = x;
        
        if (parameterAsString == null)
        {
            return "";
        }
        
        // in查询不替换单引号
        if (x.contains(Constants.NO_REPLACE_SINGLE_QUOTATION))
        {
            x = x.replace(Constants.NO_REPLACE_SINGLE_QUOTATION, "");
            return x;
        }
        
        // 只是下划线进行转义
        if (StringUtil.isEq(x, Constants.SYMBO_UNDERLINE))
        {
            x = '!' + "_";
            return x;
        }
        
        int stringLength = x.length();
        StringBuilder parameterAsBytes;
        if (isEscapeNeededForString(x, stringLength))
        {
            parameterAsBytes = new StringBuilder((int)((double)x.length() * 1.1D));
            // parameterAsBytes.append('\'');
            
            for (int i = 0; i < stringLength; ++i)
            {
                char c = x.charAt(i);
                switch (c)
                {
                    case '\u0000':
                        parameterAsBytes.append('\\');
                        parameterAsBytes.append('0');
                        break;
                    case '\n':
                        parameterAsBytes.append('\\');
                        parameterAsBytes.append('n');
                        break;
                    case '\r':
                        parameterAsBytes.append('\\');
                        parameterAsBytes.append('r');
                        break;
                    case '\u001a':
                        parameterAsBytes.append('\\');
                        parameterAsBytes.append('Z');
                        break;
                    case '\"':
                        if (usingAnsiMode)
                        {
                            parameterAsBytes.append('\\');
                        }
                        
                        parameterAsBytes.append('\"');
                        break;
                    case '\'':
                        // parameterAsBytes.append('\\');
                        parameterAsBytes.append('\'');
                        parameterAsBytes.append('\'');
                        break;
                    case '\\':
                        parameterAsBytes.append('\\');
                        parameterAsBytes.append('\\');
                        break;
                    case '¥':
                        break;
                    case '₩':
                        if (charsetEncoder != null)
                        {
                            CharBuffer cbuf = CharBuffer.allocate(1);
                            ByteBuffer bbuf = ByteBuffer.allocate(1);
                            cbuf.put(c);
                            cbuf.position(0);
                            charsetEncoder.encode(cbuf, bbuf, true);
                            if (bbuf.get(0) == 92)
                            {
                                parameterAsBytes.append('\\');
                            }
                        }
                        break;
                    case '%':
                        parameterAsBytes.append('!');
                        parameterAsBytes.append('%');
                        break;
                    // case '_':
                    // parameterAsBytes.append('!');
                    // parameterAsBytes.append('_');
                    // break;
                    case '!':
                        parameterAsBytes.append('!');
                        parameterAsBytes.append('!');
                        break;
                    default:
                        parameterAsBytes.append(c);
                        break;
                }
            }
            // parameterAsBytes.append('\'');
            parameterAsString = parameterAsBytes.toString();
        }
        return parameterAsString;
    }
    
    private static boolean isEscapeNeededForString(String x, int stringLength)
    {
        boolean needsHexEscape = false;
        
        for (int i = 0; i < stringLength; ++i)
        {
            char c = x.charAt(i);
            switch (c)
            {
                case '\u0000':
                    needsHexEscape = true;
                    break;
                case '\n':
                    needsHexEscape = true;
                    break;
                case '\r':
                    needsHexEscape = true;
                    break;
                case '\u001a':
                    needsHexEscape = true;
                    break;
                case '\"':
                    needsHexEscape = true;
                    break;
                case '\'':
                    needsHexEscape = true;
                    break;
                case '\\':
                    needsHexEscape = true;
                    break;
                case '%':
                    needsHexEscape = true;
                    break;
                case '_':
                    needsHexEscape = true;
                    break;
                case '!':
                    needsHexEscape = true;
                    break;
            }
            
            if (needsHexEscape)
            {
                break;
            }
        }
        return needsHexEscape;
    }
}
