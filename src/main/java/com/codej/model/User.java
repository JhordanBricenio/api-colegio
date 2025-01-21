package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastname;

    @Column(name = "dni", length = 8, nullable = false, unique = true)
    @Size(min = 8, max = 8)
    private String dni;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "phone", length = 12, nullable = false)
    private String phone;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "photo", length = 100)
    private String photo;

    @Column(name = "sex", length = 10, nullable = false)
    private String sex;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnoreProperties({"users","hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_workshop",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "id_workshop", referencedColumnName = "idWorkshop")
    )
    private List<Workshop> workshops;

    @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Services> services;


    @JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Assistance> assistance;

}
