package com.github.mclich.ssa.validation.annotation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.validation.NameValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=NameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Name
{
    String value() default "name";
    String message() default "{"+Constants.NAME_MSG+"}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}