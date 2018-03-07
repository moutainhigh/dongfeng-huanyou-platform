package com.navinfo.opentsp.dongfeng.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjUtils {

    /**
     * 将简单（只包含存在基本类型及其封装类和string）bean类型转换为map，此方法只做第一层的处理，即：如果param中有符合类型的对象，只取出对象
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 判断是否为空bean，如果是直接返回
        if (bean == null) {
            return resultMap;
        }
        try {
            if (Map.class.isAssignableFrom(bean.getClass())) {
                resultMap = (Map<String, Object>) bean;

                for (String key : resultMap.keySet()) {
                    Object value = resultMap.get(key);

                    // 如果是String类型进行转义
                    if (value != null && String.class.isAssignableFrom(value.getClass())) {
                        resultMap.put(key, EscapeUtil.getString(String.valueOf(value)));
                    } else {
                        resultMap.put(key, value);
                    }
                }
                return resultMap;
            }
            // 获取类的class
            Field[] fields = bean.getClass().getDeclaredFields();
            // 获取所有父类中的所有Field，并合并到当前Field中
            Class superClass = bean.getClass().getSuperclass();
            while (superClass != null) {
                Field[] superFields = superClass.getDeclaredFields();
                fields = concat(superFields, fields);// concat时，注意数组顺序（父类在前，子类在后），为了下面转成Map后，如果存在子类含有和父类一样的属性的情况时，子类可以覆盖父类中对于属性的值
                superClass = superClass.getSuperclass();
            }
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();// 获取所有字段名称
                Object filedValue = null;

                int typeInt = fields[i].getModifiers();// 获取字段的类型
                // 获取字段的类型申明表，8静态，2私有，16final =26，类型26为静态常量，不做处理如最终serialVersionUID
                if (typeInt != 26) {
                    fields[i].setAccessible(true);// 设置访问权限
                    filedValue = fields[i].get(bean);// 获取所有字段的值

                    // 如果是String类型进行转义
                    if (filedValue != null && String.class.isAssignableFrom(filedValue.getClass())) {
                        filedValue = EscapeUtil.getString(String.valueOf(filedValue));
                    }

                }

                resultMap.put(fieldName, filedValue);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 数组合并
     *
     * @param first  数组1
     * @param second 数组2
     * @param <T>    泛型
     * @return 数组1+数组2
     */
    private static <T> T[] concat(T[] first, T[] second) {
        if (first == null || first.length == 0) {
            return second;
        }
        if (second == null || second.length == 0) {
            return first;
        }
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * bean转换为属性map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> toPropertyMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
