package com.github.mclich.ssa.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="teacher")
public class Teacher
{
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean gender;
    private @OneToOne @JoinColumn Class clazz;
}