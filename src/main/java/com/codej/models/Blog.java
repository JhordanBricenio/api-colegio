package com.codej.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "El titulo no puede estar vacio")
    private String title;
    @Column(length = 65535, nullable = false) // especifica la longitud de la columna de descripción
    @NotBlank(message = "El contenido no puede estar vacia")
    private String content;
    private String image;

    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(nullable = false)
    @NotBlank(message = "El autor no puede estar vacio")
    private String author;

    // Relacion con la tabla Etiqueta
    @JsonIgnoreProperties({"blog","hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "blog_etiqueta", joinColumns = @JoinColumn(name = "blog_id")
            , inverseJoinColumns = @JoinColumn(name = "etiqueta_id"))
    private List<Tag> tags;

}
