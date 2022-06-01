package com.github.mclich.ssa.validation.annotation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.validation.PasswordValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Password
{
    String message() default "{"+Constants.PASSWORD_MSG+"}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}