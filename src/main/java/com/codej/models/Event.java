package com.codej.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    //@NotBlank(message = "La fecha es obligatorio")
    //@Column(nullable = false)
    private Date date;
    //private Date time;
    @NotBlank(message = "El lugar es obligatorio")
    @Column(nullable = false)
    private String place;
    private String link;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date created_at;

    @PrePersist
    public void prePersist(){
        this.created_at = new Date();
    }






}
