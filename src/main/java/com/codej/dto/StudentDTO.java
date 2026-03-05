package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class StudentDTO {

    private UUID idStudent;

    private String code;

    private boolean status;

    private UserDTO user;

    private String idDegree;

    private String idEducationLevel;
}
