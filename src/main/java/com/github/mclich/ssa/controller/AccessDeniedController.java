package com.github.mclich.ssa.controller;

import com.github.mclich.ssa.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/403")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class AccessDeniedController
{
    @GetMapping
    public String to403(Authentication auth, Model model)
    {
        if(auth!=null&&auth.getPrincipal() instanceof User) model.addAttribute("existingUser", (User)auth.getPrincipal());
        return "access-denied";
    }
}