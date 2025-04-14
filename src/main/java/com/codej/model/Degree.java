package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "degrees")
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDegree;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private String description;

    @JsonIgnoreProperties({"degrees","hibernateLazyInitializer", "handler"})
    @ManyToMany
    @JoinTable(
            name = "course_degree",
            joinColumns = @JoinColumn(name = "degree_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;




}
