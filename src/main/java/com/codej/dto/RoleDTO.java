package com.codej.dto;

import com.codej.emuns.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class RoleDTO {

    private UUID idRole;

    @NotNull
    private RoleName name;

    private String description;

    @NotNull
    private boolean status;
}
