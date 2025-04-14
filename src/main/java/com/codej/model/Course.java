package com.codej.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCourse;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private String description;


    @ManyToMany(mappedBy = "courses")
    private List<Degree> degrees;








}
