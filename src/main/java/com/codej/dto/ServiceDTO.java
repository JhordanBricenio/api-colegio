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
public class ServiceDTO {

    private UUID idServices;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String image;

    private LocalDateTime creationDate;
    
    private UserDTO user;
}
