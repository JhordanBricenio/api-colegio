package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String lastname;
    private String dni;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String photo;
}
