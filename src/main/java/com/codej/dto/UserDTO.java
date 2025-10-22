package com.codej.dto;

import com.codej.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class UserDTO {

    private UUID idUser;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String dni;

   @NotNull
    private LocalDate birthDate;

    @NotNull
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private String photo;

    @NotNull
    private String gender;

    private LocalDateTime createdAt;

    @NotNull
    private String rolId;
}
