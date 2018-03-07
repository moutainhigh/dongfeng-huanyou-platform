package com.navinfo.dongfeng.terminal.comm.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Converter {
	
	String target() default "";
	
	ParameterType type() default ParameterType.BEAN;
}
