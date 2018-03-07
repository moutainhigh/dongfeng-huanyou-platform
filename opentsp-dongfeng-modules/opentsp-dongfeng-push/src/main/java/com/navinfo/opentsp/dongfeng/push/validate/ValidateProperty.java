package com.navinfo.opentsp.dongfeng.push.validate;

import com.navinfo.opentsp.dongfeng.common.validation.ValidateUtil;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

/**
 * Created by zhangyu on 2017/3/27.
 */
public class ValidateProperty
{
    
    /**
     * 入参属性校验
     * 
     * @param obj
     * @return
     */
    public static String validateCommand(Object obj)
    {
        return ValidateUtil.validateCommand(obj, GroupCommand.class);
        
    }
}
