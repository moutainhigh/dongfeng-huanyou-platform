package com.navinfo.opentsp.dongfeng.common.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaocy on 2017/03/09. 通用 用户类型
 */
public enum UserTypeConstant
{
    
    SYSTEM_ADMIN(1, "系统管理员"), CAR_FACTORY(2, "车厂"), SERVICE_STATION(3, "服务站"), TRANSPORT(4, "运输企业"), BUSINESS(5, "经销商"), FINANCE_COMPANY(
        6, "金融公司"),
    
    UNKNOWN(99, "未知类型");
    
    private final int code;
    
    private String name;
    
    UserTypeConstant(final int code, final String name)
    {
        this.code = code;
        this.name = name;
    }
    
    private static Map<Integer, UserTypeConstant> codes = new ConcurrentHashMap<>();
    
    static
    {
        for (UserTypeConstant errorCodeEnum : UserTypeConstant.values())
        {
            codes.put(errorCodeEnum.getCode(), errorCodeEnum);
        }
    }
    
    public int getCode()
    {
        return code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public static UserTypeConstant valueOf(int code)
    {
        if (codes.get(code) != null)
        {
            return codes.get(code);
        }
        else
        {
            return UNKNOWN;
        }
    }
}
