package com.github.mclich.ssa.controller;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.model.Performance;
import com.github.mclich.ssa.model.Student;
import com.github.mclich.ssa.model.Teacher;
import com.github.mclich.ssa.repository.PerformanceRepository;
import com.github.mclich.ssa.repository.StudentRepository;
import com.github.mclich.ssa.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/{fn}-{ln}")
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class PersonController
{
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final PerformanceRepository performanceRepo;

    @GetMapping
    public String toPerson(@PathVariable("fn") String firstName, @PathVariable("ln") String lastName, Model model)
    {
        Optional<Student> oStudent=this.studentRepo.findByFullName(firstName, lastName);
        Optional<Teacher> oTeacher=this.teacherRepo.findByFullName(firstName, lastName);
        if(oStudent.isPresent())
        {
            Student student=oStudent.get();
            Optional<Performance> oPerf=this.performanceRepo.findById(student.getId());
            if(oPerf.isPresent())
            {
                model.addAttribute("student", student);
                model.addAttribute("perf", oPerf.get());
                model.addAttribute("dateFormat", Constants.DATE_FORMAT);
                return "student";
            }
            else throw new NoSuchElementException("No performance found for student with ID \""+student.getId()+"\"");
        }
        else if(oTeacher.isPresent())
        {
            Teacher teacher=oTeacher.get();
            model.addAttribute("teacher", teacher);
            model.addAttribute("dateFormat", Constants.DATE_FORMAT);
            return "teacher";
        }
        else throw new NoSuchElementException("No person found with name \""+StringUtils.capitalize(firstName)+" "+StringUtils.capitalize(lastName)+"\"");
    }
}