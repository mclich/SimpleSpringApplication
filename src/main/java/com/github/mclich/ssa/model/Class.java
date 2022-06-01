package com.github.mclich.ssa.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="class")
public class Class
{
    private @Id @GeneratedValue Long id;
    private String title;
    private LocalDate year;
    private int cPoints;
}