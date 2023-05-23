package com.codej.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "licenses")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private User user;
}
