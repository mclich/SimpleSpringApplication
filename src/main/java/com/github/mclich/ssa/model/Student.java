package com.github.mclich.ssa.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="student")
public class Student
{
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean gender;
    private int pPoints;
    private int dPoints;
    private String imagePath;
    private @ManyToOne @JoinColumn Class clazz;
}