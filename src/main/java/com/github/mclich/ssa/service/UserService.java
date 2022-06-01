package com.github.mclich.ssa.service;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.model.Role;
import com.github.mclich.ssa.model.Status;
import com.github.mclich.ssa.model.User;
import com.github.mclich.ssa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    private final UserRepository userRepo;
    private final PasswordEncoder pwEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        return this.userRepo.findByLogin(login).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
    }

    public boolean save(User user)
    {
        if(user==null||this.userRepo.findByLogin(user.getLogin()).isPresent()) return false;
        user.setStatus(Status.ACTIVE);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(this.pwEncoder.encode(user.getPassword()));
        this.userRepo.save(user);
        return true;
    }

    public boolean save(User user, Model model, boolean terms)
    {
        if(user==null) return false;
        if(!user.getPassword().equals(user.getCPassword()))
        {
            model.addAttribute("pwcError", Constants.PASSWORD_CONFIRM);
            return false;
        }
        if(!terms)
        {
            model.addAttribute("termsError", Constants.TERMS_MSG);
            return false;
        }
        return this.save(user);
    }
}