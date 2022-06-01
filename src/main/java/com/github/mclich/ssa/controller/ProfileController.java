package com.github.mclich.ssa.controller;

import com.github.mclich.ssa.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController
{
    @GetMapping
    public String toProfile(Model model)
    {
        model.addAttribute("user", (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "profile";
    }
}