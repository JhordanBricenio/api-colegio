package com.codej.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
public class ManagementDTO {

    private UUID idManagement;

    @NotNull
    private String name;

    @NotNull
    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
