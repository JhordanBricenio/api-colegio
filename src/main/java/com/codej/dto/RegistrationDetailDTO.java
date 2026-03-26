package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDetailDTO {
    private UUID idRegistration;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UUID studentId;
    private String studentFullName;
    private String studentCode; // nuevo campo
    private String studentDni; // nuevo campo

    private UUID parentId;
    private String parentFullName;
    private String parentPhone; // nuevo
    private String parentDni;
    private String parentRelationship; // nuevo campo

    private UUID degreeId;
    private String degreeCourse;
    private String degreeSection;

    private UUID educationLevelId;
    private String educationLevelName;




}
