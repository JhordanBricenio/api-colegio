package com.codej.controller.dto;

import com.codej.emuns.StatusPayment;
import com.codej.emuns.StatusRegistration;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private Long id;
    private Date fechaMatricula;
    private Date fechaInicio;
    private Date fechaFin;
    private StatusRegistration statusRegistration;
    private StatusPayment statusPayment;
    private Double costo;
    private Double descuento;
    private String periodoAcademico;
    private String turno;
    private String seccion;
    private String jornada;

    private UserDTO userDTO;




}
