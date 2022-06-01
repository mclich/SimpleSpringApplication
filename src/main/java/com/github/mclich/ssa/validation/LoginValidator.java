package com.github.mclich.ssa.validation;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.repository.UserRepository;
import com.github.mclich.ssa.validation.annotation.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class LoginValidator implements ConstraintValidator<Login, String>
{
    private UserRepository userRepo;

    public LoginValidator(){}

    @Autowired
    public LoginValidator(UserRepository userRepo)
    {
        this.userRepo=userRepo;
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext ctx)
    {
        if(this.userRepo==null) return false;    //true
        boolean isUnique=this.userRepo.findByLogin(login).isEmpty();
        if(!isUnique)
        {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("{"+Constants.LOGIN_NOT_UNIQUE+"}").addConstraintViolation();
        }
        return isUnique&&Pattern.compile(Constants.LOGIN_REGEX).matcher(login).matches();
    }
}