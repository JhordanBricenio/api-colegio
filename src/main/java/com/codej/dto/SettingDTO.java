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
public class SettingDTO {

    private UUID idSetting;

    @NotNull
    private String name;

    private String logo;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
