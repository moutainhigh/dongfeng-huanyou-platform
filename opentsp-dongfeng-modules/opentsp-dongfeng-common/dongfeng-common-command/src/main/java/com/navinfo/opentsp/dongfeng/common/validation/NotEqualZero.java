package com.navinfo.opentsp.dongfeng.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-11
 **/
@Constraint(validatedBy = NotEqualValidator.class) //具体的实现
@Target({java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface NotEqualZero {
    String message() default "数据不能为0"; //提示信息,可以写死,可以填写国际化的key

    String code() default "UTF-8";

    //下面这两个属性必须添加
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
