package com.navinfo.opentsp.dongfeng.common.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-09-11
 **/
public class NotEqualValidator implements ConstraintValidator<NotEqualZero, String> {
    @Override
    public void initialize(NotEqualZero notEqualZero) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        if ("0".equals(s)) {
            return false;
        } else {
            return true;
        }
    }
}
