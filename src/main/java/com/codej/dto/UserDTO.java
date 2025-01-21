package com.codej.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UserDTO {
    private UUID id;
    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String dni;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private String photo;

    @NotNull
    private String sex;
}
