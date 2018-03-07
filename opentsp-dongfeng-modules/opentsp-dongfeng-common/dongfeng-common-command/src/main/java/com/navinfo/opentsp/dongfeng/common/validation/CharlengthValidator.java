package com.navinfo.opentsp.dongfeng.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2016/6/2.
 */
public class CharlengthValidator implements ConstraintValidator<Charlength, String> {
    private int len;
    private int max;
    private int min;
    private String code;
    /**
     * 初始参数
     */
    @Override
    public void initialize(Charlength arg0) {
        this.len = arg0.length();
        this.max = arg0.max();
        this.min = arg0.min();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            return false;
        }
        //一个汉字或日韩文长度为2,英文字符长度为1
        char[] c = str.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++)
        {
            len++;
            if (!isLetter(c[i]))
            {
                len++;
            }
        }
        if(len > max){
            return  false;
        }else if(len < min){
            return  false;
        }
        else return  true;

    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c 指定的字符
     * @return true:Ascill字符
     * @author zhangyu
     */
    public static boolean isLetter(char c)
    {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }


}