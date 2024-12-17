package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "degrees")
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;
    private String description;
    private String color;
    private String icon;

    //Relacion con course
    @JsonIgnoreProperties({"degrees","hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "degrees")
    private List<Course> courses;




}
