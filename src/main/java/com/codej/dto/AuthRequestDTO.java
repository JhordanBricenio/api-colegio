package com.codej.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String identifier;
    private String password;
}
