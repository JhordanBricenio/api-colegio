package com.codej.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DegreeDTO {

    private Integer idDegree;
    @NotNull
    private String name;
    private String description;
}
