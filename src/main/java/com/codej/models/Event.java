package com.codej.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;
    @NotBlank(message = "La fecha es obligatorio")
    @Column(nullable = false)
    private String date;
    private String time;
    @NotBlank(message = "El lugar es obligatorio")
    @Column(nullable = false)
    private String place;
    private String link;
    private String status;
    private String created_at;
}
