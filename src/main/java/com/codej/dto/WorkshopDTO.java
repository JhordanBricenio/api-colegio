package com.codej.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDTO {

    private UUID idWorkshop;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer hours;

    private String photo;

    private LocalDateTime creationDate;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private UUID idUser;
}
