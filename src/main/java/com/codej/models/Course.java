package com.codej.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;
    private String description;
    private Integer credits;
    private Integer hours;
    private Integer semester;
    private String photo;

    /*//Relacion con course
    @JsonIgnoreProperties({"courses","hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "courses")
    private List<User> users;*/

    //Relacion con degree
    @JsonIgnoreProperties({"courses","hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_degree",joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "degree_id"))
    private List<Degree> degrees;








}
