package com.codej.models;

import com.codej.emuns.Sex;
import com.codej.emuns.StatusUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(length = 50, nullable = false)
    private String lastname;

    @NotBlank(message = "El dni es obligatorio")
    @Column(unique = true)
    private String dni;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    private String address;
    private String photo;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private StatusUser statusUser=StatusUser.ACTIVO;

    private String speciality;




    /*@JsonIgnoreProperties({"users","hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;*/

    // Relacion con la tabla Notes
 /*  @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Note> notes;*/

    // Relacion con la tabla Payments
    @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    // Relacion con la tabla Services
    @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Servicio> services;

    // Relacion con la tabla Courses
    /*@JsonIgnoreProperties({"users","hibernateLazyInitializer", "handler"})
    @ManyToMany
    @JoinTable(name = "user_course",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;*/

    // Relacion con la tabla Workshops
    @JsonIgnoreProperties({"users","hibernateLazyInitializer", "handler"})
    @ManyToMany
    @JoinTable(name = "user_workshop",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "workshop_id"))
    private List<Workshop> workshops;

    @JsonIgnoreProperties({"users","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user")
    private List<Assistance> assistance;


}
