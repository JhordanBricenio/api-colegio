package com.codej.dto;

import com.codej.emuns.Shift;
import com.codej.emuns.EducationLevelType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelDTO {

    private UUID idEducationLevel;

    @NotNull
    private String name;

    @NotNull
    private Shift shift;

    @NotNull
    private EducationLevelType levelType;

    @NotNull
    private boolean status;

    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @NotNull
    private String idManagement;

    // Constructor compatible con versiones anteriores (sin levelType)
    public EducationLevelDTO(UUID idEducationLevel, String name, Shift shift, boolean status, LocalDateTime createdAt, LocalDateTime updatedAt, String idManagement) {
        this.idEducationLevel = idEducationLevel;
        this.name = name;
        this.shift = shift;
        this.levelType = EducationLevelType.OTHER; // valor por defecto para compatibilidad
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idManagement = idManagement;
    }
}
