package com.github.mclich.ssa.controller;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.repository.ClassRepository;
import com.github.mclich.ssa.repository.StudentRepository;
import com.github.mclich.ssa.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class MenuController
{
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final ClassRepository classRepo;

    @GetMapping
    public String toHome()
    {
        return "home";
    }

    @GetMapping("/students")
    public String toStudents(Model model)
    {
        model.addAttribute("students", this.studentRepo.findAll());
        model.addAttribute("dateFormat", Constants.DATE_FORMAT);
        return "students";
    }

    @GetMapping("/teachers")
    public String toTeachers(Model model)
    {
        model.addAttribute("teachers", this.teacherRepo.findAll());
        model.addAttribute("dateFormat", Constants.DATE_FORMAT);
        return "teachers";
    }

    @GetMapping("/classes")
    public String toClasses(Model model)
    {
        model.addAttribute("classes", this.classRepo.findAll());
        model.addAttribute("yearFormat", Constants.YEAR_FORMAT);
        return "classes";
    }

    @GetMapping("/about")
    public String toAbout()
    {
        return "about";
    }
}