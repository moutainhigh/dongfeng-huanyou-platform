package com.navinfo.opentsp.dongfeng.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * 复制相同属性工具类
 * 
 * @author liusanhu
 * @since
 */
public class CopyPropUtil {

	/** 
     * 将源对象相同属性复制到目标对象 
     * 
     */  
    public static void copyProp(Object source, Object target) throws Exception{  
          
        Class<? extends Object> sourceClass = source.getClass();
        Class<? extends Object> targetClass = target.getClass();
          
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();
          
        for(Field sourceField : sourceFields){  
            String sourceName = sourceField.getName();
            Class<?> sourceType = sourceField.getType();
                         
            TFOR:for(Field targetField : targetFields){  
            	
                String targetName = targetField.getName();
                Class<?> targetType = targetField.getType();
                
                if(targetName.equals(sourceName) && targetType.getName().equals(sourceType.getName())){
                	PropertyDescriptor sourcePd = new PropertyDescriptor(sourceName,sourceClass);
        			Method getMethod = sourcePd.getReadMethod();
        			Object value = getMethod.invoke(source);
        			
        			PropertyDescriptor targetPd = new PropertyDescriptor(targetName,targetClass);
        			Method setMethod = targetPd.getWriteMethod();
        			setMethod.invoke(target,value);
        			break TFOR;
                }
            }  
        }  
    }  
}
