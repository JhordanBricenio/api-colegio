package com.codej.model;

import com.codej.emuns.StatusPayment;
import com.codej.emuns.StatusRegistration;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaMatricula;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    private StatusRegistration statusRegistration; // Activo, Inactivo, Retirado

    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment; // Pagado, Pendiente, Parcial

    private Double costo;
    private Double descuento;

    private String periodoAcademico;
    private String turno;
    private String seccion;
    private String jornada;

    @Column(nullable = false)
    private String usuarioRegistro;
    private String fechaModificacion;
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "apoderado_id")
    private Apoderado apoderado;


    @PrePersist
    public void prePersist(){
        this.fechaMatricula = new Date();
        this.fechaInicio=new Date();
    }
}
