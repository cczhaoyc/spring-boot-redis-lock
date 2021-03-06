package com.suxia.cc.mybatis.base.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentNoValidator implements ConstraintValidator<IdentNo, String> {

    private String regexp;

    @Override
    public void initialize(IdentNo constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.matches(regexp)) {
            return true;
        }
        return false;
    }
}
