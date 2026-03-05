package com.codej.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class TeacherDTO {

    private UUID idTeacher;

    @NotNull
    private String specialty;

    @NotNull
    UserDTO user;
}
