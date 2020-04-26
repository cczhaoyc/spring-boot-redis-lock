package com.suxia.cc.mybatis.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IdentNoValidator.class)
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdentNo {
	
	String message() default "证件号格式不正确！";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { }; 

    /**
     * 证件号的验证规则
     * @return
     */
    String regexp() default "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
    + "((0[1-9]{1})|(1[0-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
    + "[0-9]{3}[0-9Xx]{1}";


    @Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        IdentNo[] value();
    }

}
