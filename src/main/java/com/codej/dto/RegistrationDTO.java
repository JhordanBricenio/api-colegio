package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RegistrationDTO {

    private UUID idRegistration;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private StudentDTO student;

    private ParentDTO parent;

    private DegreeDTO degree;

    private EducationLevelDTO educationLevel;
}
