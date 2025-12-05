package com.codej.dto;

import com.codej.emuns.Shift;
import com.codej.model.Management;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
public class EducationLevelDTO {

    private UUID idEducationLevel;

    @NotNull
    private String name;

    @NotNull
    private Shift shift;

    @NotNull
    private boolean status;

    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @NotNull
    private String idManagement;
}
