package com.github.mclich.ssa.controller;

import com.github.mclich.ssa.model.User;
import com.github.mclich.ssa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/sign-up")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class SignUpController
{
    private final UserService userService;

    @GetMapping
    public String signUp(Model model)
    {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping
    public String signUp(@RequestParam(defaultValue="false") boolean terms, @Valid @ModelAttribute User user, BindingResult bResult, HttpSession session, Model model)
    {
        model.addAttribute("user", user);
        if(this.userService.save(user, model, terms)&&!bResult.hasErrors())
        {
            session.setAttribute("signUp", "signUp");
            return "redirect:/";
        }
        return "sign-up";
    }
}