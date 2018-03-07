package com.navinfo.dongfeng.terminal.comm.validator;

import com.navinfo.dongfeng.terminal.comm.common.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Administrator on 2016/3/4.
 */
public class ValidateUtil {

    public static String validateCommand(Object obj, Class... groups) {
        String message = null;//用于存储验证后的错误信息

        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        if (null != obj) {
            try {

                Field[] fields = obj.getClass().getDeclaredFields();

                for (Field field : fields) {

                    if (field.isAnnotationPresent(Converter.class)) {

                        field.setAccessible(true);
                        Object jsonObj = field.get(obj);

                        if (null == jsonObj) {
                            continue;
                        }

                        if (!(jsonObj instanceof String)) {
                            continue;
                        }

                        String parameter = (String) jsonObj;

                        if (org.springframework.util.StringUtils.isEmpty(parameter)) {
                            continue;
                        }

                        Converter converter = field.getAnnotation(Converter.class);
                        message = setData(converter, obj, parameter, validator, groups);
                        if (!StringUtils.isNull(message)) {
                            return message;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return message;
            }

            if (!org.springframework.util.StringUtils.isEmpty(message)) {
                return message;
            }
        }

        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(obj, groups);//验证某个对象,，其实也可以只验证其中的某一个属性的

        Iterator<ConstraintViolation<Object>> iter = constraintViolations
                .iterator();
        while (iter.hasNext()) {
            String tempMessage = iter.next().getMessage();
            if (tempMessage != null && tempMessage.length() > 0) {
                message = tempMessage;
                break;
            }
        }

        return message;
    }

    /**
     * @param converter 传入对象成员变量上定义的Converter注解
     * @param obj       传入的对象.
     */
    private static String setData(Converter converter, Object obj, String json, Validator validator, Class... groups) throws Exception {

        String message = null;
        //获取成员变量的名字
        String targetFieldName = converter.target();

        //目标成员变量的Field对象
        Field targetField = obj.getClass().getDeclaredField(targetFieldName);

        //设置允许访问私有成员变量的权限
        targetField.setAccessible(true);

        JSONObject jsonObj = null;
        JSONArray jsonArray = null;

        Object targetFieldObject = null;
        try {

            jsonObj = JSONObject.fromObject(json);
        } catch (Exception e) {
            jsonArray = JSONArray.fromObject(json);
        }

        if ((null == jsonObj || jsonObj.isEmpty()) && (null == jsonArray || jsonArray.isEmpty())) {
            return null;
        }
        if (null != jsonObj && !jsonObj.isEmpty()) {
            targetFieldObject = JSONObject.toBean(jsonObj, targetField.getType());
        } else {

            ParameterizedType pt = (ParameterizedType) targetField.getGenericType();
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            Type actualTypeArgument = actualTypeArguments[0];
            String typeName = actualTypeArgument.getTypeName();
            Class c = Class.forName(typeName);
            targetFieldObject = JSONArray.toCollection(jsonArray, c);
        }

        //如果传入的参数中的成员变量包含ArrayList类型则进入此分支
        if (Collection.class.isAssignableFrom(targetFieldObject.getClass())) {

            Collection collection = (Collection) targetFieldObject;
            ParameterType type = converter.type();

            switch (type) {
                case LIST: {

                    List targetList = new ArrayList(collection);
                    if (null != targetList && !targetList.isEmpty()) {

                        for (Object o : targetList) {
                            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o, groups);//验证某个对象,，其实也可以只验证其中的某一个属性的
                            Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
                            while (iter.hasNext()) {
                                String tempMessage = iter.next().getMessage();
                                if (tempMessage != null && tempMessage.length() > 0) {
                                    message = tempMessage;
                                    return message;
                                }
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        } else {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(targetFieldObject, groups);//验证某个对象,，其实也可以只验证其中的某一个属性的
            Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
            while (iter.hasNext()) {
                String tempMessage = iter.next().getMessage();
                if (tempMessage != null && tempMessage.length() > 0) {
                    message = tempMessage;
                    return message;
                }
            }
        }
        targetField.set(obj, targetFieldObject);
        return null;
    }

    /**
     * 校验bean
     *
     * @param obj 需要校验的bean
     * @return
     */
    public static String validateBean(Object obj) {
        String message = null;
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        Map<String, StringBuffer> errorMap = null;
        Set<ConstraintViolation<Object>> set = validator.validate(obj, Default.class);
        Iterator<ConstraintViolation<Object>> iter = set.iterator();
        while (iter.hasNext()) {
            String tempMessage = iter.next().getMessage();
            if (tempMessage != null && tempMessage.length() > 0) {
                message = tempMessage;
                return message;
            }
        }
        return null;
    }
}
