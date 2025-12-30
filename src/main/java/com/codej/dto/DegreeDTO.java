package com.codej.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DegreeDTO {

    private UUID idDegree;

    @NotNull
    private String course;

    @NotNull
    private String section;

    @NotNull
    private boolean status;

    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @NotNull
    private String idEducationLevel;
}
