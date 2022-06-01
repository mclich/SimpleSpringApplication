package com.github.mclich.ssa.validation.annotation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.validation.LoginValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=LoginValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Login
{
    String message() default "{"+Constants.LOGIN_MSG+"}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}