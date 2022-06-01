package com.github.mclich.ssa.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="performance")
public class Performance
{
    private @Id @GeneratedValue @Column(name="student_id") Long id;
    private String academic;
    private String intellectual;
    private String personal;
    private String physical;
    private String cooperative;
}