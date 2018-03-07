package com.navinfo.opentsp.dongfeng.common.annotation;

import com.navinfo.opentsp.dongfeng.common.util.ColumnStyle;
import com.navinfo.opentsp.dongfeng.common.util.FormatStyle;
import com.navinfo.opentsp.dongfeng.common.util.TimePrecision;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
	
	//列中文名称
	String header() default "";
	//顺序编号
	int columnNum() default 0;
	
	String split() default "";
	
	String width() default "";
	
	/**
	 * 列样式字段
	 * @return
	 */
	ColumnStyle style() default ColumnStyle.NULL;
	
	/**
	 * 时间精度,默认是精确到秒
	 * @return
	 */
	TimePrecision precision() default TimePrecision.NULL;
	
	/**
	 * 日期显示格式
	 * @return
	 */
	FormatStyle format() default FormatStyle.DATE;
}
