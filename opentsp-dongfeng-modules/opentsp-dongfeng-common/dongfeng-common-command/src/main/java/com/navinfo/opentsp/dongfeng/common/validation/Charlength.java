package com.navinfo.opentsp.dongfeng.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/6/2.
 */

@Constraint(validatedBy = CharlengthValidator.class) //具体的实现
@Target( { java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.FIELD })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface Charlength {
    String message() default "超出限定字数"; //提示信息,可以写死,可以填写国际化的key

    int length() default 0;
    int min() default 0;
    int max()  default 50;

    //下面这两个属性必须添加
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}