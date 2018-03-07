package com.navinfo.opentsp.dongfeng.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/6/2.
 */
public class BytelengthValidator implements ConstraintValidator<Bytelength, String> {
    private int len;
    private int max;
    private String code;
    /**
     * 初始参数
     */
    @Override
    public void initialize(Bytelength arg0) {
        this.len = arg0.length();
        this.max = arg0.max();
        this.code = arg0.code();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
            int strlen= 0;
            try {
                 strlen = str.getBytes(code).length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(strlen > max){
                return  false;
            }else return  true;

    }


}