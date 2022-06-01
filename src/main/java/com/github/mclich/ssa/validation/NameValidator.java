package com.github.mclich.ssa.validation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.validation.annotation.Name;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String>
{
    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt)
    {
        return Pattern.compile(Constants.NAME_REGEX, Pattern.CASE_INSENSITIVE).matcher(name).matches();
    }
}