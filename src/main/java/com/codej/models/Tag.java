package com.codej.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
@Table(name = "etiqueta")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @Column(nullable = false)
    @NotBlank(message = "El color no puede estar vacio")
    private String color;
}
