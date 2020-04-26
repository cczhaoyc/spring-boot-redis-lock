package com.suxia.cc.mybatis.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class  EmailValidator implements ConstraintValidator<Email, String> {

	private String regexp;

    @Override
    public void initialize(Email constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if (value.matches(regexp)){
            return true;
        }
        return false;
    }
}