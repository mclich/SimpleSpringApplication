package com.github.mclich.ssa.validation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.validation.annotation.Password;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String>
{
    @Override
    public boolean isValid(String login, ConstraintValidatorContext cxt)
    {
        return Pattern.compile(Constants.PASSWORD_REGEX).matcher(login).matches();
    }
}